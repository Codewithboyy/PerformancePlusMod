package com.performanceplus;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PerformanceCommand {

    public static void register() {

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {

                    dispatcher.register(
                            CommandManager.literal("perfstats")
                                    .requires(source -> source.hasPermissionLevel(2))
                                    .executes(PerformanceCommand::executeStats)
                    );

                    dispatcher.register(
                            CommandManager.literal("performanceplus")
                                    .requires(source -> source.hasPermissionLevel(2))
                                    .executes(PerformanceCommand::executeMain)
                    );
                }
        );
    }

    private static int executeMain(
            CommandContext<ServerCommandSource> context
    ) {

        ServerCommandSource source = context.getSource();

        source.sendFeedback(
                () -> Text.literal("§aPerformancePlus Loaded"),
                false
        );

        return 1;
    }

    private static int executeStats(
            CommandContext<ServerCommandSource> context
    ) {

        ServerCommandSource source = context.getSource();

        source.sendFeedback(
                () -> Text.literal("§6§l=== PerformancePlus ==="),
                false
        );

        source.sendFeedback(
                () -> Text.literal("§7Adaptive Visibility Optimization"),
                false
        );

        source.sendFeedback(
                () -> Text.literal("§7Underground Chunk Optimization"),
                false
        );

        source.sendFeedback(
                () -> Text.literal("§7Entity Frustum Culling"),
                false
        );

        source.sendFeedback(
                () -> Text.literal("§7Particle Distance Culling"),
                false
        );

        source.sendFeedback(
                () -> Text.literal("§aOptimizer Running"),
                false
        );

        return 1;
    }
}e);
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

