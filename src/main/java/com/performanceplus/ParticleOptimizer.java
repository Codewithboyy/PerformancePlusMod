package com.performanceplus;

public class ParticleOptimizer {

    private static boolean enabled = true;

    public static void init() {
        PerformancePlus.LOGGER.info("Particle Optimizer initialized");
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }
}
