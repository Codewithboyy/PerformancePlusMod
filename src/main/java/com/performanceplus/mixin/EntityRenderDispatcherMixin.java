package com.performanceplus.mixin;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void performanceplus$optimizeEntityRendering(
            Entity entity,
            Frustum frustum,
            double x,
            double y,
            double z,
            CallbackInfoReturnable<Boolean> cir
    ) {

        double distanceSq = entity.squaredDistanceTo(x, y, z);

        // Skip extremely far invisible entities
        if (distanceSq > 4096 && !frustum.isVisible(entity.getBoundingBox())) {
            cir.setReturnValue(false);
        }
    }
}