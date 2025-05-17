package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.item.base.BasicPolymerItem;
import cc.thonly.touhoumod.util.DelayedTask;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TimeStopClock extends BasicPolymerItem {
    public TimeStopClock(String path, Settings settings) {
        super(path, settings.maxCount(1), Items.CLOCK);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        MinecraftServer server = world.getServer();
        if (!world.isClient() && server != null) {
            ServerTickManager tickManager = server.getTickManager();
            tickManager.setFrozen(!tickManager.isFrozen());
            DelayedTask.create(server, 20 * 20, () -> tickManager.setFrozen(false));
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.SUCCESS;
    }
}
