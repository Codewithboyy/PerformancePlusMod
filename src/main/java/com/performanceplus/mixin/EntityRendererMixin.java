package com.performanceplus.mixin;

import com.performanceplus.EntityRenderOptimizer;
import com.performanceplus.renderer.VisibilityEngine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

    @Inject(
            method = "shouldRender",
            at = @At("HEAD"),
            cancellable = true
    )
    private void performanceplus$optimizeEntityRendering(
            T entity,
            net.minecraft.client.render.Frustum frustum,
            double x,
            double y,
            double z,
            CallbackInfoReturnable<Boolean> cir
    ) {

        MinecraftClient client =
                MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        boolean shouldRender =
                EntityRenderOptimizer.shouldRender(
                        entity,
                        client.player.getX(),
                        client.player.getY(),
                        client.player.getZ()
                );

        if (!shouldRender) {
            boolean visible =
                VisibilityEngine.isVisible(
                    entity.getX(),
                    entity.getY(),
                    entity.getZ()
                );

            if (!visible) {

                cir.setReturnValue(false);

                return;
            }
            cir.setReturnValue(false);
        }
    }
}
