package cc.thonly.reverie_dreams.mixin.server;

import cc.thonly.reverie_dreams.trading_card.ITradingCardPlayer;
import cc.thonly.reverie_dreams.trading_card.TradingCardManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements ITradingCardPlayer {
    @Unique
    private TradingCardManager tradingCardManager;

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(MinecraftServer server, ServerWorld world, GameProfile profile, SyncedClientOptions clientOptions, CallbackInfo ci) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) (Object) this;
        this.tradingCardManager = new TradingCardManager(serverPlayer);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    public void read(ReadView view, CallbackInfo ci) {
        this.tradingCardManager.read(view);
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    public void write(WriteView view, CallbackInfo ci) {
        this.tradingCardManager.write(view);
    }

    @Override
    public TradingCardManager getTradingCardManager() {
        return this.tradingCardManager;
    }
}
