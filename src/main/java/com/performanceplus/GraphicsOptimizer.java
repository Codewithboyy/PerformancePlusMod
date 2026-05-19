package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;

public class GraphicsOptimizer {
    private static boolean autoOptimize = true;
    private static boolean smartShadows = true;
    private static boolean optimizeFoliage = true;
    private static boolean reduceSmoothLighting = false;
    private static int tickCounter = 0;
    
    // Preset configurations for different performance levels
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(GraphicsOptimizer::onClientTick);
        applyOptimizations();
        PerformancePlus.LOGGER.info("Graphics Optimizer initialized");
    }
    
    private static void onClientTick(MinecraftClient client) {
        if (!autoOptimize || client.world == null) return;
        
        tickCounter++;
        
        // Re-apply optimizations every 5 seconds
        if (tickCounter % 100 == 0) {
            applyOptimizations();
        }
    }
    
    public static void applyOptimizations() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) return;
        
        GameOptions options = client.options;
        
        // Apply performance-friendly settings while maintaining visual quality
        
        // Use Fast graphics for leaves, but keep other visuals nice
        if (optimizeFoliage) {
            options.getGraphicsMode().setValue(GraphicsMode.FAST);
        }
        
        // Reduce view bobbing for better performance
        options.getBobView().setValue(false);
        
        // Optimize clouds
        options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.OFF);
        
        // Reduce entity shadows
        if (smartShadows) {
            options.getEntityShadows().setValue(false);
        }
        
        // Disable vignette (minimal visual impact, slight performance gain)
        options.getVignette().setValue(false);
        
        // Reduce distortion effects
        options.getDistortionEffectScale().setValue(0.5);
        
        // Optimize biome blend for performance
        options.getBiomeBlendRadius().setValue(0); // No blending for max FPS
        
        // Disable autosave indicator
        options.getShowAutosaveIndicator().setValue(false);
        
        PerformancePlus.LOGGER.debug("Graphics optimizations applied");
    }
    
    public static void applyUltraPerformancePreset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) return;
        
        GameOptions options = client.options;
        
        // Absolute minimum for maximum FPS
        options.getGraphicsMode().setValue(GraphicsMode.FAST);
        options.getRenderDistance().setValue(4); // Very low render distance
        options.getEntityDistanceScaling().setValue(0.5); // Half entity render distance
        options.getMaxFps().setValue(300); // Cap at 300 FPS
        options.getEnableVsync().setValue(false); // Disable VSync
        options.getBobView().setValue(false);
        options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.OFF);
        options.getEntityShadows().setValue(false);
        options.getVignette().setValue(false);
        options.getBiomeBlendRadius().setValue(0);
        options.getFov().setValue(90.0); // Standard FOV
        
        // Particle settings
        ParticleOptimizer.setPreset("minimal");
        
        // Resolution scaling
        ResolutionScaler.setPreset("high_performance");
        
        PerformancePlus.LOGGER.info("Ultra Performance preset applied - optimized for 300+ FPS");
    }
    
    public static void applyBalancedPreset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) return;
        
        GameOptions options = client.options;
        
        // Balance between quality and performance
        options.getGraphicsMode().setValue(GraphicsMode.FAST);
        options.getRenderDistance().setValue(8); // Moderate render distance
        options.getEntityDistanceScaling().setValue(0.75);
        options.getMaxFps().setValue(300);
        options.getEnableVsync().setValue(false);
        options.getBobView().setValue(false);
        options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FAST);
        options.getEntityShadows().setValue(false);
        options.getBiomeBlendRadius().setValue(1);
        
        ParticleOptimizer.setPreset("reduced");
        ResolutionScaler.setPreset("balanced");
        
        PerformancePlus.LOGGER.info("Balanced preset applied");
    }
    
    public static void applyQualityPreset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) return;
        
        GameOptions options = client.options;
        
        // Better visuals, still optimized
        options.getGraphicsMode().setValue(GraphicsMode.FANCY);
        options.getRenderDistance().setValue(12);
        options.getEntityDistanceScaling().setValue(1.0);
        options.getMaxFps().setValue(240);
        options.getEnableVsync().setValue(false);
        options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FANCY);
        options.getBiomeBlendRadius().setValue(2);
        
        ParticleOptimizer.setPreset("normal");
        ResolutionScaler.setPreset("quality");
        
        PerformancePlus.LOGGER.info("Quality preset applied");
    }
    
    public static void setAutoOptimize(boolean enabled) {
        autoOptimize = enabled;
        PerformancePlus.LOGGER.info("Auto-optimize: {}", enabled ? "enabled" : "disabled");
    }
    
    public static void setSmartShadows(boolean enabled) {
        smartShadows = enabled;
        applyOptimizations();
    }
    
    public static void setOptimizeFoliage(boolean enabled) {
        optimizeFoliage = enabled;
        applyOptimizations();
    }
    
    public static String getStats() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null) return "N/A";
        
        GameOptions options = client.options;
        return String.format("Graphics: %s | RD: %d | Entities: %.0f%% | Shadows: %s", 
            options.getGraphicsMode().getValue().toString(),
            options.getRenderDistance().getValue(),
            options.getEntityDistanceScaling().getValue() * 100,
            options.getEntityShadows().getValue() ? "ON" : "OFF");
    }
}
