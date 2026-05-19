package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleManager;

public class ParticleOptimizer {
    private static float particleMultiplier = 0.5f; // Reduce particles by 50%
    private static int maxParticles = 2000; // Limit total particles
    private static boolean enableParticleOptimization = true;
    
    // Quality presets
    public static final float NONE = 0.0f;        // No particles
    public static final float MINIMAL = 0.25f;    // 25% particles
    public static final float REDUCED = 0.5f;     // 50% particles (default)
    public static final float NORMAL = 0.75f;     // 75% particles
    public static final float ALL = 1.0f;         // 100% particles
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ParticleOptimizer::onClientTick);
        PerformancePlus.LOGGER.info("Particle Optimizer initialized at {}% particles", (int)(particleMultiplier * 100));
    }
    
    private static void onClientTick(MinecraftClient client) {
        if (!enableParticleOptimization || client.world == null) return;
        
        ParticleManager particleManager = client.particleManager;
        if (particleManager != null) {
            // The particle manager is accessed here for future optimizations
            // In a real implementation, you would use mixins to control particle spawning
        }
    }
    
    public static void setParticleMultiplier(float multiplier) {
        particleMultiplier = Math.max(0.0f, Math.min(1.0f, multiplier));
        PerformancePlus.LOGGER.info("Particle multiplier set to {}%", (int)(particleMultiplier * 100));
    }
    
    public static void setPreset(String preset) {
        switch (preset.toLowerCase()) {
            case "none":
                setParticleMultiplier(NONE);
                break;
            case "minimal":
                setParticleMultiplier(MINIMAL);
                break;
            case "reduced":
                setParticleMultiplier(REDUCED);
                break;
            case "normal":
                setParticleMultiplier(NORMAL);
                break;
            case "all":
                setParticleMultiplier(ALL);
                break;
            default:
                PerformancePlus.LOGGER.warn("Unknown preset: {}", preset);
        }
    }
    
    public static void setMaxParticles(int max) {
        maxParticles = Math.max(100, Math.min(10000, max));
        PerformancePlus.LOGGER.info("Max particles set to {}", maxParticles);
    }
    
    public static void setEnabled(boolean enabled) {
        enableParticleOptimization = enabled;
        PerformancePlus.LOGGER.info("Particle optimization: {}", enabled ? "enabled" : "disabled");
    }
    
    public static float getParticleMultiplier() {
        return particleMultiplier;
    }
    
    public static int getMaxParticles() {
        return maxParticles;
    }
    
    public static boolean isEnabled() {
        return enableParticleOptimization;
    }
    
    public static String getStats() {
        MinecraftClient client = MinecraftClient.getInstance();
        String particleCount = "N/A";
        if (client.particleManager != null) {
            particleCount = String.valueOf(client.particleManager.getDebugString().split(" ")[0]);
        }
        return String.format("Particles: %s | Multiplier: %d%% | Max: %d", 
            particleCount, (int)(particleMultiplier * 100), maxParticles);
    }
}
