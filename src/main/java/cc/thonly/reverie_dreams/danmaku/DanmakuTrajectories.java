package cc.thonly.reverie_dreams.danmaku;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.danmaku.trajectory.SingleTrajectory;
import cc.thonly.reverie_dreams.danmaku.trajectory.TripleTrajectory;
import cc.thonly.reverie_dreams.registry.RegistryManager;
import cc.thonly.reverie_dreams.registry.StandaloneRegistry;

public class DanmakuTrajectories {
    public static final DanmakuTrajectory SINGLE = RegistryManager.register(RegistryManager.DANMAKU_TRAJECTORY, Touhou.id("single"), new SingleTrajectory());
    public static final DanmakuTrajectory TRIPLE = RegistryManager.register(RegistryManager.DANMAKU_TRAJECTORY, Touhou.id("triple"), new TripleTrajectory());

    public static void bootstrap(StandaloneRegistry<DanmakuTrajectory> registry) {

    }
}
