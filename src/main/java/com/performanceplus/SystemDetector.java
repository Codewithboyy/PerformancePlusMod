package com.performanceplus;

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

            String runtimeName =
                    System.getProperty("java.runtime.name", "")
                            .toLowerCase();

            String arch =
                    System.getProperty("os.arch", "")
                            .toLowerCase();

            int cores =
                    Runtime.getRuntime()
                            .availableProcessors();

            boolean mobileCPU =
                    arch.contains("aarch64") ||
                    arch.contains("arm");

            boolean isAndroid =
                    osName.contains("android") ||

                    runtimeName.contains("android") ||

                    (osName.contains("linux") && mobileCPU) ||

                    System.getProperty("pojav.launcher") != null;

            if (isAndroid) {

                if (cores <= 4) {

                    detectedType =
                            DeviceType.LOW_END_MOBILE;

                } else if (cores <= 6) {

                    detectedType =
                            DeviceType.MID_RANGE_MOBILE;

                } else {

                    detectedType =
                            DeviceType.HIGH_END_MOBILE;
                }

            } else {

                if (cores <= 4) {

                    detectedType =
                            DeviceType.LOW_END_PC;

                } else if (cores <= 8) {

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

            PerformancePlus.LOGGER.info(
                    "OS: {} | ARCH: {} | CORES: {}",
                    osName,
                    arch,
                    cores
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
