package cc.thonly.reverie_dreams.danmaku;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.danmaku.trajectory.SingleTrajectory;
import cc.thonly.reverie_dreams.danmaku.trajectory.TripleTrajectory;
import cc.thonly.reverie_dreams.registry.RegistrySchema;
import cc.thonly.reverie_dreams.registry.RegistrySchemas;

public class DanmakuTrajectories {
    public static final DanmakuTrajectory SINGLE = RegistrySchemas.register(RegistrySchemas.DANMAKU_TRAJECTORY, Touhou.id("single"), new SingleTrajectory());
    public static final DanmakuTrajectory TRIPLE = RegistrySchemas.register(RegistrySchemas.DANMAKU_TRAJECTORY, Touhou.id("triple"), new TripleTrajectory());

    public static void bootstrap(RegistrySchema<DanmakuTrajectory> registry) {

    }
}
