package com.performanceplus;

public class GraphicsOptimizer {

    private static boolean enabled = true;

    private static boolean smartCulling = true;
    private static boolean particleOptimization = true;
    private static boolean chunkOptimization = true;

    public static void init() {

        PerformancePlus.LOGGER.info("Graphics Optimizer initialized");

        PerformancePlus.LOGGER.info(
                "Smart Culling: {}",
                smartCulling ? "enabled" : "disabled"
        );

        PerformancePlus.LOGGER.info(
                "Particle Optimization: {}",
                particleOptimization ? "enabled" : "disabled"
        );

        PerformancePlus.LOGGER.info(
                "Chunk Optimization: {}",
                chunkOptimization ? "enabled" : "disabled"
        );
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static boolean isSmartCullingEnabled() {
        return smartCulling;
    }

    public static boolean isParticleOptimizationEnabled() {
        return particleOptimization;
    }

    public static boolean isChunkOptimizationEnabled() {
        return chunkOptimization;
    }

    public static void setSmartCulling(boolean value) {
        smartCulling = value;
    }

    public static void setParticleOptimization(boolean value) {
        particleOptimization = value;
    }

    public static void setChunkOptimization(boolean value) {
        chunkOptimization = value;
    }
}
