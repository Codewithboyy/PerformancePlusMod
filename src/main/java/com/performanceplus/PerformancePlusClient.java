package com.performanceplus;

import net.fabricmc.api.ClientModInitializer;

public class PerformancePlusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		PerformancePlus.LOGGER.info("Performance Plus client initialized!");
		
		// Initialize client-side optimizations
		FPSOptimizer.init();
		EntityOptimizer.init();
		ParticleOptimizer.init();
		GraphicsOptimizer.init();
		
		PerformancePlus.LOGGER.info("All client optimizations loaded - targeting 300+ FPS!");
	}
}
