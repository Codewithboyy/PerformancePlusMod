package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class FPSOptimizer {

    private static double averageFPS = 0.0;

    private static int sampleCount = 0;

    private static final int MAX_SAMPLES = 120;

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(
            FPSOptimizer::onClientTick
        );

        PerformancePlus.LOGGER.info(
            "FPS Optimizer initialized"
        );
    }

    private static void onClientTick(MinecraftClient client) {

        if (client == null || client.world == null) {
            return;
        }

        int currentFPS = client.getCurrentFps();

        if (currentFPS <= 0) {
            return;
        }

        averageFPS =
            ((averageFPS * sampleCount) + currentFPS)
            / (sampleCount + 1);

        sampleCount++;

        if (sampleCount >= MAX_SAMPLES) {
            sampleCount = 1;
            averageFPS = currentFPS;
        }
    }

    public static double getAverageFPS() {
        return averageFPS;
    }

    public static int getRoundedFPS() {
        return (int)Math.round(averageFPS);
    }

    public static boolean isLowFPS() {
        return averageFPS < 45;
    }

    public static boolean isGoodFPS() {
        return averageFPS >= 90;
    }

    public static String getFPSStats() {

        String level;

        if (averageFPS >= 144) {
            level = "EXTREME";
        } else if (averageFPS >= 90) {
            level = "HIGH";
        } else if (averageFPS >= 60) {
            level = "GOOD";
        } else if (averageFPS >= 30) {
            level = "LOW";
        } else {
            level = "VERY LOW";
        }

        return String.format(
            "FPS: %d | Performance: %s",
            getRoundedFPS(),
            level
        );
    }
}