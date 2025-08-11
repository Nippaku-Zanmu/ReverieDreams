package cc.thonly.mystias_izakaya.api;

import cc.thonly.mystias_izakaya.component.FoodProperty;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

@FunctionalInterface
public interface FoodPropertyCallback {
    Event<FoodPropertyCallback> EVENT = EventFactory.createArrayBacked(FoodPropertyCallback.class,
            (listeners) -> (world, user, property) -> {
                for (FoodPropertyCallback callback : listeners) {
                    callback.onUse(world, user, property);
                }
            }
    );

    void onUse(ServerWorld world, LivingEntity user, FoodProperty property);
}
