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
                                     .executes(PerformanceCommand::executeStats)
                    );

                    dispatcher.register(
                            CommandManager.literal("performanceplus")
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
}
