package cc.thonly.touhoumod.item;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.component.ModDataComponentTypes;
import cc.thonly.touhoumod.item.base.BasicPolymerDanmakuItem;
import cc.thonly.touhoumod.item.base.UsingDanmaku;
import cc.thonly.touhoumod.registry.RegistrySchemas;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class BasicDanmakuItem extends BasicPolymerDanmakuItem {
    public BasicDanmakuItem(String path, Settings settings) {
        super(path, settings, Items.SNOWBALL);
    }

    @Override
    public void shoot(ServerWorld serverWorld, PlayerEntity user, Hand hand) {
        String templateType = user.getStackInHand(hand).getOrDefault(ModDataComponentTypes.Danmaku.TEMPLATE, Touhou.id("single").toString());
        UsingDanmaku usingDanmaku = RegistrySchemas.USING_DANMAKU.get(Identifier.of(templateType));
        usingDanmaku.use(serverWorld, user, hand, this);
     }
}
