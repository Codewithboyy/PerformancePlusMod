package com.performanceplus.renderer;

import com.performanceplus.PerformancePlus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncChunkBuilder {

    private static final int THREADS =
            Math.max(
                    2,
                    Runtime.getRuntime()
                            .availableProcessors() / 2
            );

    private static final ExecutorService EXECUTOR =
            Executors.newFixedThreadPool(THREADS);

    private static final LinkedBlockingQueue<Runnable>
            BUILD_QUEUE =
            new LinkedBlockingQueue<>();

    private static boolean initialized = false;

    private static int queuedTasks = 0;

    public static void init() {

        if (initialized) {
            return;
        }

        initialized = true;

        for (int i = 0; i < THREADS; i++) {

            EXECUTOR.submit(() -> {

                while (true) {

                    try {

                        Runnable task =
                                BUILD_QUEUE.take();

                        task.run();

                    } catch (Exception e) {

                        PerformancePlus.LOGGER.error(
                                "Async chunk build failed",
                                e
                        );
                    }
                }
            });
        }

        PerformancePlus.LOGGER.info(
                "AsyncChunkBuilder initialized with {} threads",
                THREADS
        );
    }

    public static void submit(Runnable task) {

        if (BUILD_QUEUE.size() > 256) {
            return;
        }

        queuedTasks++;

        BUILD_QUEUE.offer(() -> {

            try {

                task.run();

            } finally {

                queuedTasks--;
            }
        });
    }

    public static int getQueuedTasks() {

        return queuedTasks;
    }

    public static String getStats() {

        return
                "Async Chunk Tasks: " +
                queuedTasks;
    }
}
