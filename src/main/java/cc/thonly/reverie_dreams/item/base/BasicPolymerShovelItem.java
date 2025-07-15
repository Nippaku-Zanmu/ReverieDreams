package cc.thonly.reverie_dreams.item.base;

import cc.thonly.reverie_dreams.Touhou;
import cc.thonly.reverie_dreams.util.IdentifierGetter;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public abstract class BasicPolymerShovelItem extends ShovelItem implements PolymerItem, PolymerClientDecoded, PolymerKeepModel, IdentifierGetter {
    final Identifier identifier;
    final Item vanillaItem = Items.DIAMOND_SHOVEL;
    public static final List<BasicPolymerShovelItem> ITEMS = new ArrayList<>();

    public BasicPolymerShovelItem(String path, ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        this(Touhou.id(path), material, attackDamage, attackSpeed, settings);
    }

    public BasicPolymerShovelItem(Identifier identifier, ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));
        this.identifier = identifier;
        ITEMS.add(this);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext packetContext) {
        return this.vanillaItem;
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
        ItemStack stack = PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, context);
        return stack;
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack stack, PacketContext context) {
        return this.identifier;
    }

}
