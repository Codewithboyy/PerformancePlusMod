package com.performanceplus;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashMap;
import java.util.Map;

public class ChunkOptimizer {
	private static final Map<String, Integer> chunkLoadStats = new HashMap<>();
	private static int totalChunksLoaded = 0;
	private static int totalChunksUnloaded = 0;
	
	public static void init() {
		// Track chunk loading/unloading
		ServerChunkEvents.CHUNK_LOAD.register(ChunkOptimizer::onChunkLoad);
		ServerChunkEvents.CHUNK_UNLOAD.register(ChunkOptimizer::onChunkUnload);
		
		PerformancePlus.LOGGER.info("Chunk Optimizer initialized");
	}
	
	private static void onChunkLoad(ServerWorld world, WorldChunk chunk) {
		totalChunksLoaded++;
		
		// Track per-dimension statistics
		String dimension = world.getRegistryKey().getValue().toString();
		chunkLoadStats.put(dimension, chunkLoadStats.getOrDefault(dimension, 0) + 1);
		
		// Log if chunk loading is excessive (potential performance issue)
		if (totalChunksLoaded % 1000 == 0) {
			PerformancePlus.LOGGER.info("Chunk loading stats - Loaded: {}, Unloaded: {}", 
				totalChunksLoaded, totalChunksUnloaded);
		}
	}
	
	private static void onChunkUnload(ServerWorld world, WorldChunk chunk) {
		totalChunksUnloaded++;
	}
	
	public static String getChunkStats() {
		return String.format("Chunks - Loaded: %d, Unloaded: %d, Active: %d", 
			totalChunksLoaded, totalChunksUnloaded, totalChunksLoaded - totalChunksUnloaded);
	}
	
	public static Map<String, Integer> getDimensionStats() {
		return new HashMap<>(chunkLoadStats);
	}
}
