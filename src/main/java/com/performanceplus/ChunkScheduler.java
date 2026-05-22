package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChunkScheduler {

    private static final Queue<Runnable> rebuildQueue =
            new ConcurrentLinkedQueue<>();

    private static int maxTasksPerTick = 2;

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.world == null) {
                return;
            }

            processQueue();
        });

        PerformancePlus.LOGGER.info(
                "Chunk Scheduler initialized"
        );
    }

    public static void schedule(Runnable task) {

        if (task == null) {
            return;
        }

        rebuildQueue.add(task);
    }

    private static void processQueue() {

        int processed = 0;

        while (
                processed < maxTasksPerTick &&
                !rebuildQueue.isEmpty()
        ) {

            Runnable task = rebuildQueue.poll();

            if (task != null) {

                try {

                    task.run();

                } catch (Exception e) {

                    PerformancePlus.LOGGER.error(
                            "Chunk task failed",
                            e
                    );
                }
            }

            processed++;
        }
    }

    public static void setAggressiveMode(boolean aggressive) {

        maxTasksPerTick = aggressive ? 1 : 3;
    }

    public static int getQueuedTasks() {

        return rebuildQueue.size();
    }
}
