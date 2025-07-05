package cc.thonly.mystias_izakaya.block.crop;

import cc.thonly.reverie_dreams.block.base.BasicCropBlock;
import cc.thonly.reverie_dreams.util.CropAgeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

public class SweetPotatoCrop extends BasicCropBlock {
    public static final MapCodec<SweetPotatoCrop> CODEC = SweetPotatoCrop.createCodec(SweetPotatoCrop::new);

    protected SweetPotatoCrop(Settings settings) {
        super(settings);
    }

    public SweetPotatoCrop(Identifier identifier, Settings settings) {
        super(identifier, settings);
    }

    @Override
    public Integer getMaxAge() {
        return 6;
    }

    @Override
    public IntProperty getAgeProperty() {
        return CropAgeUtil.fromInt(6);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }
}
