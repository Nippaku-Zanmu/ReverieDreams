package cc.thonly.mystias_izakaya.block.kitchenware;

import cc.thonly.mystias_izakaya.block.AbstractKitchenwareBlock;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.util.function.DoubleUnaryOperator;


public class Steamer extends AbstractKitchenwareBlock {
    public Steamer(String id, DoubleUnaryOperator bonusOperator, Double failureProbability, Settings settings) {
        super(id, bonusOperator, failureProbability, new Vector3f(1f), new Vec3d(0, 0, 0), settings);
    }
}
