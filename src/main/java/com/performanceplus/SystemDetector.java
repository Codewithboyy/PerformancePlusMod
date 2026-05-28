package com.performanceplus;

import net.minecraft.client.MinecraftClient;

public class SystemDetector {

    public enum DeviceType {
        LOW_END_MOBILE,
        MID_RANGE_MOBILE,
        HIGH_END_MOBILE,
        LOW_END_PC,
        MID_RANGE_PC,
        HIGH_END_PC
    }

    private static DeviceType detectedType;

    private static boolean initialized = false;

    public static void detectSystem() {

        if (initialized) {
            return;
        }

        initialized = true;

        try {

            String osName =
                    System.getProperty("os.name", "")
                            .toLowerCase();

            String javaVendor =
                    System.getProperty("java.vendor", "")
                            .toLowerCase();

            String runtimeName =
                    System.getProperty("java.runtime.name", "")
                            .toLowerCase();

            int cores =
                    Runtime.getRuntime()
                            .availableProcessors();

            long maxMemoryMB =
                    Runtime.getRuntime()
                            .maxMemory() /
                            (1024 * 1024);

            boolean isAndroid =
                    osName.contains("android") ||
                    javaVendor.contains("android") ||
                    runtimeName.contains("android") ||
                    System.getProperty("pojav.launcher") != null;

            if (isAndroid) {

                if (maxMemoryMB <= 2048 || cores <= 4) {

                    detectedType =
                            DeviceType.LOW_END_MOBILE;

                } else if (maxMemoryMB <= 4096) {

                    detectedType =
                            DeviceType.MID_RANGE_MOBILE;

                } else {

                    detectedType =
                            DeviceType.HIGH_END_MOBILE;
                }

            } else {

                if (maxMemoryMB <= 4096 || cores <= 4) {

                    detectedType =
                            DeviceType.LOW_END_PC;

                } else if (maxMemoryMB <= 8192) {

                    detectedType =
                            DeviceType.MID_RANGE_PC;

                } else {

                    detectedType =
                            DeviceType.HIGH_END_PC;
                }
            }

            PerformancePlus.LOGGER.info(
                    "Detected device type: {}",
                    detectedType
            );

        } catch (Exception e) {

            detectedType =
                    DeviceType.MID_RANGE_PC;

            PerformancePlus.LOGGER.error(
                    "System detection failed",
                    e
            );
        }
    }

    public static boolean isMobile() {

        return detectedType ==
                DeviceType.LOW_END_MOBILE ||

               detectedType ==
                DeviceType.MID_RANGE_MOBILE ||

               detectedType ==
                DeviceType.HIGH_END_MOBILE;
    }

    public static DeviceType getDeviceType() {

        return detectedType;
    }
}
