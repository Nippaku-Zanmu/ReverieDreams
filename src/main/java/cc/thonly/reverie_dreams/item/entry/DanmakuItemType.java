package cc.thonly.reverie_dreams.item.entry;

import cc.thonly.reverie_dreams.item.BasicDanmakuItemTypeItem;
import com.mojang.serialization.Codec;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Setter
@Getter
@ToString
@Deprecated
public class DanmakuItemType {
    public static final Codec<DanmakuItemType> CODEC = DanmakuItemBuilder.CODEC.xmap(
            DanmakuItemBuilder::build,
            danmakuItemType ->
            {
                throw new UnsupportedOperationException("Cannot serialize DanmakuItemType back to builder.");
            }
    );
    private final Random random = new Random();
    protected List<BasicDanmakuItemTypeItem> values = new ArrayList<>();

    public void addItem(BasicDanmakuItemTypeItem item) {
        this.values.add(item);
    }

    public BasicDanmakuItemTypeItem random() {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(random.nextInt(values.size()));
    }

    public static DanmakuItemBuilder createBuilder(String path, List<DanmakuColor> colors, float damage, float scale, float speed, boolean tile, boolean infinite) {
        return new DanmakuItemBuilder(path, colors, damage, scale, speed, tile, infinite);
    }

}