package com.performanceplus;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class MemoryOptimizer {
	private static int tickCounter = 0;
	private static final int GC_INTERVAL = 6000; // Run every 5 minutes (6000 ticks)
	private static final Runtime runtime = Runtime.getRuntime();
	
	public static void init() {
		// Register server tick event for periodic optimization
		ServerTickEvents.END_SERVER_TICK.register(MemoryOptimizer::onServerTick);
		PerformancePlus.LOGGER.info("Memory Optimizer initialized");
	}
	
	private static void onServerTick(MinecraftServer server) {
		tickCounter++;
		
		// Perform memory check every 5 minutes
		if (tickCounter >= GC_INTERVAL) {
			tickCounter = 0;
			optimizeMemory();
		}
	}
	
	private static void optimizeMemory() {
		long memoryBefore = getUsedMemory();
		
		// Suggest garbage collection when memory usage is high
		long maxMemory = runtime.maxMemory();
		long usedMemory = getUsedMemory();
		double memoryUsagePercent = (double) usedMemory / maxMemory * 100;
		
		if (memoryUsagePercent > 75) {
			PerformancePlus.LOGGER.info("Memory usage at {}%, suggesting garbage collection...", 
				String.format("%.1f", memoryUsagePercent));
			System.gc();
			
			long memoryAfter = getUsedMemory();
			long freed = memoryBefore - memoryAfter;
			if (freed > 0) {
				PerformancePlus.LOGGER.info("Freed {} MB of memory", freed / 1024 / 1024);
			}
		}
	}
	
	private static long getUsedMemory() {
		return runtime.totalMemory() - runtime.freeMemory();
	}
	
	public static String getMemoryStats() {
		long maxMemory = runtime.maxMemory() / 1024 / 1024;
		long usedMemory = getUsedMemory() / 1024 / 1024;
		long freeMemory = runtime.freeMemory() / 1024 / 1024;
		
		return String.format("Memory: %d MB / %d MB (Free: %d MB)", 
			usedMemory, maxMemory, freeMemory);
	}
}
