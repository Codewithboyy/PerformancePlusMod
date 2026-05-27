package com.performanceplus;

import net.fabricmc.api.ClientModInitializer;

public class PerformancePlusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		PerformancePlus.LOGGER.info("Performance Plus client initialized!");

		SystemDetector.detectSystem();
                OptimizationProfiles.applyProfile();
		// Initialize client-side optimizations
                ChunkScheduler.init();
                OcclusionEngine.init();
		FPSOptimizer.init();
		EntityOptimizer.init();
		ParticleOptimizer.init();
		GraphicsOptimizer.init();
                ParticleBudgetSystem.resetFrameBudget();
		PerformancePlus.LOGGER.info("All client optimizations loaded - targeting 300+ FPS!");
	}
}
