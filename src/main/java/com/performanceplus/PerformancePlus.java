package com.performanceplus;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformancePlus implements ModInitializer {
	public static final String MOD_ID = "performanceplus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Performance Plus mod initialized!");
		
		// Initialize optimization modules
		MemoryOptimizer.init();
		ChunkOptimizer.init();
		
		// Register commands
		PerformanceCommand.register();
		
		LOGGER.info("All performance optimizations loaded successfully!");
	}
}
