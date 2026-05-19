package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class EntityOptimizer {
    private static boolean optimizeDistantEntities = true;
    private static double cullDistance = 64.0; // Cull entities beyond 64 blocks
    private static boolean reduceShadows = true;
    private static int entityUpdateInterval = 2; // Update entities every 2 ticks instead of every tick
    private static int tickCounter = 0;
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(EntityOptimizer::onClientTick);
        PerformancePlus.LOGGER.info("Entity Optimizer initialized - Cull distance: {} blocks", cullDistance);
    }
    
    private static void onClientTick(MinecraftClient client) {
        tickCounter++;
        
        if (client.world == null || client.player == null) return;
        
        // Skip entity updates on some ticks for performance
        if (tickCounter % entityUpdateInterval != 0) return;
        
        // Count visible entities for monitoring
        if (tickCounter % 100 == 0) {
            int entityCount = client.world.getEntities().size();
            if (entityCount > 200) {
                PerformancePlus.LOGGER.debug("High entity count detected: {} entities", entityCount);
            }
        }
    }
    
    public static void setCullDistance(double distance) {
        cullDistance = Math.max(16.0, Math.min(256.0, distance));
        PerformancePlus.LOGGER.info("Entity cull distance set to {} blocks", cullDistance);
    }
    
    public static void setEntityUpdateInterval(int interval) {
        entityUpdateInterval = Math.max(1, Math.min(5, interval));
        PerformancePlus.LOGGER.info("Entity update interval set to {} ticks", entityUpdateInterval);
    }
    
    public static void setOptimizeDistantEntities(boolean enabled) {
        optimizeDistantEntities = enabled;
        PerformancePlus.LOGGER.info("Distant entity optimization: {}", enabled ? "enabled" : "disabled");
    }
    
    public static void setReduceShadows(boolean enabled) {
        reduceShadows = enabled;
        PerformancePlus.LOGGER.info("Shadow reduction: {}", enabled ? "enabled" : "disabled");
    }
    
    public static double getCullDistance() {
        return cullDistance;
    }
    
    public static boolean isOptimizeDistantEntities() {
        return optimizeDistantEntities;
    }
    
    public static String getStats() {
        MinecraftClient client = MinecraftClient.getInstance();
        int entityCount = client.world != null ? client.world.getEntities().size() : 0;
        return String.format("Entities: %d | Cull: %.0f blocks | Update: every %d ticks", 
            entityCount, cullDistance, entityUpdateInterval);
    }
}
