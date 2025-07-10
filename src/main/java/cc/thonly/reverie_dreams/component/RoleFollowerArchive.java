package cc.thonly.reverie_dreams.component;

import cc.thonly.reverie_dreams.entity.npc.NPCEntityImpl;
import cc.thonly.reverie_dreams.gui.NPCGui;
import cc.thonly.reverie_dreams.inventory.NPCInventory;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Getter
public class RoleFollowerArchive {
    public static final Codec<RoleFollowerArchive> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("entity_type_id").forGetter(RoleFollowerArchive::getEntityTypeId),
            Codec.STRING.fieldOf("name").forGetter(RoleFollowerArchive::getName),
            NbtCompound.CODEC.fieldOf("inventory_nbt").forGetter(RoleFollowerArchive::getInventoryNbt),
            NbtCompound.CODEC.fieldOf("head_nbt").forGetter(RoleFollowerArchive::getHeadNbt),
            NbtCompound.CODEC.fieldOf("chest_nbt").forGetter(RoleFollowerArchive::getChestNbt),
            NbtCompound.CODEC.fieldOf("legs_nbt").forGetter(RoleFollowerArchive::getLegsNbt),
            NbtCompound.CODEC.fieldOf("boots_nbt").forGetter(RoleFollowerArchive::getBootsNbt)
    ).apply(instance, RoleFollowerArchive::new));

    private final Identifier entityTypeId;
    private final String name;
    private NbtCompound inventoryNbt = new NbtCompound();
    private NbtCompound headNbt = new NbtCompound();
    private NbtCompound chestNbt = new NbtCompound();
    private NbtCompound legsNbt = new NbtCompound();
    private NbtCompound bootsNbt = new NbtCompound();

    public RoleFollowerArchive(Identifier entityTypeId, String name, NbtCompound inventoryNbt, NbtCompound headNbt, NbtCompound chestNbt, NbtCompound legsNbt, NbtCompound bootsNbt) {
        this.entityTypeId = entityTypeId;
        this.name = name;
        this.inventoryNbt = inventoryNbt;
        this.headNbt = headNbt;
        this.chestNbt = chestNbt;
        this.legsNbt = legsNbt;
        this.bootsNbt = bootsNbt;
    }

    public RoleFollowerArchive(EntityType<?> entityType, NPCEntityImpl entity, RegistryWrapper.WrapperLookup registries) {
        this.entityTypeId = Registries.ENTITY_TYPE.getId(entityType);
        this.name = entity.hasCustomName() ? "" : Text.Serialization.toJsonString(entity.getName(), registries);
        NPCInventory inventory = entity.getInventory();
        Inventories.writeNbt(this.inventoryNbt, inventory.heldStacks, registries);
        Inventories.writeNbt(this.headNbt, inventory.getArmorInventory().getHead().heldStacks, registries);
        Inventories.writeNbt(this.chestNbt, inventory.getArmorInventory().getChest().heldStacks, registries);
        Inventories.writeNbt(this.legsNbt, inventory.getArmorInventory().getLegs().heldStacks, registries);
        Inventories.writeNbt(this.bootsNbt, inventory.getArmorInventory().getFeet().heldStacks, registries);
    }

    public NPCEntityImpl respawn(ServerWorld world, BlockPos pos, RegistryWrapper.WrapperLookup registries) {
        EntityType<?> entityType = Registries.ENTITY_TYPE.get(this.entityTypeId);
        Entity entity = entityType.spawn(world, pos, SpawnReason.MOB_SUMMONED);

        if (!(entity instanceof NPCEntityImpl impl)) {
            return null;
        }

        NPCInventory inventory = new NPCInventory(NPCGui.size());
        Inventories.readNbt(this.inventoryNbt, inventory.heldStacks, registries);
        Inventories.readNbt(this.headNbt, inventory.getArmorInventory().getHead().heldStacks, registries);
        Inventories.readNbt(this.chestNbt, inventory.getArmorInventory().getChest().heldStacks, registries);
        Inventories.readNbt(this.legsNbt, inventory.getArmorInventory().getLegs().heldStacks, registries);
        Inventories.readNbt(this.bootsNbt, inventory.getArmorInventory().getFeet().heldStacks, registries);
        if (!this.name.isEmpty()) {
            Text.Serialization.fromJson(this.name, registries);
        }
        impl.setInventory(inventory);
        return impl;
    }
}
