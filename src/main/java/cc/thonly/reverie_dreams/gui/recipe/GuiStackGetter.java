package cc.thonly.reverie_dreams.gui.recipe;

import cc.thonly.reverie_dreams.gui.server.BasePageGui;

@FunctionalInterface
public interface GuiStackGetter {
    void apply(BasePageGui gui, int slotIndex);
}