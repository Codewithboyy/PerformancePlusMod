package com.performanceplus.mixin;

import com.performanceplus.AdaptiveTickOptimizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityTickMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void performanceplus$adaptiveTick(
            CallbackInfo ci
    ) {

        Entity entity =
                (Entity)(Object)this;

        MinecraftClient client =
                MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        boolean shouldTick =
                AdaptiveTickOptimizer.shouldTick(
                        entity,
                        client.player.getX(),
                        client.player.getY(),
                        client.player.getZ(),
                        entity.age
                );

        if (!shouldTick) {
            ci.cancel();
        }
    }
}
