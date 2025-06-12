package cc.thonly.reverie_dreams.entity.holder;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import org.joml.Matrix4f;

@Setter
@Getter
public class MagicBroomHolder extends ElementHolder {
    private ItemDisplayElement element;
    private Entity entity;

    public MagicBroomHolder(Entity entity) {
        this.entity = entity;
    }

    @Override
    protected void onTick() {
        super.onTick();
        if(element != null) {
            Matrix4f transform = new Matrix4f()
                    .translate(0f, -0.5f, 0f)
                    .rotateY((float) Math.toRadians(-entity.getHeadYaw()))
                    .scale(1.0f);

            element.setTransformation(transform);
            element.startInterpolationIfDirty();
        }
    }
}
