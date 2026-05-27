package com.performanceplus;

import net.minecraft.entity.Entity;

public class AdaptiveTickOptimizer {

    private static boolean enabled = true;

    private static int skippedTicks = 0;

    public static boolean shouldTick(
            Entity entity,
            double playerX,
            double playerY,
            double playerZ,
            int age
    ) {

        if (!enabled) {
            return true;
        }

        double dx = entity.getX() - playerX;
        double dy = entity.getY() - playerY;
        double dz = entity.getZ() - playerZ;

        double distanceSq =
                dx * dx +
                dy * dy +
                dz * dz;

        if (distanceSq > 16384) {

            if (age % 10 != 0) {

                skippedTicks++;

                return false;
            }
        }

        else if (distanceSq > 4096) {

            if (age % 4 != 0) {

                skippedTicks++;

                return false;
            }
        }

        return true;
    }

    public static String getStats() {

        return
                "Skipped Entity Ticks: " +
                skippedTicks;
    }
}
