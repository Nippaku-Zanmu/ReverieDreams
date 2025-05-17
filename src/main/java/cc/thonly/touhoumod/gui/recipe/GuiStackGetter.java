package cc.thonly.touhoumod.gui.recipe;

import cc.thonly.touhoumod.gui.server.BasePageGui;

@FunctionalInterface
public interface GuiStackGetter {
    void apply(BasePageGui gui, int slotIndex);
}