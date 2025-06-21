package cc.thonly.reverie_dreams.mixin.eiv;

//import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.fabric.impl.registry.sync.packet.DirectRegistryPacketHandler;
//import net.minecraft.registry.Registries;
//import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.ModifyVariable;

//import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = DirectRegistryPacketHandler.class, remap = false, priority = 1)
@Pseudo
public class DirectRegistrySyncManagerEIVMixin {
//    @ModifyVariable(
//            method = "sendPacket",
//            at = @At("HEAD"),
//            argsOnly = true
//    )
//    private Map<Identifier, Object2IntMap<Identifier>> modifyIds(Map<Identifier, Object2IntMap<Identifier>> ids) {
//        Object2IntMap<Identifier> map = ids.get(Registries.SCREEN_HANDLER.getKey().getValue());
//        Identifier eiv = Identifier.of("eiv", "recipe_view");
//        map.removeInt(eiv);
//        return ids;
//    }
}
