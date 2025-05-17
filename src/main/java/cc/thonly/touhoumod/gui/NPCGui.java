package cc.thonly.touhoumod.gui;

import cc.thonly.touhoumod.entity.npc.NPCEntityImpl;
import cc.thonly.touhoumod.item.ModGuiItems;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;

public class NPCGui extends SimpleGui implements GuiCommon {
    public static final String[][] GRID = {
            {"I", "I", "I", "I", "I", "I", "X", "Q", "W"},
            {"I", "I", "I", "I", "I", "I", "X", "E", "R"},
            {"I", "I", "I", "I", "I", "I", "X", "T", "Y"},
            {"X", "X", "X", "X", "X", "X", "X", "U", "I"},
            {"I", "I", "I", "I", "I", "I", "X", "X", "X"},
            {"X", "X", "X", "X", "X", "X", "X", "X", "X"},
    };
    private final ServerPlayerEntity player;
    private final NPCEntityImpl npcEntity;

    private GuiElementBuilder npcName;
    private GuiElementBuilder npcMode;
    private GuiElementBuilder npcFood;
    private GuiElementBuilder npcHealth;
    private GuiElementBuilder npcArmor;

    public NPCGui(ServerPlayerEntity player, NPCEntityImpl npcEntity) {
        super(ScreenHandlerType.GENERIC_9X6, player, false);
        this.player = player;
        this.npcEntity = npcEntity;
        init();
    }

    public void init() {
        int inventory_index = 0;

        this.setTitle(getNpcName());
        for (int row = 0; row < GRID.length; row++) {
            for (int col = 0; col < GRID[row].length; col++) {
                Integer slotIndex = row * 9 + col;
                String pos = GRID[row][col];
                if (pos.equalsIgnoreCase("X")) {
                    this.setSlot(slotIndex, new GuiElementBuilder()
                            .setItem(ModGuiItems.EMPTY_SLOT));
                }
                if (pos.equalsIgnoreCase("Q")) {
                    this.npcName = new GuiElementBuilder()
                            .setItem(Items.NAME_TAG)
                            .setItemName(Text.translatable("gui.npc.info"))
                            .setCallback((index, type, action) -> {
                                player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                            });
                    this.setSlot(slotIndex, this.npcName);
                }
                if (pos.equalsIgnoreCase("W")) {
                    this.npcFood = new GuiElementBuilder()
                            .setItem(Items.COOKED_CHICKEN)
                            .setItemName(Text.translatable("gui.npc.info"))
                            .setCallback((index, type, action) -> {
                                player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                            });
                    this.setSlot(slotIndex, this.npcFood);
                }
                if (pos.equalsIgnoreCase("E")) {
                    this.npcHealth = new GuiElementBuilder()
                            .setItem(Items.GOLDEN_APPLE)
                            .setItemName(Text.translatable("gui.npc.info"))
                            .setCallback((index, type, action) -> {
                                player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                            });
                    this.setSlot(slotIndex, this.npcHealth);
                }
                if (pos.equalsIgnoreCase("R")) {
                    this.npcArmor = new GuiElementBuilder()
                            .setItem(Items.IRON_HELMET)
                            .setItemName(Text.translatable("gui.npc.info", getNpcName().getString()))
                            .setCallback((index, type, action) -> {
                                player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                            });
                    this.setSlot(slotIndex, this.npcArmor);
                }
                if (pos.equalsIgnoreCase("T")) {
                    this.npcMode = new GuiElementBuilder()
                            .setItem(Items.DIAMOND)
                            .setItemName(Text.of("模式开关"))
                            .setLore(List.of
                                    (
                                            Text.translatable("gui.npc.mode." + npcEntity.getNpcState().getId())
                                    )
                            )
                            .setCallback((index, type, action) -> {
                                npcEntity.setNpcState(npcEntity.getNextState());
                                player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);
                            })
                    ;
                    this.setSlot(slotIndex, this.npcMode);
                }
                if (pos.equalsIgnoreCase("I")) {
                    this.setSlotRedirect(
                            slotIndex,
                            new Slot(npcEntity.getInventory(), inventory_index, 0, 0)
                    );
                    inventory_index++;
                }
            }
        }
    }

    public Text getNpcName() {
        return this.npcEntity.hasCustomName() ? this.npcEntity.getCustomName() : this.npcEntity.getName();
    }

    @Override
    public void onTick() {
        super.onTick();
        this.npcName.setItemName(Text.translatable("gui.npc.info.name", getNpcName().getString()));
        this.npcFood.setItemName(Text.translatable("gui.npc.info.food"));
        this.npcFood.setLore(
                List.of(
                        Text.translatable("gui.npc.info.food.nutrition", this.npcEntity.getNutrition() + " / 20.0"),
                        Text.translatable("gui.npc.info.food.saturation", this.npcEntity.getSaturation() + " / 20.0")
                )
        );
        this.npcHealth.setItemName(Text.translatable("gui.npc.info.health", npcEntity.getHealth(), npcEntity.getMaxHealth()));
        this.npcArmor.setItemName(Text.translatable("gui.npc.info.armor", npcEntity.getArmor()));
        this.npcMode.setLore(List.of
                (
                        Text.translatable("gui.npc.mode." + npcEntity.getNpcState().getId())
                )
        );
    }

    @Override
    public void onOpen() {
        super.onOpen();
        npcEntity.setPause(true);
    }

    @Override
    public void onClose() {
        super.onClose();
        npcEntity.setPause(false);
    }

    public static int size() {
        int result = 0;
        for (String[] strings : GRID) {
            for (String pos : strings) {
                if (pos.equalsIgnoreCase("I")) {
                    result++;
                }
            }
        }
        return result;
    }
}
