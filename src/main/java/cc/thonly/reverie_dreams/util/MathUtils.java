package cc.thonly.reverie_dreams.util;

import java.util.Random;

public final class MathUtils {
    public static int randomInRange(long seed, int min, int max) {
        Random random = new Random(seed);
        return random.nextInt(max - min + 1) + min;
    }
}
