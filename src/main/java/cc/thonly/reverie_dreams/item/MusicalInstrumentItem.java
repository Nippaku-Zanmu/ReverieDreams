package cc.thonly.reverie_dreams.item;

import cc.thonly.reverie_dreams.component.ModDataComponentTypes;
import cc.thonly.reverie_dreams.item.base.BasicPolymerItem;
import cc.thonly.reverie_dreams.util.TouhouNotaUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.World;

import java.util.List;

public class MusicalInstrumentItem extends BasicPolymerItem {
    public static final Codec<NoteBlockInstrument> NOTE_BLOCK_INSTRUMENT_CODEC = StringIdentifiable.createCodec(NoteBlockInstrument::values);

    public MusicalInstrumentItem(String path, Settings settings) {
        super(path, settings.maxCount(1), Items.TRIAL_KEY);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        boolean isSneaking = user.isSneaking();
        if (!world.isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            ItemStack itemStack = player.getStackInHand(hand);
            List<String> fileNames = TouhouNotaUtils.getFileNames();
            if (fileNames.isEmpty()) {
                player.sendMessage(Text.translatable("item.reverie_dreams.music.no_files"), false);
                return ActionResult.FAIL;
            }

            String playingMusic = itemStack.getOrDefault(ModDataComponentTypes.PLAYING_MUSIC, null);
            NoteBlockInstrument noteBlockInstrument = itemStack.getOrDefault(ModDataComponentTypes.NOTE_TYPE, NoteBlockInstrument.PLING);

            if (isSneaking) {
                int index = playingMusic == null ? -1 : fileNames.indexOf(playingMusic);
                index = (index + 1) % fileNames.size();
                String next = fileNames.get(index);
                itemStack.set(ModDataComponentTypes.PLAYING_MUSIC, next);
                player.sendMessage(Text.translatable("item.reverie_dreams.music.switch_music", next), false);
            } else {
                if (playingMusic == null) {
                    player.sendMessage(Text.translatable("item.reverie_dreams.music.no_music_selected"), false);
                } else {
                    player.sendMessage(Text.translatable("item.reverie_dreams.music.playing_music", playingMusic, noteBlockInstrument.asString()), false);
                    TouhouNotaUtils.play(player, playingMusic, noteBlockInstrument);
                }
            }
            player.swingHand(hand);
            return ActionResult.SUCCESS_SERVER;
        }
        return super.use(world, user, hand);
    }
}
