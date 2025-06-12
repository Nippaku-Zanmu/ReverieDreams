package cc.thonly.reverie_dreams.gui.recipe;

import cc.thonly.reverie_dreams.gui.server.BasePageGui;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.lang.reflect.Constructor;

@AllArgsConstructor
@Getter
@Slf4j
public class RecipeTypeGuiInfo<T extends BasePageGui> {
    private final ItemStack icon;
    private final Identifier id;
    private final Class<T> clazz;
    private final RecipeRegistryGetter registryGetter;
    private final GuiStackGetter getter;

    public void create(ServerPlayerEntity player, GuiOpeningPrevCallback callback) {
        try {
            Constructor<T> constructor = this.clazz.getConstructor(ServerPlayerEntity.class, RecipeTypeGuiInfo.class, RecipeTypeInfo.class, GuiOpeningPrevCallback.class);
            T gui = constructor.newInstance(player, this, new RecipeTypeInfo(this.id.toTranslationKey(), this.registryGetter.get(), this.getter), callback);
            gui.open();
        } catch (Exception exception) {
            log.error("Can't open gui", exception);
        }
    }
}
