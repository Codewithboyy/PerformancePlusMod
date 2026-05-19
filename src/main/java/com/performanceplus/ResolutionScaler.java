package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;

public class ResolutionScaler {
    private static float renderScale = 0.75f; // 75% of native resolution
    private static boolean autoScale = true;
    private static int targetFPS = 300;
    private static int checkInterval = 60; // Check every 60 ticks (3 seconds)
    private static int tickCounter = 0;
    
    // Quality presets
    public static final float ULTRA_PERFORMANCE = 0.5f;  // 50% resolution (max FPS)
    public static final float HIGH_PERFORMANCE = 0.65f;  // 65% resolution
    public static final float BALANCED = 0.75f;          // 75% resolution (default)
    public static final float QUALITY = 0.85f;           // 85% resolution
    public static final float ULTRA_QUALITY = 1.0f;      // 100% native resolution
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ResolutionScaler::onClientTick);
        PerformancePlus.LOGGER.info("Resolution Scaler initialized at {}% render scale", (int)(renderScale * 100));
    }
    
    private static void onClientTick(MinecraftClient client) {
        if (!autoScale || client.world == null) return;
        
        tickCounter++;
        if (tickCounter >= checkInterval) {
            tickCounter = 0;
            adjustResolutionBasedOnFPS();
        }
    }
    
    private static void adjustResolutionBasedOnFPS() {
        double currentFPS = FPSOptimizer.getAverageFPS();
        
        if (currentFPS < targetFPS - 50 && renderScale > ULTRA_PERFORMANCE) {
            // FPS too low, reduce resolution
            renderScale = Math.max(ULTRA_PERFORMANCE, renderScale - 0.05f);
            applyRenderScale();
            PerformancePlus.LOGGER.info("Lowering render scale to {}% (Current FPS: {})", 
                (int)(renderScale * 100), (int)currentFPS);
        } else if (currentFPS > targetFPS + 50 && renderScale < ULTRA_QUALITY) {
            // FPS very high, can increase quality
            renderScale = Math.min(ULTRA_QUALITY, renderScale + 0.05f);
            applyRenderScale();
            PerformancePlus.LOGGER.info("Increasing render scale to {}% (Current FPS: {})", 
                (int)(renderScale * 100), (int)currentFPS);
        }
    }
    
    public static void setRenderScale(float scale) {
        renderScale = Math.max(0.25f, Math.min(1.0f, scale));
        applyRenderScale();
        PerformancePlus.LOGGER.info("Manual render scale set to {}%", (int)(renderScale * 100));
    }
    
    public static void setPreset(String preset) {
        switch (preset.toLowerCase()) {
            case "ultra_performance":
                setRenderScale(ULTRA_PERFORMANCE);
                break;
            case "high_performance":
                setRenderScale(HIGH_PERFORMANCE);
                break;
            case "balanced":
                setRenderScale(BALANCED);
                break;
            case "quality":
                setRenderScale(QUALITY);
                break;
            case "ultra_quality":
                setRenderScale(ULTRA_QUALITY);
                break;
            default:
                PerformancePlus.LOGGER.warn("Unknown preset: {}", preset);
        }
    }
    
    private static void applyRenderScale() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.getWindow() != null) {
            int windowWidth = client.getWindow().getWidth();
            int windowHeight = client.getWindow().getHeight();
            
            int scaledWidth = (int)(windowWidth * renderScale);
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
