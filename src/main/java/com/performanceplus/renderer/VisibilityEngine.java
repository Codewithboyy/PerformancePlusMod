package com.performanceplus.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class VisibilityEngine {

    private static int hiddenChecks = 0;

    private static int visibleChecks = 0;

    public static boolean isVisible(
            double x,
            double y,
            double z
    ) {

        MinecraftClient client =
                MinecraftClient.getInstance();

        if (client.player == null ||
            client.world == null) {

            return true;
        }

        Vec3d start =
                client.player.getCameraPosVec(1.0F);

        Vec3d end =
                new Vec3d(x, y, z);

        HitResult result =
                client.world.raycast(
                        new RaycastContext(
                                start,
                                end,
                                RaycastContext.ShapeType.COLLIDER,
                                RaycastContext.FluidHandling.NONE,
                                client.player
                        )
                );

        boolean visible =
                result.getType() ==
                HitResult.Type.MISS;

        if (visible) {

            visibleChecks++;

        } else {

            hiddenChecks++;
        }

        return visible;
    }

    public static String getStats() {

        return
                "Visible: " +
                visibleChecks +
                " Hidden: " +
                hiddenChecks;
    }
}
