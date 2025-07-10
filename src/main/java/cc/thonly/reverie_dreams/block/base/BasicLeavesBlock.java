package cc.thonly.reverie_dreams.block.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.datagen.ModBlockTagProvider;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import com.mojang.serialization.MapCodec;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BasicLeavesBlock extends LeavesBlock implements PolymerBlock, PolymerTexturedBlock, FactoryBlock, IdentifierGetter {
    public static final MapCodec<BasicLeavesBlock> CODEC = BasicLeavesBlock.createCodec(BasicLeavesBlock::new);

    private Identifier identifier;
    private BlockState polymerBlockState;

    public BasicLeavesBlock(Settings settings) {
        super(0.01f, settings);
    }

    public BasicLeavesBlock(String name, Settings settings) {
        this(Touhou.id(name), settings);
    }

    public BasicLeavesBlock(Identifier identifier, Settings settings) {
        this(settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        this.polymerBlockState = PolymerBlockResourceUtils.requestBlock(BlockModelType.TRANSPARENT_BLOCK, PolymerBlockModel.of(Identifier.of(identifier.getNamespace(), "block/" + identifier.getPath())));
        ModBlockTagProvider.LEAVES.add(this);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    @Override
    public MapCodec<? extends LeavesBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void spawnLeafParticle(World world, BlockPos pos, Random random) {
        EntityEffectParticleEffect entityEffectParticleEffect = EntityEffectParticleEffect.create(ParticleTypes.TINTED_LEAVES, world.getBlockColor(pos));
        ParticleUtil.spawnParticle(world, pos, random, entityEffectParticleEffect);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return this.polymerBlockState;
    }
}
