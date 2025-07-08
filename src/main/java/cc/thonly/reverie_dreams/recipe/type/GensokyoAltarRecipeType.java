package cc.thonly.reverie_dreams.recipe.type;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.recipe.BaseRecipeType;
import cc.thonly.reverie_dreams.recipe.entry.GensokyoAltarRecipe;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class GensokyoAltarRecipeType extends BaseRecipeType<GensokyoAltarRecipe> {
    private static GensokyoAltarRecipeType INSTANCE;

    public GensokyoAltarRecipeType() {
        INSTANCE = this;
    }

    public static synchronized GensokyoAltarRecipeType getInstance() {
        return INSTANCE;
    }

    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> resources = manager.findResources((this.getTypeId() + "_recipe"), id -> {
            return id.getNamespace().equals(Touhou.MOD_ID) && id.getPath().endsWith(".json");
        });
        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            Identifier id = entry.getKey();
            Identifier registryKey = Identifier.of(id.getNamespace(), id.getPath().replaceFirst("^gensokyo_altar_recipe/", "").replaceAll("\\.json$", ""));
            Resource resource = entry.getValue();
            try (InputStream stream = resource.getInputStream()) {
                JsonElement json = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                Dynamic<JsonElement> input = new Dynamic<>(JsonOps.INSTANCE, json);

                DataResult<GensokyoAltarRecipe> result = this.getCodec().parse(input);

                result.resultOrPartial(error -> log.error("Failed to load gensokyo altar recipe {}, {}", id, error))
                        .ifPresent(recipe -> {
                            this.add(registryKey, recipe);
                        });
            } catch (IOException e) {
                log.error("Failed to load gensokyo altar recipe {}, {}, {}", id, e.getMessage(), e);
            }
        }
    }

    @Override
    public void bootstrap() {

    }

    @Override
    public List<GensokyoAltarRecipe> getMatches(List<ItemStackRecipeWrapper> wrappers) {
        if (wrappers.size() < 8) return List.of();
        List<GensokyoAltarRecipe> matches = new ArrayList<>();

        for (GensokyoAltarRecipe recipe : stream().toList()) {
            List<ItemStackRecipeWrapper> slots = recipe.getSlots();
            ItemStackRecipeWrapper slot0 = slots.get(0);
            ItemStackRecipeWrapper slot1 = slots.get(1);
            ItemStackRecipeWrapper slot2 = slots.get(2);
            ItemStackRecipeWrapper slot3 = slots.get(3);
            ItemStackRecipeWrapper slot4 = slots.get(4);
            ItemStackRecipeWrapper slot5 = slots.get(5);
            ItemStackRecipeWrapper slot6 = slots.get(6);
            ItemStackRecipeWrapper slot7 = slots.get(7);
            ItemStackRecipeWrapper slot8 = recipe.getCore();
            if (
                    wrappers.get(0).test(slot0.getItemStack()) &&
                    wrappers.get(1).test(slot1.getItemStack()) &&
                    wrappers.get(2).test(slot2.getItemStack()) &&
                    wrappers.get(3).test(slot3.getItemStack()) &&
                    wrappers.get(4).test(slot4.getItemStack()) &&
                    wrappers.get(5).test(slot5.getItemStack()) &&
                    wrappers.get(6).test(slot6.getItemStack()) &&
                    wrappers.get(7).test(slot7.getItemStack()) &&
                    wrappers.get(8).test(slot8.getItemStack())
            ) {
                matches.add(recipe);
            }
        }
        return matches;
    }

    @Override
    public Boolean isMatch(ItemStackRecipeWrapper input, ItemStackRecipeWrapper recipe) {
        return false;
    }

    @Override
    public Codec<GensokyoAltarRecipe> getCodec() {
        return GensokyoAltarRecipe.CODEC;
    }

    @Override
    public String getTypeId() {
        return "gensokyo_altar";
    }

    @Override
    public Identifier getId() {
        return Touhou.id(this.getTypeId());
    }
}
