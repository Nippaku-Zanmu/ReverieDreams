package cc.thonly.mystias_izakaya.block.base;

import cc.thonly.reverie_dreams.block.base.BasicCropBlock;
import cc.thonly.reverie_dreams.util.CropAgeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

public class TestCropBlock extends BasicCropBlock {
    public static final MapCodec<TestCropBlock> CODEC = TestCropBlock.createCodec(TestCropBlock::new);

    protected TestCropBlock(Settings settings) {
        super(settings);
    }

    public TestCropBlock(Identifier identifier, Settings settings) {
        super(identifier, settings);
    }

    @Override
    public Integer getMaxAge() {
        return 7;
    }

    @Override
    public IntProperty getAgeProperty() {
        return CropAgeUtil.fromInt(7);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }
}
