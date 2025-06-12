package cc.thonly.reverie_dreams.gui.recipe.entry;

import cc.thonly.reverie_dreams.block.ModBlocks;
import cc.thonly.reverie_dreams.block.entity.GensokyoAltarBlockEntity;
import cc.thonly.reverie_dreams.item.ModGuiItems;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.block.BlockState;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GensokyoAltarGui extends SimpleGui {
    public static final String[][] GRID = {
            {"X", "X", "I", "X", "I", "X", "I", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "I", "X", "E", "X", "I", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
            {"X", "X", "I", "X", "I", "X", "I", "X", "X"},
    };
    ServerPlayerEntity player;
    GensokyoAltarBlockEntity blockEntity;
    BlockState state;
    BlockPos pos;

    public GensokyoAltarGui(ServerPlayerEntity player, BlockState state, World world, BlockPos pos) {
        super(ScreenHandlerType.GENERIC_9X5, player, false);
        this.player = player;
        this.state = state;
        this.blockEntity = (GensokyoAltarBlockEntity) world.getBlockEntity(pos);
        this.pos = pos;
        this.init();
    }

    public void init() {
        this.setTitle(Text.translatable(ModBlocks.GENSOKYO_ALTAR.getTranslationKey()));
        int invSlot = 0;
        for (int row = 0; row < GRID.length; row++) {
            for (int col = 0; col < GRID[row].length; col++) {
                String pos = GRID[row][col];
                int index = row * 9 + col;
                if (pos.equalsIgnoreCase("X")) {
                    this.setSlot(index, new GuiElementBuilder()
                            .setItem(ModGuiItems.EMPTY_SLOT));
                    continue;
                }
                if (pos.equalsIgnoreCase("I")) {
                    this.setSlotRedirect(index, new Slot(blockEntity.getInventory(), invSlot, 0, 0));
                    invSlot++;
                    continue;
                }
                if (pos.equalsIgnoreCase("E")) {
                    this.setSlotRedirect(index, new Slot(blockEntity.getInventory(), 8, 0, 0));
                    continue;
                }
            }
        }
    }

    @Override
    public void onTick() {
        super.onTick();
        if (blockEntity == null) return;
        if (blockEntity.getWorld() != null && blockEntity.getWorld().getBlockState(blockEntity.getPos()).getBlock() != ModBlocks.GENSOKYO_ALTAR) {
            this.close();
            return;
        }
    }

    @Override
    public boolean onAnyClick(int index, ClickType type, SlotActionType action) {
        return super.onAnyClick(index, type, action);
    }

    @Override
    public void onOpen() {
        super.onOpen();
    }

    @Override
    public void onClose() {
        super.onClose();

        blockEntity.markDirty();
    }
}
