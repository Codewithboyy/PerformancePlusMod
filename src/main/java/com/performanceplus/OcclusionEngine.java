package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class OcclusionEngine {

    private static boolean enabled = true;

    private static int hiddenChecks = 0;

    private static int visibleChecks = 0;

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) {
                return;
            }

            runVisibilityUpdate(client);
        });

        PerformancePlus.LOGGER.info(
                "Occlusion Engine initialized"
        );
    }

    private static void runVisibilityUpdate(
            MinecraftClient client
    ) {

        Vec3d cameraPos =
                client.player.getCameraPosVec(1.0f);

        double px = cameraPos.x;
        double py = cameraPos.y;
        double pz = cameraPos.z;

        int radius = 8;

        hiddenChecks = 0;
        visibleChecks = 0;

        for (int x = -radius; x <= radius; x++) {

            for (int z = -radius; z <= radius; z++) {

                double cx = px + (x * 16);
                double cz = pz + (z * 16);

                double dx = cx - px;
                double dz = cz - pz;

                double distance =
                        Math.sqrt(dx * dx + dz * dz);

                if (distance > 64) {

                    hiddenChecks++;

                } else {

                    visibleChecks++;
                }
            }
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static String getStats() {

        return
                "Visible: " +
                visibleChecks +
                " Hidden: " +
                hiddenChecks;
    }
}
