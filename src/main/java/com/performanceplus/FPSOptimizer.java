package com.performanceplus;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class FPSOptimizer {
	private static long lastFrameTime = System.nanoTime();
	private static double averageFPS = 0;
	private static int frameCount = 0;
	private static final int FPS_SAMPLE_SIZE = 100;
	
	public static void init() {
		// Register client tick event for FPS monitoring
		ClientTickEvents.END_CLIENT_TICK.register(FPSOptimizer::onClientTick);
		
		PerformancePlus.LOGGER.info("FPS Optimizer initialized");
	}
	
	private static void onClientTick(MinecraftClient client) {
		// Calculate FPS
		long currentTime = System.nanoTime();
		double deltaTime = (currentTime - lastFrameTime) / 1_000_000_000.0;
		lastFrameTime = currentTime;
		
		if (deltaTime > 0) {
			double currentFPS = 1.0 / deltaTime;
			averageFPS = (averageFPS * frameCount + currentFPS) / (frameCount + 1);
			frameCount++;
			
			// Reset average after sample size
			if (frameCount >= FPS_SAMPLE_SIZE) {
				frameCount = 0;
				
				// Log if FPS is low
				if (averageFPS < 30) {
					PerformancePlus.LOGGER.warn("Low FPS detected: {} (avg over {} frames)", 
						String.format("%.1f", averageFPS), FPS_SAMPLE_SIZE);
				}
			}
		}
	}
	
	public static double getAverageFPS() {
		return averageFPS;
	}
	
	public static String getFPSStats() {
		return String.format("Average FPS: %.1f", averageFPS);
	}
}
