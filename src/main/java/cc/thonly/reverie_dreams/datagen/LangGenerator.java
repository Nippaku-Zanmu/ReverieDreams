package cc.thonly.reverie_dreams.datagen;

import cc.thonly.reverie_dreams.danmaku.DanmakuTrajectory;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface LangGenerator {
    public default void generateDanmakuType(FabricLanguageProvider.TranslationBuilder translationBuilder, DanmakuTrajectory trajectory, String value) {
        translationBuilder.add(trajectory.getId().toTranslationKey(), value);
    }
    public default void generateJukeBox(FabricLanguageProvider.TranslationBuilder translationBuilder, RegistryKey<JukeboxSong> key, String value) {
        translationBuilder.add(this.getSoundEventSubtitle(key), value);
        translationBuilder.add(this.getJukeBoxSongDisc(key), value);
    }

    public default void generateStatusEffect(FabricLanguageProvider.TranslationBuilder translationBuilder, RegistryEntry<StatusEffect> registryEntry, String value) {
        translationBuilder.add(getStatusEffect(registryEntry), value);
    }

    public default void generatePotion(FabricLanguageProvider.TranslationBuilder translationBuilder,
                                       Potion registryEntry,
                                       String potion,
                                       String splash,
                                       String lingering
    ) {
        translationBuilder.add(getPotion(registryEntry), potion);
        translationBuilder.add(getSplashPotion(registryEntry), splash);
        translationBuilder.add(getLingeringPotion(registryEntry), lingering);
    }

    public default void generateSoundEventSubtitle(FabricLanguageProvider.TranslationBuilder translationBuilder, SoundEvent soundEvent, String value) {
        translationBuilder.add(getSoundEventSubtitle(soundEvent), value);
    }

    public default String getStatusEffect(RegistryEntry<StatusEffect> registryEntry) {
        StringBuilder sb = new StringBuilder();
        String idAsString = registryEntry.getIdAsString();
        idAsString = idAsString.replaceAll(":", ".");
        idAsString = idAsString.replaceAll("/", ".");
        sb = sb.append("effect.");
        sb = sb.append(idAsString);
        return sb.toString();
    }

    public default String getPotion(Potion registryEntry) {
        StringBuilder sb = new StringBuilder();
        String idAsString = registryEntry.getBaseName();
        idAsString = idAsString.replaceAll(":", ".");
        idAsString = idAsString.replaceAll("/", ".");
        sb = sb.append("item.minecraft.potion.effect.");
        sb = sb.append(idAsString);
        return sb.toString();
    }

    public default String getSplashPotion(Potion registryEntry) {
        StringBuilder sb = new StringBuilder();
        String idAsString = registryEntry.getBaseName();
        idAsString = idAsString.replaceAll(":", ".");
        idAsString = idAsString.replaceAll("/", ".");
        sb = sb.append("item.minecraft.splash_potion.effect.");
        sb = sb.append(idAsString);
        return sb.toString();
    }

    public default String getLingeringPotion(Potion registryEntry) {
        StringBuilder sb = new StringBuilder();
        String idAsString = registryEntry.getBaseName();
        idAsString = idAsString.replaceAll(":", ".");
        idAsString = idAsString.replaceAll("/", ".");
        sb = sb.append("item.minecraft.lingering_potion.effect.");
        sb = sb.append(idAsString);
        return sb.toString();
    }

    public default String getSoundEventSubtitle(SoundEvent soundEvent) {
        Identifier id = soundEvent.id();
        return id.toTranslationKey("sound");
    }

    public default String getSoundEventSubtitle(RegistryKey<JukeboxSong> registryKey) {
        Identifier id = registryKey.getValue();
        return id.toTranslationKey("sound");
    }

    public default String getJukeBoxSongDisc(RegistryKey<JukeboxSong> registryKey) {
        Identifier key = registryKey.getValue();
        String namespace = key.getNamespace();
        String path = key.getPath().replaceAll("/", ".");
        return key.toTranslationKey("jukebox_song");
    }
}
