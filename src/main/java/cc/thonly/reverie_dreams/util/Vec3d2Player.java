package cc.thonly.reverie_dreams.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Vec3d2Player(@NotNull Vec3d vec3d, @Nullable Entity player) {
}
