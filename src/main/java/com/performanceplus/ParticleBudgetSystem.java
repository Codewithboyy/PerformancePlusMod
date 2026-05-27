package com.performanceplus;

import java.util.Random;

public class ParticleBudgetSystem {

    private static boolean enabled = true;

    private static int particleBudget = 1500;

    private static int currentParticles = 0;

    private static final Random RANDOM =
            new Random();

    public static boolean shouldSpawnParticle(
            double distance
    ) {

        if (!enabled) {
            return true;
        }

        currentParticles++;

        if (currentParticles > particleBudget) {

            if (distance > 16) {
                return RANDOM.nextInt(4) == 0;
            }

            if (distance > 32) {
                return RANDOM.nextInt(8) == 0;
            }

            if (distance > 64) {
                return false;
            }
        }

        return true;
    }

    public static void resetFrameBudget() {

        currentParticles = 0;
    }

    public static String getStats() {

        return
                "Particles: " +
                currentParticles +
                "/" +
                particleBudget;
    }
}
