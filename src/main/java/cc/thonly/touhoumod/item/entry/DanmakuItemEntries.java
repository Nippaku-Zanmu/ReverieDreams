package cc.thonly.touhoumod.item.entry;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.item.BasicDanmakuItem;
import cc.thonly.touhoumod.item.ModItems;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@ToString
public class DanmakuItemEntries {
    private final Random random = new Random();
    protected List<BasicDanmakuItem> values = new ArrayList<>();

    public void addItem(BasicDanmakuItem item) {
        this.values.add(item);
    }

    public BasicDanmakuItem random() {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(random.nextInt(values.size()));
    }

    public static Builder createBuilder(String path, List<ColorEnum> colors, float damage, float scale, float speed, boolean tile, boolean infinite) {
        return new Builder(path, colors, damage, scale, speed, tile, infinite);
    }

    @Setter
    @Getter
    @ToString
    public static class Builder {
        String path;
        List<ColorEnum> colors;
        float damage;
        float scale;
        float speed;
        boolean tile;
        boolean infinite;

        protected Builder(String path, List<ColorEnum> colors, float damage, float scale, float speed, boolean tile, boolean infinite) {
            this.path = path;
            this.colors = colors;
            this.damage = damage;
            this.scale = scale;
            this.speed = speed;
            this.tile = tile;
            this.infinite = infinite;
        }

        public DanmakuItemEntries build() {
            DanmakuItemEntries entry = new DanmakuItemEntries();
            for (ColorEnum color : this.colors) {
                String itemPath = "bullet/" + this.path + "/" + color.getIndex();
                BasicDanmakuItem item = new BasicDanmakuItem(
                        itemPath,
                        new Item.Settings()
                                .component(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString())
                                .component(ModDataComponentTypes.Danmaku.DAMAGE, this.damage)
                                .component(ModDataComponentTypes.Danmaku.SPEED, this.speed)
                                .component(ModDataComponentTypes.Danmaku.SCALE, this.scale)
                                .component(ModDataComponentTypes.Danmaku.COUNT, 1)
                                .component(ModDataComponentTypes.Danmaku.COLOR, color.getIndex())
                                .component(ModDataComponentTypes.Danmaku.TILE, this.tile)
                                .component(ModDataComponentTypes.Danmaku.INFINITE, this.infinite)
                                .maxDamage(120)
                                .useCooldown(1f)
                );
                ModItems.registerDanmakuItem(item);
                entry.addItem(item);
            }
            return entry;
        }
    }

    @Getter
    @ToString
    public enum ColorEnum {
        BLACK(0, "black", "黑色", "Black", Items.BLACK_DYE),
        DARK_RED(1, "dark_red", "深红", "Dark Red", Items.RED_DYE),
        RED(2, "red", "红色", "Red", Items.RED_DYE),
        DARK_PURPLE(3, "dark_purple", "深紫", "Dark Purple", Items.PURPLE_DYE),
        PURPLE(4, "purple", "紫色", "Purple", Items.PURPLE_DYE),
        DARK_BLUE(5, "dark_blue", "深蓝", "Dark Blue", Items.BLUE_DYE),
        BLUE(6, "blue", "蓝色", "Blue", Items.BLUE_DYE),
        DARK_CYAN(7, "dark_cyan", "深青", "Dark Cyan", Items.CYAN_DYE),
        CYAN(8, "cyan", "青色", "Cyan", Items.CYAN_DYE),
        DARK_GREEN(9, "dark_green", "深绿", "Dark Green", Items.GREEN_DYE),
        GREEN(10, "green", "绿色", "Green", Items.GREEN_DYE),
        DARK_YELLOW_GREEN(11, "dark_yellow_green", "深黄绿", "Dark Yellow Green", Items.LIME_DYE),
        YELLOW_GREEN(12, "yellow_green", "黄绿色", "Yellow Green", Items.LIME_DYE),
        YELLOW(13, "yellow", "黄色", "Yellow", Items.YELLOW_DYE),
        ORANGE(14, "orange", "橙色", "Orange", Items.ORANGE_DYE),
        GREY(15, "grey", "灰色", "Grey", Items.GREEN_DYE),
        UNDEFINED(-1, "undefined", "Undefined", "Undefined", Items.WHITE_DYE);

        private final int index;
        private final String id;
        private final String chineseTranslation;
        private final String englishTranslation;
        private final Item dye;

        ColorEnum(int index, String id, String chineseTranslation, String englishTranslation, Item dye) {
            this.index = index;
            this.id = id;
            this.chineseTranslation = chineseTranslation;
            this.englishTranslation = englishTranslation;
            this.dye = dye;
        }

        public static ColorEnum fromIndex(int index) {
            for (ColorEnum color : values()) {
                if (color.getIndex() == index) {
                    return color;
                }
            }
            return null;
        }

        public static ColorEnum fromId(String id) {
            for (ColorEnum color : values()) {
                if (color.getId().equalsIgnoreCase(id)) {
                    return color;
                }
            }
            return null;
        }
    }
}