package cc.thonly.reverie_dreams.block.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public class BasicPolymerPressurePlate extends PressurePlateBlock implements FactoryBlock, IdentifierGetter {
    private final Identifier identifier;
    private final Block template = Blocks.MANGROVE_PRESSURE_PLATE;

    public BasicPolymerPressurePlate(Identifier identifier, BlockSetType blockSet, Settings settings) {
        super(blockSet, settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, identifier)));
        this.identifier = identifier;
    }

    public BasicPolymerPressurePlate(String path, BlockSetType blockSet, Settings settings) {
        this(Touhou.id(path), blockSet, settings);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return template.getDefaultState().with(POWERED, state.get(POWERED));
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new Model(initialBlockState, this.getIdentifier());
    }

    public static final class Model extends BlockModel {
        public final ItemStack MODEL_UNPOWERED;
        public final ItemStack MODEL_POWERED;
        public ItemDisplayElement main;
        private static final Queue<Model> toBeTicked = new ArrayDeque<>();
        private static final Timer timer = new Timer("Polywood: Pressure Plate Update Timer");

        public Model(BlockState state, Identifier id) {
            MODEL_UNPOWERED = ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(), "block/" + id.getPath()));
            MODEL_POWERED = ItemDisplayElementUtil.getModel(Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_down"));

            main = ItemDisplayElementUtil.createSimple();
            this.updateItem(state.get(POWERED));
            addElement(main);
        }

        private void updateItem(boolean powered) {
            main.setItem(powered ? MODEL_POWERED : MODEL_UNPOWERED);
            float scale = 1.0025f;
            main.setScale(new Vector3f(powered ? scale : 2 * scale));
            float scaleOffset = (scale - 1) / 2;
            main.setTranslation(new Vector3f(0, scaleOffset, 0));
        }

        @Override
        public void notifyUpdate(HolderAttachment.UpdateType updateType) {
            if (updateType == BlockBoundAttachment.BLOCK_STATE_UPDATE) {
                if (!this.blockState().get(POWERED)) {
                    this.updateItem(false);
                    this.tick();
                } else { // This fixes the regular pressure plate flashing for a brief moment when activated
                    toBeTicked.add(this);

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!toBeTicked.isEmpty()) {
                                Model model = toBeTicked.peek();
                                model.updateItem(true);
                                model.tick();
                                toBeTicked.remove();
                            }
                        }
                    }, 100);
                }
            }
            super.notifyUpdate(updateType);
        }
    }
}
