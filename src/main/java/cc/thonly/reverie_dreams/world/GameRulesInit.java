package cc.thonly.reverie_dreams.world;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameRulesInit {
    public static final GameRules.Key<GameRules.BooleanRule> DO_GHOST = GameRuleRegistry.register(
            "doGhost",
            GameRules.Category.MOBS,
            GameRuleFactory.createBooleanRule(true)

    );
    public static void init() {

    }
}
