package cc.thonly.touhoumod.util;


import net.minecraft.entity.Entity;

public interface EntryLookup<T extends Entity> {
    EntryLookup<T> isRef(T reference);
}
