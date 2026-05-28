package com.performanceplus;

public class DynamicChunkLimiter {

    private static int maxChunkUpdatesPerFrame = 3;

    private static int currentUpdates = 0;

    private static boolean aggressiveMode = false;

    public static boolean allowChunkUpdate() {

        if (currentUpdates >= maxChunkUpdatesPerFrame) {
            return false;
        }

        currentUpdates++;

        return true;
    }

    public static void resetFrame() {

        currentUpdates = 0;
    }

    public static void setAggressiveMode(
            boolean enabled
    ) {

        aggressiveMode = enabled;

        if (enabled) {

            maxChunkUpdatesPerFrame = 2;

        } else {

            maxChunkUpdatesPerFrame = 5;
        }
    }

    public static void updateDynamicBudget(
            double fps
    ) {

        if (fps < 40) {

            maxChunkUpdatesPerFrame = 2;

        } else if (fps < 70) {

            maxChunkUpdatesPerFrame = 3;

        } else if (fps < 120) {

            maxChunkUpdatesPerFrame = 5;

        } else {

            maxChunkUpdatesPerFrame = 8;
        }
    }

    public static String getStats() {

        return
                "Chunk Updates: " +
                currentUpdates +
                "/" +
                maxChunkUpdatesPerFrame;
    }
}
