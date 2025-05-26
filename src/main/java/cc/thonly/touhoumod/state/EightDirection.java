package cc.thonly.touhoumod.state;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum EightDirection implements StringIdentifiable {
    NORTH, NORTHEAST, EAST, SOUTHEAST,
    SOUTH, SOUTHWEST, WEST, NORTHWEST;

    @Override
    public String asString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static EightDirection fromYaw(double yaw) {
        int index = Math.floorMod((int)Math.round(yaw / 45.0), 8);
        return values()[index];
    }

    public float getYaw() {
        return switch (this) {
            case SOUTH -> 0f;
            case SOUTHWEST -> 45f;
            case WEST -> 90f;
            case NORTHWEST -> 135f;
            case NORTH -> 180f;
            case NORTHEAST -> 225f;
            case EAST -> 270f;
            case SOUTHEAST -> 315f;
        };
    }
}