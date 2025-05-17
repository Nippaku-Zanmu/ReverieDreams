package cc.thonly.touhoumod.util;

import cc.thonly.touhoumod.Touhou;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import net.minecraft.util.Identifier;

public class ModelUtil {
    public static Identifier id(String path) {
        return Touhou.id(path);
    }

    public static Model loadModel(Identifier id) {
        return BbModelLoader.load(id);
    }
}
