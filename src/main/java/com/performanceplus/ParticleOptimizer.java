package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;

public class ParticleOptimizer {

    private static boolean initialized = false;

    private static String currentPreset = "balanced";

    public static void init() {

        ClientTickEvents.END_CLIENT_TICK.register(
            ParticleOptimizer::onClientTick
        );

        PerformancePlus.LOGGER.info(
            "Particle Optimizer initialized"
        );
    }

    private static void onClientTick(MinecraftClient client) {

        if (initialized) {
            return;
        }

        if (client == null || client.world == null) {
            return;
        }

        applyPreset(currentPreset);

        initialized = true;
    }

    public static void applyPreset(String preset) {

        MinecraftClient client =
            MinecraftClient.getInstance();

        if (client == null || client.options == null) {
            return;
        }

        switch (preset.toLowerCase()) {

            case "minimal":

                client.options.getParticles().setValue(
                    ParticlesMode.MINIMAL
                );

                break;

            case "reduced":

                client.options.getParticles().setValue(
                    ParticlesMode.DECREASED
                );

                break;

            case "all":

                client.options.getParticles().setValue(
                    ParticlesMode.ALL
                );

                break;

            default:

                client.options.getParticles().setValue(
                    ParticlesMode.DECREASED
                );

                preset = "balanced";

                break;
        }

        currentPreset = preset;

        PerformancePlus.LOGGER.info(
            "Particle preset applied: {}",
            currentPreset
        );
    }

    public static void setMinimalParticles() {

        applyPreset("minimal");
    }

    public static void setReducedParticles() {

        applyPreset("reduced");
    }

    public static void setAllParticles() {

        applyPreset("all");
    }

    public static String getCurrentPreset() {

        return currentPreset;
    }

    public static boolean isPerformanceMode() {

        return currentPreset.equalsIgnoreCase(
            "minimal"
        );
    }

    public static String getStats() {

        return String.format(
            "Particles: %s",
            currentPreset.toUpperCase()
        );
    }
}", maxParticles);
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
