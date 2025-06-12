package cc.thonly.reverie_dreams.util;

import cc.thonly.reverie_dreams.Touhou;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ImageToTextScanner {
    private static ImageToTextScanner INSTANCE;

    private final Map<Integer, List<Text>> cache = new HashMap<>();
    private static final Class<?> CLAZZ = Touhou.class;

    private ImageToTextScanner() {
    }

    public static void registerBuffer() {
        CompletableFuture.runAsync(ImageToTextScanner::preload);
    }

    private static void preload() {
        ImageToTextScanner instance = ImageToTextScanner.getInstance();
        instance.loadImageFromJar("/assets/" + Touhou.MOD_ID + "/icon.png");
    }

    public static synchronized ImageToTextScanner getInstance() {
        synchronized (ImageToTextScanner.class) {
            if (INSTANCE == null) {
                INSTANCE = new ImageToTextScanner();
            }
        }
        return INSTANCE;
    }

    public List<Text> renderImageToText(BufferedImage image, int width, int height) {
        int key = Objects.hash(width, height, imageHash(image));
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        List<Text> lines = new ArrayList<>();
        for (int y = 0; y < Math.min(height, image.getHeight()); y++) {
            MutableText line = Text.literal("");
            for (int x = 0; x < Math.min(width, image.getWidth()); x++) {
                int argb = image.getRGB(x, y);
                int alpha = (argb >> 24) & 0xFF;
                if (alpha < 128) {
                    line.append(Text.literal(" ").setStyle(Style.EMPTY.withColor(Formatting.BLACK)));
                    continue;
                }
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = argb & 0xFF;
                line.append(
                        Text.literal("█").setStyle(
                                Style.EMPTY.withColor(TextColor.fromRgb((red << 16) | (green << 8) | blue))
                        )
                );
            }
            lines.add(line);
        }

        cache.put(key, lines);
        return lines;
    }

    public BufferedImage loadImageFromJar(String pathInJar) {
        try (InputStream stream = CLAZZ.getResourceAsStream(pathInJar)) {
            if (stream == null) {
                throw new IllegalArgumentException("Resource not found in JAR: " + pathInJar);
            }
            return ImageIO.read(stream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image from JAR: " + pathInJar, e);
        }
    }

    private int imageHash(BufferedImage image) {
        int hash = 7;
        for (int y = 0; y < image.getHeight(); y += 4) {
            for (int x = 0; x < image.getWidth(); x += 4) {
                hash = 31 * hash + image.getRGB(x, y);
            }
        }
        return hash;
    }

    public void clearCache() {
        cache.clear();
    }
}
