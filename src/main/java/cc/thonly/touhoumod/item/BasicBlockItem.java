package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.item.base.BasicPolymerBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BasicBlockItem extends BasicPolymerBlockItem {
    private static final Map<BlockSoundGroup, Item> SOUND_GROUP_CACHES = new HashMap<>();

    public BasicBlockItem(Identifier identifier, Block block, Settings settings) {
        super(identifier, block, settings, getBlockItemBase(block));
    }

    public BasicBlockItem(Identifier identifier, Block block, Item item, Settings settings) {
        super(identifier, block, settings, item);
    }

    public BasicBlockItem(String path, Block block, Settings settings) {
        super(path, block, settings, Items.BARRIER);
    }

    public BasicBlockItem(String path, Block block, Item item, Settings settings) {
        super(path, block, settings, item);
    }

    public static Item getBlockItemBase(Block block) {
        return Items.BARRIER;
//        AbstractBlock.Settings settings = block.getSettings();
//        AbstractBlockSettingsAccessorImpl settingsImpl = (AbstractBlockSettingsAccessorImpl) settings;
//        BlockSoundGroup soundGroup = settingsImpl.getSoundGroup();
//        Item blockItem = Items.BARRIER;
//        if (SOUND_GROUP_CACHES.containsKey(soundGroup)) {
//            blockItem = SOUND_GROUP_CACHES.get(soundGroup);
//        }
//        List<Block> blocks = Registries.BLOCK.stream().toList();
//        for (Block registryBlock : blocks) {
//            if (registryBlock.asItem() == Items.AIR) continue;
//            AbstractBlock.Settings settings1 = registryBlock.getSettings();
//            AbstractBlockSettingsAccessorImpl settingsImpl1 = (AbstractBlockSettingsAccessorImpl) settings1;
//            BlockSoundGroup soundGroup1 = settingsImpl1.getSoundGroup();
//
//            if (soundGroup.equals(soundGroup1)) {
//                blockItem = registryBlock.asItem();
//                SOUND_GROUP_CACHES.put(soundGroup, blockItem);
//                break;
//            }
//        }
//        return blockItem;
    }
}
