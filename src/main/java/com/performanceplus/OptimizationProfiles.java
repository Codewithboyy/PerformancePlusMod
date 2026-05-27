package com.performanceplus;

public class OptimizationProfiles {

    public static void applyProfile() {

        SystemDetector.DeviceType type =
                SystemDetector.getDeviceType();

        switch (type) {

            case LOW_END_MOBILE:

                EntityRenderOptimizer
                        .setMaxRenderDistance(48);

                ChunkScheduler
                        .setAggressiveMode(true);

                PerformancePlus.LOGGER.info(
                        "Applied LOW_END_MOBILE profile"
                );

                break;

            case MID_RANGE_MOBILE:

                EntityRenderOptimizer
                        .setMaxRenderDistance(64);

                PerformancePlus.LOGGER.info(
                        "Applied MID_RANGE_MOBILE profile"
                );

                break;

            case HIGH_END_MOBILE:

                EntityRenderOptimizer
                        .setMaxRenderDistance(80);

                PerformancePlus.LOGGER.info(
                        "Applied HIGH_END_MOBILE profile"
                );

                break;

            case LOW_END_PC:

                EntityRenderOptimizer
                        .setMaxRenderDistance(80);

                break;

            case MID_RANGE_PC:

                EntityRenderOptimizer
                        .setMaxRenderDistance(96);

                break;

            case HIGH_END_PC:

                EntityRenderOptimizer
                        .setMaxRenderDistance(128);

                break;
        }
    }
}
