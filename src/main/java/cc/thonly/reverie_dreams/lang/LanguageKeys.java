package cc.thonly.reverie_dreams.lang;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.entity.ModEntities;
import lombok.Getter;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum LanguageKeys {
    SIMP_CHINESE("zh_cn"),
    ENGLISH("en_us"),
    ;

    private final String key;
    private final Map<Object, String> objectStringMap = new HashMap<>();
    LanguageKeys(String key) {
        this.key = key;
    }
    public LanguageKeys add(Object object, String text) {
        objectStringMap.put(object, text);
        return this;
    }
    public LanguageKeys add(Item item, String text) {
        objectStringMap.put(item.getTranslationKey(), text);
        return this;
    }
    public LanguageKeys add(Block block, String text) {
        objectStringMap.put(block.getTranslationKey(), text);
        return this;
    }
    public LanguageKeys addEntity(EntityType<?> npc, String text, String spawnEggText) {
        String entity_id = npc.getTranslationKey();
        String item_id = "item"+"."+ Touhou.MOD_ID +"."+ EntityType.getId(npc) + "_spawn_egg";
        String item_value = text + spawnEggText;

        objectStringMap.put(entity_id, text);
        objectStringMap.put(item_id, item_value);
        return this;
    }
    public LanguageKeys addNpcEntity(ModEntities.NPCEntityTypes npc, String text, String spawnEggText) {
        String entity_id = "entity"+"."+ Touhou.MOD_ID +"."+npc.getId();
        String item_id = "item"+"."+ Touhou.MOD_ID +"."+npc.getId() + "_spawn_egg";
        String item_value = text + spawnEggText;

        objectStringMap.put(entity_id, text);
        objectStringMap.put(item_id, item_value);
        return this;
    }
    public LanguageKeys addNpcEntity(ModEntities.NPCEntityTypes npc, String text, String spawnEggText, boolean isReverse) {
        String entity_id = "entity"+"."+ Touhou.MOD_ID +"."+npc.getId();
        String item_id = "item"+"."+ Touhou.MOD_ID +"."+npc.getId() + "_spawn_egg";
        String item_value = text + spawnEggText;
        if(isReverse) item_value = spawnEggText + text;

        objectStringMap.put(entity_id, text);
        objectStringMap.put(item_id, item_value);
        return this;
    }
    public FabricLanguageProvider.TranslationBuilder build(FabricLanguageProvider.TranslationBuilder translationBuilder) {
        for (var entry : objectStringMap.entrySet()) {
            translationBuilder.add(entry.getKey().toString(), entry.getValue());
        }
        return translationBuilder;
    }
}
