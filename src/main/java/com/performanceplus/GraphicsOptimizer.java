package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.ParticlesMode;

public class GraphicsOptimizer {

    private static boolean initialized = false;

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(GraphicsOptimizer::onClientTick);

        PerformancePlus.LOGGER.info("Graphics Optimizer initialized");
    }

    private static void onClientTick(MinecraftClient client) {

        if (initialized) {
            return;
        }

        if (client == null || client.world == null) {
            return;
        }

        applyOptimizations(client);

        initialized = true;
    }

    private static void applyOptimizations(MinecraftClient client) {

        GameOptions options = client.options;

        if (options == null) {
            return;
        }

        // Graphics

        options.getGraphicsMode().setValue(GraphicsMode.FAST);

        options.getCloudRenderMode().setValue(
            CloudRenderMode.OFF
        );

        options.getEntityShadows().setValue(false);

        options.getBiomeBlendRadius().setValue(0);

        options.getParticles().setValue(
            ParticlesMode.MINIMAL
        );

        // Distances

        options.getViewDistance().setValue(8);

        options.getSimulationDistance().setValue(5);

        options.getEntityDistanceScaling().setValue(0.75D);

        // FPS

        options.getMaxFps().setValue(120);

        options.getEnableVsync().setValue(false);

        // Visual Effects

        options.getBobView().setValue(false);

        options.getDistortionEffectScale().setValue(0.0);

        options.getMipmapLevels().setValue(2);

        options.getAttackIndicator().setValue(
            net.minecraft.client.option.AttackIndicator.CROSSHAIR
        );

        // UI

        options.getShowAutosaveIndicator().setValue(false);

        PerformancePlus.LOGGER.info("Performance settings applied");
    }

    public static void applyUltraPerformancePreset() {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.options == null) {
            return;
        }

        GameOptions options = client.options;

        options.getGraphicsMode().setValue(GraphicsMode.FAST);

        options.getViewDistance().setValue(4);

        options.getSimulationDistance().setValue(4);

        options.getEntityDistanceScaling().setValue(0.5D);

        options.getParticles().setValue(
            ParticlesMode.MINIMAL
        );

        options.getCloudRenderMode().setValue(
            CloudRenderMode.OFF
        );

        options.getEntityShadows().setValue(false);

        options.getBiomeBlendRadius().setValue(0);

        options.getMaxFps().setValue(120);

        options.getEnableVsync().setValue(false);

        PerformancePlus.LOGGER.info(
            "Ultra Performance preset applied"
        );
    }

    public static void applyBalancedPreset() {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.options == null) {
            return;
        }

        GameOptions options = client.options;

        options.getGraphicsMode().setValue(GraphicsMode.FAST);

        options.getViewDistance().setValue(8);

        options.getSimulationDistance().setValue(5);

        options.getEntityDistanceScaling().setValue(0.75D);

        options.getParticles().setValue(
            ParticlesMode.DECREASED
        );

        options.getCloudRenderMode().setValue(
            CloudRenderMode.FAST
        );

        options.getEntityShadows().setValue(false);

        options.getBiomeBlendRadius().setValue(1);

        options.getMaxFps().setValue(120);

        options.getEnableVsync().setValue(false);

        PerformancePlus.LOGGER.info(
            "Balanced preset applied"
        );
    }

    public static void applyQualityPreset() {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.options == null) {
            return;
        }

        GameOptions options = client.options;

        options.getGraphicsMode().setValue(GraphicsMode.FANCY);

        options.getViewDistance().setValue(12);

        options.getSimulationDistance().setValue(8);

        options.getEntityDistanceScaling().setValue(1.0D);

        options.getParticles().setValue(
            ParticlesMode.ALL
        );

        options.getCloudRenderMode().setValue(
            CloudRenderMode.FANCY
        );

        options.getEntityShadows().setValue(true);

        options.getBiomeBlendRadius().setValue(2);

        options.getMaxFps().setValue(144);

        options.getEnableVsync().setValue(false);

        PerformancePlus.LOGGER.info(
            "Quality preset applied"
        );
    }

    public static String getStats() {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.options == null) {
            return "N/A";
        }

        GameOptions options = client.options;

        return String.format(
            "Graphics: %s | View Distance: %d | Sim Distance: %d | FPS Limit: %d",
            options.getGraphicsMode().getValue(),
            options.getViewDistance().getValue(),
            options.getSimulationDistance().getValue(),
            options.getMaxFps().getValue()
        );
    }
}
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
        return String.format("Graphics: %s | VD: %d | Entities: %.0f%% | Shadows: %s", 
            options.getGraphicsMode().getValue().toString(),
            options.getViewDistance().getValue(),
            options.getEntityDistanceScaling().getValue() * 100,
            options.getEntityShadows().getValue() ? "ON" : "OFF");
    }
}
