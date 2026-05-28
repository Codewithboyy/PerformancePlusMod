package com.performanceplus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PerformancePlusClient implements ClientModInitializer {

    private static boolean initialized = false;

    @Override
    public void onInitializeClient() {

        PerformancePlus.LOGGER.info(
                "Performance Plus client initialized!"
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.world == null) {
                return;
            }

            if (!initialized) {

                initialized = true;

                SystemDetector.detectSystem();

                OptimizationProfiles.applyProfile();

                ChunkScheduler.init();

                OcclusionEngine.init();

                FPSOptimizer.init();

                EntityOptimizer.init();

                ParticleOptimizer.init();

                GraphicsOptimizer.init();

                PerformancePlus.LOGGER.info(
                        "Adaptive optimization initialized!"
                );
            }

            ParticleBudgetSystem.resetFrameBudget();
            DynamicChunkLimiter.resetFrame();
            DynamicChunkLimiter.updateDynamicBudget(
                FPSOptimizer.getAverageFPS()
            );
        });
    }
}
