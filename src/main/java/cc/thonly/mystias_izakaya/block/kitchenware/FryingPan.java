package cc.thonly.mystias_izakaya.block.kitchenware;

import cc.thonly.mystias_izakaya.block.AbstractKitchenwareBlock;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class FryingPan extends AbstractKitchenwareBlock {
    public FryingPan(String id, Settings settings) {
        super(id, new Vector3f(2.0f), new Vec3d(0, 0, 0), settings);
    }
}
