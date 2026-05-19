package com.performanceplus;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashMap;
import java.util.Map;

public class ChunkOptimizer {

    private static final Map<String, Integer> dimensionLoads =
        new HashMap<>();

    private static final Map<String, Integer> dimensionUnloads =
        new HashMap<>();

    private static int totalLoaded = 0;

    private static int totalUnloaded = 0;

    private static int activeChunks = 0;

    public static void init() {

        ServerChunkEvents.CHUNK_LOAD.register(
            ChunkOptimizer::onChunkLoad
        );

        ServerChunkEvents.CHUNK_UNLOAD.register(
            ChunkOptimizer::onChunkUnload
        );

        PerformancePlus.LOGGER.info(
            "Chunk Optimizer initialized"
        );
    }

    private static void onChunkLoad(
        ServerWorld world,
        WorldChunk chunk
    ) {

        totalLoaded++;
        activeChunks++;

        String dimension =
            world.getRegistryKey()
                 .getValue()
                 .toString();

        dimensionLoads.put(
            dimension,
            dimensionLoads.getOrDefault(dimension, 0) + 1
        );

        // Prevent negative chunk counts

        if (activeChunks < 0) {
            activeChunks = 0;
        }
    }

    private static void onChunkUnload(
        ServerWorld world,
        WorldChunk chunk
    ) {

        totalUnloaded++;
        activeChunks--;

        String dimension =
            world.getRegistryKey()
                 .getValue()
                 .toString();

        dimensionUnloads.put(
            dimension,
            dimensionUnloads.getOrDefault(dimension, 0) + 1
        );

        if (activeChunks < 0) {
            activeChunks = 0;
        }
    }

    public static int getActiveChunks() {
        return activeChunks;
    }

    public static int getTotalLoaded() {
        return totalLoaded;
    }

    public static int getTotalUnloaded() {
        return totalUnloaded;
    }

    public static Map<String, Integer> getDimensionLoads() {
        return new HashMap<>(dimensionLoads);
    }

    public static Map<String, Integer> getDimensionUnloads() {
        return new HashMap<>(dimensionUnloads);
    }

    public static boolean isChunkLoadHeavy() {

        return activeChunks > 1500;
    }

    public static String getChunkStats() {

        return String.format(
            "Chunks | Active: %d | Loaded: %d | Unloaded: %d",
            activeChunks,
            totalLoaded,
            totalUnloaded
        );
    }

    public static String getDimensionStats() {

        StringBuilder builder = new StringBuilder();

        for (String dimension : dimensionLoads.keySet()) {

            int loads =
                dimensionLoads.getOrDefault(dimension, 0);

            int unloads =
                dimensionUnloads.getOrDefault(dimension, 0);

            builder.append(dimension)
                   .append(" [")
                   .append(loads)
                   .append("/")
                   .append(unloads)
                   .append("] ");
        }

        return builder.toString().trim();
    }
}