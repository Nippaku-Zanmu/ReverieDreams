package cc.thonly.reverie_dreams.server;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
public class DelayedTask {
    public static final List<DelayedTask> TASKS = new ArrayList<>();
    private MinecraftServer server;
    private float ticksLeft;
    private Runnable action;

    public DelayedTask(MinecraftServer server, float ticksLeft, Runnable action) {
        this.server = server;
        this.ticksLeft = ticksLeft;
        this.action = action;
        TASKS.add(this);
    }

    public static DelayedTask create(MinecraftServer server, float ticksLeft, Runnable action) {
        return new DelayedTask(server, ticksLeft, action);
    }

    public static DelayedTask createFromSecond(MinecraftServer server, float second, Runnable action) {
        return new DelayedTask(server, second * 20, action);
    }

    public static void repeat(MinecraftServer server, int times, float intervalSeconds, Runnable action) {
        if (times <= 0) return;
        createFromSecond(server, intervalSeconds, () -> {
            action.run();
            repeat(server, times - 1, intervalSeconds, action);
        });
    }

    public static void repeat(MinecraftServer server, int times, int intervalTick, Runnable action) {
        if (times <= 0) return;
        create(server, intervalTick, () -> {
            action.run();
            repeat(server, times - 1, intervalTick, action);
        });
    }


    public static void tick(MinecraftServer server) {
        Set<DelayedTask> collect = TASKS.stream().filter(t -> t.server.equals(server)).collect(Collectors.toSet());
        for (var task : collect) {
            task.tick();
        }
    }

    public boolean tick() {
        if (--this.ticksLeft <= 0) {
            this.action.run();
            TASKS.remove(this);
            return true;
        }
        return false;
    }
}
