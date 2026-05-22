package com.performanceplus;

public class EntityOptimizer {

    private static boolean enabled = true;

    public static void init() {
        PerformancePlus.LOGGER.info("Entity Optimizer initialized");
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }
}
