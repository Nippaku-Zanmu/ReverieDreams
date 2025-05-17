package cc.thonly.touhoumod.item.base;

import cc.thonly.touhoumod.Touhou;
import cc.thonly.touhoumod.util.IdentifierGetter;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

@Setter
@Getter
@ToString
public class BasicPolymerBlockItem extends BlockItem implements PolymerItem, PolymerClientDecoded, PolymerKeepModel, IdentifierGetter {
    final Identifier identifier;
    final Item vanillaItem;

    public BasicPolymerBlockItem(String path, Block block, Settings settings, Item vanillaItem) {
        this(Touhou.id(path), block, settings, vanillaItem);
    }

    public BasicPolymerBlockItem(Identifier identifier, Block block, Settings settings, Item vanillaItem) {
        super(block, settings.useBlockPrefixedTranslationKey().registryKey(RegistryKey.of(RegistryKeys.ITEM, identifier)));
        this.identifier = identifier;
        this.vanillaItem = vanillaItem;
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
