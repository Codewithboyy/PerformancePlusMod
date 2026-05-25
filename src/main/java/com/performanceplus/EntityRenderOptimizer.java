package com.performanceplus;

import net.minecraft.entity.Entity;

public class EntityRenderOptimizer {

    private static boolean enabled = true;

    private static double maxRenderDistance = 96.0;

    private static int skippedEntities = 0;

    public static boolean shouldRender(
            Entity entity,
            double playerX,
            double playerY,
            double playerZ
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

        double limit =
                maxRenderDistance *
                maxRenderDistance;

        if (distanceSq > limit) {

            skippedEntities++;

            return false;
        }

        return true;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setMaxRenderDistance(double value) {
        maxRenderDistance = value;
    }

    public static String getStats() {

        return
                "Skipped Entities: " +
                skippedEntities +
                " | Max Distance: " +
                maxRenderDistance;
    }
}
