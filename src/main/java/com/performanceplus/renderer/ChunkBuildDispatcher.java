package com.performanceplus.renderer;

public class ChunkBuildDispatcher {

    public static void scheduleChunkBuild(
            Runnable buildTask
    ) {

        AsyncChunkBuilder.submit(() -> {

            try {

                buildTask.run();

            } catch (Exception ignored) {

            }
        });
    }
}
