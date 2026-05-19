package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;

public class ResolutionScaler {

    private static boolean autoOptimize = true;
    private static int targetFPS = 120;
    private static int checkInterval = 200;
    private static int tickCounter = 0;

    private static boolean lowGraphicsEnabled = false;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ResolutionScaler::onClientTick);

        PerformancePlus.LOGGER.info("Performance Optimizer initialized");
    }

    private static void onClientTick(MinecraftClient client) {

        if (!autoOptimize || client.world == null) {
            return;
        }

        tickCounter++;

        if (tickCounter >= checkInterval) {
            tickCounter = 0;
            optimizePerformance(client);
        }
    }

    private static void optimizePerformance(MinecraftClient client) {

        double currentFPS = FPSOptimizer.getAverageFPS();

        if (currentFPS < targetFPS && !lowGraphicsEnabled) {

            enablePerformanceMode(client);

            lowGraphicsEnabled = true;

            PerformancePlus.LOGGER.info(
                "Performance mode enabled (FPS: {})",
                (int) currentFPS
            );

        } else if (currentFPS > targetFPS + 40 && lowGraphicsEnabled) {

            restoreGraphics(client);

            lowGraphicsEnabled = false;

            PerformancePlus.LOGGER.info(
                "Graphics restored (FPS: {})",
                (int) currentFPS
            );
        }
    }

    private static void enablePerformanceMode(MinecraftClient client) {

        if (client.options == null) {
            return;
        }

        client.options.getParticles().setValue(
            net.minecraft.client.option.ParticlesMode.MINIMAL
        );

        client.options.getGraphicsMode().setValue(
            GraphicsMode.FAST
        );

        client.options.getBiomeBlendRadius().setValue(0);

        client.options.getEntityDistanceScaling().setValue(0.75D);

        client.options.getCloudRenderMode().setValue(
            net.minecraft.client.option.CloudRenderMode.OFF
        );

        client.options.setGuiScale(2);

        client.options.getMipmapLevels().setValue(0);

        client.worldRenderer.reload();
    }

    private static void restoreGraphics(MinecraftClient client) {

        if (client.options == null) {
            return;
        }

        client.options.getParticles().setValue(
            net.minecraft.client.option.ParticlesMode.ALL
        );

        client.options.getGraphicsMode().setValue(
            GraphicsMode.FANCY
        );

        client.options.getBiomeBlendRadius().setValue(2);

        client.options.getEntityDistanceScaling().setValue(1.0D);

        client.options.getCloudRenderMode().setValue(
            net.minecraft.client.option.CloudRenderMode.FANCY
        );

        client.options.getMipmapLevels().setValue(4);

        client.worldRenderer.reload();
    }

    public static void setAutoOptimize(boolean enabled) {
        autoOptimize = enabled;
    }

    public static void setTargetFPS(int fps) {
        targetFPS = Math.max(60, Math.min(240, fps));
    }

    public static boolean isAutoOptimizeEnabled() {
        return autoOptimize;
    }

    public static int getTargetFPS() {
        return targetFPS;
    }

    public static String getStats() {

        return String.format(
            "Target FPS: %d | Performance Mode: %s",
            targetFPS,
            lowGraphicsEnabled ? "ON" : "OFF"
        );
    }
}erScale);
            int scaledHeight = (int)(windowHeight * renderScale);
            
            // Update framebuffer size
            Framebuffer framebuffer = client.getFramebuffer();
            if (framebuffer != null) {
                framebuffer.resize(scaledWidth, scaledHeight, MinecraftClient.IS_SYSTEM_MAC);
            }
        }
    }
    
    public static void setAutoScale(boolean enabled) {
        autoScale = enabled;
        PerformancePlus.LOGGER.info("Auto-scaling {}", enabled ? "enabled" : "disabled");
    }
    
    public static void setTargetFPS(int fps) {
        targetFPS = Math.max(60, Math.min(500, fps));
        PerformancePlus.LOGGER.info("Target FPS set to {}", targetFPS);
    }
    
    public static float getRenderScale() {
        return renderScale;
    }
    
    public static boolean isAutoScaleEnabled() {
        return autoScale;
    }
    
    public static int getTargetFPS() {
        return targetFPS;
    }
    
    public static String getStats() {
        return String.format("Render Scale: %d%% | Target FPS: %d | Auto-scale: %s", 
            (int)(renderScale * 100), targetFPS, autoScale ? "ON" : "OFF");
    }
}
