package cc.thonly.reverie_dreams.mixin.accessor;

import cc.thonly.reverie_dreams.interfaces.AbstractBlockSettingsAccessorImpl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor extends AbstractBlockSettingsAccessorImpl {
    @Accessor("soundGroup")
    BlockSoundGroup getSoundGroup();
}
