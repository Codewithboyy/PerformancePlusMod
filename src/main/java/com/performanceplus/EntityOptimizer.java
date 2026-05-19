package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class EntityOptimizer {

    private static int entityCount = 0;

    private static int hostileCount = 0;

    private static int passiveCount = 0;

    private static int itemCount = 0;

    private static int checkTimer = 0;

    private static final int CHECK_INTERVAL = 40;

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(
            EntityOptimizer::onClientTick
        );

        PerformancePlus.LOGGER.info(
            "Entity Optimizer initialized"
        );
    }

    private static void onClientTick(
        MinecraftClient client
    ) {

        if (client == null || client.world == null) {
            return;
        }

        checkTimer++;

        if (checkTimer >= CHECK_INTERVAL) {

            checkTimer = 0;

            scanEntities(client);
        }
    }

    private static void scanEntities(
        MinecraftClient client
    ) {

        entityCount = 0;
        hostileCount = 0;
        passiveCount = 0;
        itemCount = 0;

        for (Entity entity : client.world.getEntities()) {

            entityCount++;

            String name =
                entity.getType()
                      .toString()
                      .toLowerCase();

            if (name.contains("item")) {

                itemCount++;

            } else if (
                name.contains("zombie") ||
                name.contains("skeleton") ||
                name.contains("creeper") ||
                name.contains("spider") ||
                name.contains("enderman")
            ) {

                hostileCount++;

            } else {

                passiveCount++;
            }
        }
    }

    public static boolean isEntityHeavy() {

        return entityCount > 300;
    }

    public static boolean isTooManyItems() {

        return itemCount > 100;
    }

    public static int getEntityCount() {

        return entityCount;
    }

    public static int getHostileCount() {

        return hostileCount;
    }

    public static int getPassiveCount() {

        return passiveCount;
    }

    public static int getItemCount() {

        return itemCount;
    }

    public static String getStats() {

        return String.format(
            "Entities: %d | Hostile: %d | Passive: %d | Items: %d",
            entityCount,
            hostileCount,
            passiveCount,
            itemCount
        );
    }
}imizeDistantEntities;
    }
    
    public static String getStats() {
        MinecraftClient client = MinecraftClient.getInstance();
        int entityCount = 0;
        if (client.world != null) {
            for (net.minecraft.entity.Entity entity : client.world.getEntities()) {
                entityCount++;
            }
        }
        return String.format("Entities: %d | Cull: %.0f blocks | Update: every %d ticks", 
            entityCount, cullDistance, entityUpdateInterval);
    }
}
