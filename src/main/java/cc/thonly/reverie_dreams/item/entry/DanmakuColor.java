package cc.thonly.reverie_dreams.item.entry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

@Getter
@ToString
public enum DanmakuColor {
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
    public static final Codec<DanmakuColor> CODEC = Codec.STRING.comapFlatMap(
            id -> {
                DanmakuColor match = fromId(id);
                return match != null
                        ? DataResult.success(match)
                        : DataResult.error(() -> "Unknown DanmakuColor id: " + id);
            },
            DanmakuColor::getId
    );

    public static final Codec<List<DanmakuColor>> LIST_CODEC = CODEC.listOf();
    // 弹幕颜色
    public static final List<DanmakuColor> ALL_COLOR = List.of(
            BLACK,
            DARK_RED,
            RED,
            DARK_PURPLE,
            PURPLE,
            DARK_BLUE,
            BLUE,
            DARK_CYAN,
            CYAN,
            DARK_GREEN,
            GREEN,
            DARK_YELLOW_GREEN,
            YELLOW_GREEN,
            YELLOW,
            ORANGE,
            GREY
    );

    private final int index;
    private final String id;
    private final String chineseTranslation;
    private final String englishTranslation;
    private final Item dye;

    DanmakuColor(int index, String id, String chineseTranslation, String englishTranslation, Item dye) {
        this.index = index;
        this.id = id;
        this.chineseTranslation = chineseTranslation;
        this.englishTranslation = englishTranslation;
        this.dye = dye;
    }

    public static DanmakuColor fromIndex(int index) {
        for (DanmakuColor color : values()) {
            if (color.getIndex() == index) {
                return color;
            }
        }
        return null;
    }

    public static DanmakuColor fromId(String id) {
        for (DanmakuColor color : values()) {
            if (color.getId().equalsIgnoreCase(id)) {
                return color;
            }
        }
        return null;
    }
}
