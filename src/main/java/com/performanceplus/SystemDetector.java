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

    public static void detectSystem() {

        String os =
                System.getProperty("os.name")
                        .toLowerCase();

        String arch =
                System.getProperty("os.arch")
                        .toLowerCase();

        int cores =
                Runtime.getRuntime()
                        .availableProcessors();

        long ram =
                Runtime.getRuntime()
                        .maxMemory() /
                        (1024 * 1024);

        boolean mobile =
                os.contains("android");

        if (mobile) {

            if (ram <= 2048 || cores <= 4) {

                detectedType =
                        DeviceType.LOW_END_MOBILE;

            } else if (ram <= 4096) {

                detectedType =
                        DeviceType.MID_RANGE_MOBILE;

            } else {

                detectedType =
                        DeviceType.HIGH_END_MOBILE;
            }

        } else {

            if (ram <= 4096 || cores <= 4) {

                detectedType =
                        DeviceType.LOW_END_PC;

            } else if (ram <= 8192) {

                detectedType =
                        DeviceType.MID_RANGE_PC;

            } else {

                detectedType =
                        DeviceType.HIGH_END_PC;
            }
        }

        PerformancePlus.LOGGER.info(
                "Detected system: {}",
                detectedType
        );
    }

    public static DeviceType getDeviceType() {

        return detectedType;
    }

    public static boolean isMobile() {

        return detectedType ==
                DeviceType.LOW_END_MOBILE ||

               detectedType ==
                DeviceType.MID_RANGE_MOBILE ||

               detectedType ==
                DeviceType.HIGH_END_MOBILE;
    }
}
