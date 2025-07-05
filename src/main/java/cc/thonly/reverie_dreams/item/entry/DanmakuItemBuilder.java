package cc.thonly.reverie_dreams.item.entry;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.BasicDanmakuItemTypeItem;
import cc.thonly.reverie_dreams.item.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.Item;

import java.util.List;

@Setter
@Getter
@ToString
@Deprecated
public class DanmakuItemBuilder {
    public static final Codec<DanmakuItemBuilder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("path").forGetter(DanmakuItemBuilder::getPath),
            DanmakuColor.LIST_CODEC.fieldOf("colors").forGetter(DanmakuItemBuilder::getColors),
            Codec.FLOAT.fieldOf("damage").forGetter(DanmakuItemBuilder::getDamage),
            Codec.FLOAT.fieldOf("scale").forGetter(DanmakuItemBuilder::getScale),
            Codec.FLOAT.fieldOf("speed").forGetter(DanmakuItemBuilder::getSpeed),
            Codec.BOOL.fieldOf("tile").forGetter(DanmakuItemBuilder::isTile),
            Codec.BOOL.fieldOf("infinite").forGetter(DanmakuItemBuilder::isInfinite)
    ).apply(instance, DanmakuItemBuilder::new));

    String path;
    List<DanmakuColor> colors;
    float damage;
    float scale;
    float speed;
    boolean tile;
    boolean infinite;

    protected DanmakuItemBuilder(String path, List<DanmakuColor> colors, float damage, float scale, float speed, boolean tile, boolean infinite) {
        this.path = path;
        this.colors = colors;
        this.damage = damage;
        this.scale = scale;
        this.speed = speed;
        this.tile = tile;
        this.infinite = infinite;
    }

    public DanmakuItemType build() {
        DanmakuItemType entry = new DanmakuItemType();
        for (DanmakuColor color : this.colors) {
            String itemPath = "bullet/" + this.path + "/" + color.getIndex();
            BasicDanmakuItemTypeItem item = new BasicDanmakuItemTypeItem(
                    itemPath,
                    new Item.Settings()
                            .component(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString())
                            .component(ModDataComponentTypes.Danmaku.DAMAGE, this.damage)
                            .component(ModDataComponentTypes.Danmaku.SPEED, this.speed)
                            .component(ModDataComponentTypes.Danmaku.SCALE, this.scale)
                            .component(ModDataComponentTypes.Danmaku.COUNT, 1)
                            .component(ModDataComponentTypes.Danmaku.TILE, this.tile)
                            .component(ModDataComponentTypes.Danmaku.INFINITE, this.infinite)
                            .component(ModDataComponentTypes.Danmaku.DAMAGE_TYPE, Touhou.id("generic").toString())
                            .maxDamage(120)
//                                .useCooldown(0.5f)
            );
            ModItems.registerDanmakuItem(item);
            entry.addItem(item);
        }
        return entry;
    }
}
