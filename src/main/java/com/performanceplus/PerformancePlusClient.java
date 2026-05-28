
package com.performanceplus;

import com.performanceplus.renderer.AsyncChunkBuilder;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.fabricmc.loader.api.FabricLoader;

public class PerformancePlusClient
        implements ClientModInitializer {

    private static boolean initialized = false;

    @Override
    public void onInitializeClient() {

        PerformancePlus.LOGGER.info(
                "Performance Plus loading..."
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.world == null) {
                return;
            }

            if (!initialized) {

                initialized = true;

                SystemDetector.detectSystem();

                OptimizationProfiles.applyProfile();



                // Mod compatibility detection

                if (FabricLoader.getInstance()
                        .isModLoaded("sodium")) {

                    PerformancePlus.LOGGER.info(
                            "Sodium detected"
                    );
                }

                if (FabricLoader.getInstance()
                        .isModLoaded("lithium")) {

                    PerformancePlus.LOGGER.info(
                            "Lithium detected"
                    );
                }

                if (FabricLoader.getInstance()
                        .isModLoaded("ferritecore")) {

                    PerformancePlus.LOGGER.info(
                            "FerriteCore detected"
                    );
                }

                if (FabricLoader.getInstance()
                        .isModLoaded("immediatelyfast")) {

                    PerformancePlus.LOGGER.info(
                            "ImmediatelyFast detected"
                    );
                }

                if (FabricLoader.getInstance()
                        .isModLoaded("entityculling")) {

                    PerformancePlus.LOGGER.info(
                            "EntityCulling detected"
                    );
                }

                if (FabricLoader.getInstance()
                        .isModLoaded("moreculling")) {

                    PerformancePlus.LOGGER.info(
                            "MoreCulling detected"
                    );
                }



                // Initialize systems

                AsyncChunkBuilder.init();

                ChunkScheduler.init();

                OcclusionEngine.init();

                FPSOptimizer.init();

                EntityOptimizer.init();

                ParticleOptimizer.init();

                GraphicsOptimizer.init();



                PerformancePlus.LOGGER.info(
                        "Performance Plus initialized!"
                );

                PerformancePlus.LOGGER.info(
                        "Device Type: {}",
                        SystemDetector.getDeviceType()
                );
            }



            // Runtime systems

            ParticleBudgetSystem.resetFrameBudget();

            DynamicChunkLimiter.resetFrame();
        });
    }
}
