package cc.thonly.reverie_dreams.block.base;


import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

@Getter
public class BasicPottedPolymerPlant extends FlowerPotBlock implements FactoryBlock, IdentifierGetter {
    private final Identifier identifier;
    private final ItemStack MODEL;

    public BasicPottedPolymerPlant(Identifier identifier, Block content, Settings settings, boolean useExtraModel) {
        super(content, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
        MODEL = ItemDisplayElementUtil.getModel(Identifier.of(identifier.getNamespace(), "block/%s".formatted(useExtraModel ? identifier.getPath() : identifier.getPath().replace("potted_", ""))));
    }

    public BasicPottedPolymerPlant(String path, Block content, Settings settings, boolean useExtraModel) {
        this(Touhou.id(path), content, settings, useExtraModel);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.FLOWER_POT.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new ItemDisplayPottedPlantModel(pos);
    }

    private class ItemDisplayPottedPlantModel extends BlockModel {

        public ItemDisplayPottedPlantModel(BlockPos pos) {
            ItemDisplayElement main = ItemDisplayElementUtil.createSimple(MODEL);
            main.setScale(new Vector3f(0.98f));
            main.setDisplaySize(1, 1);
            int rotation = pos.hashCode() % 4 * 90;
            main.setRightRotation(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
            this.addElement(main);
        }
    }
}