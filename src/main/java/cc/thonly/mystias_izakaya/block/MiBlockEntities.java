package cc.thonly.mystias_izakaya.block;

import cc.thonly.mystias_izakaya.block.entity.KitchenwareBlockEntity;
import cc.thonly.reverie_dreams.Touhou;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MiBlockEntities {
    public static final BlockEntityType<KitchenwareBlockEntity> KITCHENWARE_BLOCK_ENTITY =
            registerBlockEntity("cooking_pot_block_entity",
                    KitchenwareBlockEntity::new,
                    MIBlocks.COOKING_POT, MIBlocks.CUTTING_BOARD, MIBlocks.FRYING_PAN, MIBlocks.GRILL, MIBlocks.STEAMER
            );

    public static void registerBlockEntities() {

    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Touhou.id(name);
        BlockEntityType<T> entityType = Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
        PolymerBlockUtils.registerBlockEntity(entityType);
        return entityType;
    }
}
