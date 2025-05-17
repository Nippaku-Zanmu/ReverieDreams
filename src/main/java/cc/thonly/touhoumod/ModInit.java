package cc.thonly.touhoumod;

import cc.thonly.touhoumod.block.FumoBlocks;
import cc.thonly.touhoumod.block.ModBlocks;
import cc.thonly.touhoumod.block.entity.ModBlockEntities;
import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.data.ModTags;
import cc.thonly.touhoumod.effect.ModStatusEffects;
import cc.thonly.touhoumod.entity.ModEntities;
import cc.thonly.touhoumod.entity.ModEntityHolders;
import cc.thonly.touhoumod.item.ModGuiItems;
import cc.thonly.touhoumod.item.ModItemGroups;
import cc.thonly.touhoumod.item.ModItems;
import cc.thonly.touhoumod.sound.ModJukeboxSongs;
import cc.thonly.touhoumod.sound.ModSoundEvents;
import cc.thonly.touhoumod.world.gen.ModWorldGeneration;

public class ModInit {
    public static void init() {
        ModSoundEvents.init();
        ModJukeboxSongs.init();
        ModDataComponentTypes.init();
        ModGuiItems.init();
        ModBlockEntities.registerBlockEntities();
        ModBlocks.registerBlocks();
        FumoBlocks.registerFumoBlocks();
        ModItems.registerItems();
        ModItemGroups.registerItemGroups();
        ModEntityHolders.registerHolders();
        ModEntities.registerEntities();
        ModStatusEffects.init();
        ModTags.registerTags();
        ModWorldGeneration.registerModGen();
    }
}
