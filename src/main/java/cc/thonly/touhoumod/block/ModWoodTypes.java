package cc.thonly.touhoumod.block;

import cc.thonly.touhoumod.Touhou;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;

public interface ModWoodTypes {
    BlockSetType SPIRITUAL_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(Touhou.id("spiritual"));
    WoodType SPIRITUAL_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).register(Touhou.id("spiritual"), SPIRITUAL_BLOCK_SET_TYPE);
}
