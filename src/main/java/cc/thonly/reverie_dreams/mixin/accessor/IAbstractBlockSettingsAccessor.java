package cc.thonly.reverie_dreams.mixin.accessor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface IAbstractBlockSettingsAccessor extends cc.thonly.reverie_dreams.interfaces.IAbstractBlockSettingsAccessor {
    @Accessor("soundGroup")
    BlockSoundGroup getSoundGroup();
}
