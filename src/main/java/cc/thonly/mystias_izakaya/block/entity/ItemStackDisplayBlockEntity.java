package cc.thonly.mystias_izakaya.block.entity;

import cc.thonly.mystias_izakaya.block.ItemStackDisplay;
import cc.thonly.mystias_izakaya.block.MIBlockEntities;
import cc.thonly.reverie_dreams.recipe.ItemStackRecipeWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

@Setter
@Getter
public class ItemStackDisplayBlockEntity extends BlockEntity {
    private static final Gson GSON = new Gson();
    private ItemStackRecipeWrapper item = ItemStackRecipeWrapper.empty();
    private double yaw = 0.0;
    private int tick = 0;

    public ItemStackDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(MIBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ItemStackDisplayBlockEntity blockEntity) {
        if (blockEntity.tick > 5) {
            var model = ItemStackDisplay.STATE_TO_MODEL.get(state);
            if (model != null) {
                model.updateItem(state);
            }
            blockEntity.tick = 0;
        }
        blockEntity.tick++;
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        Optional<String> pOutputOptional = view.getOptionalString("Item");
        if (pOutputOptional.isPresent()) {
            String preOutputJson = pOutputOptional.get();
            JsonElement json = JsonParser.parseString(preOutputJson);
            Dynamic<JsonElement> input = new Dynamic<>(JsonOps.INSTANCE, json);
            DataResult<ItemStackRecipeWrapper> parse = ItemStackRecipeWrapper.CODEC.parse(input);
            Optional<ItemStackRecipeWrapper> result = parse.result();
            result.ifPresent(wrapper -> this.item = wrapper);
        }
        this.yaw = view.getDouble("Yaw", 0);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        DataResult<JsonElement> dataResult = ItemStackRecipeWrapper.CODEC.encodeStart(JsonOps.INSTANCE, this.item);
        Optional<JsonElement> result = dataResult.result();
        if (result.isPresent()) {
            JsonElement element = result.get();
            view.putString("Item", GSON.toJson(element));
        }
        view.putDouble("Yaw", this.yaw);
    }
}
