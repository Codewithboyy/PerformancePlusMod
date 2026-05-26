package com.performanceplus;

import net.minecraft.block.entity.BlockEntity;

public class BlockEntityOptimizer {

    private static boolean enabled = true;

    private static double maxRenderDistance = 64.0;

    private static int skippedBlockEntities = 0;

    public static boolean shouldRender(
            BlockEntity blockEntity,
            double playerX,
            double playerY,
            double playerZ
    ) {

        if (!enabled) {
            return true;
        }

        double dx =
                blockEntity.getPos().getX() - playerX;

        double dy =
                blockEntity.getPos().getY() - playerY;

        double dz =
                blockEntity.getPos().getZ() - playerZ;

        double distanceSq =
                dx * dx +
                dy * dy +
                dz * dz;

        double limit =
                maxRenderDistance *
                maxRenderDistance;

        if (distanceSq > limit) {

            skippedBlockEntities++;

            return false;
        }

        return true;
    }

    public static String getStats() {

        return
                "Skipped Block Entities: " +
                skippedBlockEntities;
    }
}
