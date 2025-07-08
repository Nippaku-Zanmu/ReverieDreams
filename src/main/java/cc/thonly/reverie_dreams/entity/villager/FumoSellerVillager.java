package cc.thonly.reverie_dreams.entity.villager;

import cc.thonly.reverie_dreams.block.Fumo;
import cc.thonly.reverie_dreams.entity.ModEntities;
import cc.thonly.reverie_dreams.mixin.accessor.VillagerEntityAccessor;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.sgui.api.gui.MerchantGui;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.*;
import net.minecraft.world.World;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.*;

@Slf4j
@Getter
public class FumoSellerVillager extends WanderingTraderEntity implements PolymerEntity {
    private static final Gson GSON = new Gson();
    private final Set<SellerGui> sessions = new HashSet<>();
    private VillagerData prev;

    public FumoSellerVillager(EntityType<? extends WanderingTraderEntity> entityType, World world) {
        super(entityType, world);
    }

    public FumoSellerVillager(World world) {
        super(ModEntities.FUMO_SELLER_VILLAGER, world);
    }

    public FumoSellerVillager(VillagerData prev, World world) {
        super(ModEntities.FUMO_SELLER_VILLAGER, world);
        this.prev = prev;
    }

    public FumoSellerVillager(VillagerEntity prevEntity, World world) {
        super(ModEntities.FUMO_SELLER_VILLAGER, world);
        this.prev = prevEntity.getVillagerData();
    }

    @Override
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        PolymerEntity.super.modifyRawTrackedData(data, player, initial);
        if (initial && !this.getWorld().isClient) {
            MinecraftServer server = this.getServer();
            assert server != null;
            DynamicRegistryManager.Immutable registryManager = server.getRegistryManager();
            Registry<VillagerType> villagerTypeRegistry = registryManager.getOrThrow(RegistryKeys.VILLAGER_TYPE);
            Registry<VillagerProfession> villagerProfessionRegistry = registryManager.getOrThrow(RegistryKeys.VILLAGER_PROFESSION);

            VillagerData modifyData = new VillagerData(
                    villagerTypeRegistry.getOrThrow(VillagerType.PLAINS),
                    villagerProfessionRegistry.getOrThrow(VillagerProfession.LIBRARIAN),
                    2
            );

            DataTracker.SerializedEntry<VillagerData> entry = DataTracker.SerializedEntry.of(
                    VillagerEntityAccessor.VILLAGER_DATA(),
                    modifyData);

            data.add(entry);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        World baseWorld = player.getWorld();
        ItemStack stack = player.getStackInHand(hand);
        if (!baseWorld.isClient() && baseWorld instanceof ServerWorld world) {
            if (stack.getItem() instanceof ShovelItem && player.isSneaking()) {
                boolean canceled = this.cancel();
                if (canceled && !player.isInCreativeMode()) {
                    stack.damage(1, player);
                }
                player.swingHand(hand);
                return ActionResult.SUCCESS_SERVER;
            }
            if (this.sessions.isEmpty()) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

                this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, player.getPos());
                this.getNavigation().stop();

                SellerGui sellerGui = new SellerGui(serverPlayer, this);
                sellerGui.open();
                player.swingHand(hand);
                return ActionResult.SUCCESS_SERVER;
            }
        }
        return ActionResult.SUCCESS;
    }

    public static DefaultAttributeContainer.Builder createLivingAttributes() {
        DefaultAttributeContainer.Builder livingAttributes = MobEntity.createMobAttributes();
        return livingAttributes;
    }

    @Override
    public void tickMovement() {
        if (!this.getWorld().isClient() && !this.sessions.isEmpty()) {
            return;
        }
        super.tickMovement();
    }

    public boolean cancel() {
        World world = this.getWorld();
        if (this.prev != null) {
            VillagerEntity villager = new VillagerEntity(EntityType.VILLAGER, this.getWorld());
            villager.setVillagerData(this.prev);
            villager.setPos(this.getX(), this.getY(), this.getZ());
            world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS);
            this.discard();
            world.spawnEntity(villager);
            return true;
        }
        return false;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Optional<String> prevVillagerData = nbt.getString("PrevVillagerData");
        if (prevVillagerData.isPresent()) {
            String jsonString = prevVillagerData.get();
            JsonElement element = JsonParser.parseString(jsonString);
            Dynamic<JsonElement> input = new Dynamic<>(JsonOps.INSTANCE, element);
            DataResult<VillagerData> parse = VillagerData.CODEC.parse(input);
            Optional<VillagerData> result = parse.result();
            result.ifPresent((data) -> this.prev = data);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.prev != null) {
            DataResult<JsonElement> dataResult = VillagerData.CODEC.encodeStart(JsonOps.INSTANCE, this.prev);
            Optional<JsonElement> result = dataResult.result();
            if (result.isPresent()) {
                JsonElement element = result.get();
                nbt.putString("PrevVillagerData", GSON.toJson(element));
            }
        }

    }

    public List<TradeOffer> getVillagerOffers() {
        long seed = this.getVillagerSeed();
        Random random = new Random(seed);

        List<TradeOffer> offers = new ArrayList<>();
        List<Fumo> allFumos = new ArrayList<>(RegistryManager.FUMO.values());

        Collections.shuffle(allFumos, random);

        int count = 5 + random.nextInt(6);
        List<Fumo> selectedFumos = allFumos.subList(0, Math.min(count, allFumos.size()));

        for (Fumo fumo : selectedFumos) {
            Item item = fumo.item();
            ItemStack sellItem = new ItemStack(item);

            int emeraldAmount = 31 + random.nextInt(14);
            TradedItem first = new TradedItem(Items.EMERALD, emeraldAmount);
            TradedItem second = new TradedItem(Items.WHITE_WOOL, 32);

            TradeOffer offer = new TradeOffer(first, Optional.of(second), sellItem, 3, 1, 0.05f);
            offers.add(offer);
        }

        return offers;
    }

    public long getVillagerSeed() {
        UUID uuid = this.getUuid();
        World world = this.getWorld();
        long day = world.getTimeOfDay() / 24000L;
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        return mostSigBits + leastSigBits + day;
    }

    @Override
    public EntityType<?> getPolymerEntityType(PacketContext context) {
        return EntityType.VILLAGER;
    }

    @Getter
    public static class SellerGui extends MerchantGui {
        private final FumoSellerVillager self;

        public SellerGui(ServerPlayerEntity player, FumoSellerVillager self) {
            super(player, false);
            this.self = self;
            this.init();
        }

        public void init() {
            this.setTitle(this.self.getName());
            List<TradeOffer> villagerOffers = this.self.getVillagerOffers();
            for (TradeOffer offer : villagerOffers) {
                this.addTrade(offer);
            }
        }

        @Override
        public void onOpen() {
            super.onOpen();
            this.self.getSessions().add(this);
        }

        @Override
        public void onClose() {
            super.onClose();
            this.self.getSessions().remove(this);
        }
    }
}
