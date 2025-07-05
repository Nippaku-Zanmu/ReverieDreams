package cc.thonly.reverie_dreams.mixin.patches;

import eu.pb4.polymer.resourcepack.impl.generation.DefaultRPBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;

@Pseudo
@Mixin(value = DefaultRPBuilder.class, remap = false)
public class DefaultRPBuilderMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/nio/file/Files;deleteIfExists(Ljava/nio/file/Path;)Z"))
    public void fixIO(Path outputPath, CallbackInfo ci) {
        try {
            File file = new File(outputPath.toUri());
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            FileChannel channel = raf.getChannel();

            FileLock lock = channel.tryLock();

            if (lock != null) {
                lock.release();
            }
            channel.close();
            raf.close();
        } catch (Exception ignored) {
        }
    }
}
