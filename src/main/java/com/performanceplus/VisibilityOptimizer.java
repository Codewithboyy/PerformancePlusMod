package com.performanceplus;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class VisibilityOptimizer {

    public static boolean isUndergroundHidden(BlockPos pos) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.world == null) {
            return false;
        }

        ClientPlayerEntity player = client.player;

        if (player == null) {
            return false;
        }

        // Player above area
        if (player.getY() > pos.getY() + 20) {

            // Check if chunk area has sky access
            boolean canSeeSky = client.world.isSkyVisible(pos);

            return !canSeeSky;
        }

        return false;
    }
}
