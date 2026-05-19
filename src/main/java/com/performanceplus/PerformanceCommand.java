package com.performanceplus;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PerformanceCommand {
	
	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			// Performance statistics command
			dispatcher.register(CommandManager.literal("perfstats")
				.requires(source -> source.hasPermissionLevel(2))
				.executes(PerformanceCommand::executeStats));
			
			// Apply preset commands
			dispatcher.register(CommandManager.literal("perfpreset")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.literal("ultra_performance")
					.executes(ctx -> executePreset(ctx, "ultra_performance")))
				.then(CommandManager.literal("balanced")
					.executes(ctx -> executePreset(ctx, "balanced")))
				.then(CommandManager.literal("quality")
					.executes(ctx -> executePreset(ctx, "quality"))));
			
			// Resolution scale command
			dispatcher.register(CommandManager.literal("perfscale")
				.requires(source -> source.hasPermissionLevel(2))
				.then(CommandManager.literal("ultra_performance")
					.executes(ctx -> executeScalePreset(ctx, "ultra_performance")))
				.then(CommandManager.literal("high_performance")
					.executes(ctx -> executeScalePreset(ctx, "high_performance")))
				.then(CommandManager.literal("balanced")
					.executes(ctx -> executeScalePreset(ctx, "balanced")))
				.then(CommandManager.literal("quality")
					.executes(ctx -> executeScalePreset(ctx, "quality")))
				.then(CommandManager.literal("ultra_quality")
					.executes(ctx -> executeScalePreset(ctx, "ultra_quality"))));
		});
	}
	
	private static int executeStats(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		
		// Send performance statistics to player
		source.sendFeedback(() -> Text.literal("§6§l=== Performance Plus Statistics ==="), false);
		source.sendFeedback(() -> Text.literal(""), false);
		source.sendFeedback(() -> Text.literal("§e" + MemoryOptimizer.getMemoryStats()), false);
		source.sendFeedback(() -> Text.literal("§e" + ChunkOptimizer.getChunkStats()), false);
		source.sendFeedback(() -> Text.literal("§e" + FPSOptimizer.getFPSStats()), false);
		source.sendFeedback(() -> Text.literal(""), false);
		source.sendFeedback(() -> Text.literal("§b§l--- Render Optimizations ---"), false);
		source.sendFeedback(() -> Text.literal("§b" + ResolutionScaler.getStats()), false);
		source.sendFeedback(() -> Text.literal("§b" + EntityOptimizer.getStats()), false);
		source.sendFeedback(() -> Text.literal("§b" + ParticleOptimizer.getStats()), false);
		source.sendFeedback(() -> Text.literal("§b" + GraphicsOptimizer.getStats()), false);
		source.sendFeedback(() -> Text.literal(""), false);
		source.sendFeedback(() -> Text.literal("§7Use /perfpreset <preset> to change settings"), false);
		
		return 1;
	}
	
	private static int executePreset(CommandContext<ServerCommandSource> context, String preset) {
		ServerCommandSource source = context.getSource();
		
		switch (preset) {
			case "ultra_performance":
				GraphicsOptimizer.applyUltraPerformancePreset();
				source.sendFeedback(() -> Text.literal("§a✓ Ultra Performance preset applied!"), false);
				source.sendFeedback(() -> Text.literal("§7Optimized for 300+ FPS on iGPU"), false);
				break;
			case "balanced":
				GraphicsOptimizer.applyBalancedPreset();
				source.sendFeedback(() -> Text.literal("§a✓ Balanced preset applied!"), false);
				source.sendFeedback(() -> Text.literal("§7Good balance of quality and performance"), false);
				break;
			case "quality":
				GraphicsOptimizer.applyQualityPreset();
				source.sendFeedback(() -> Text.literal("§a✓ Quality preset applied!"), false);
				source.sendFeedback(() -> Text.literal("§7Better visuals with solid performance"), false);
				break;
		}
		
		return 1;
	}
	
	private static int executeScalePreset(CommandContext<ServerCommandSource> context, String preset) {
		ServerCommandSource source = context.getSource();
		
		ResolutionScaler.setPreset(preset);
		source.sendFeedback(() -> Text.literal("§a✓ Resolution scale preset applied: " + preset), false);
		
		return 1;
	}
}

