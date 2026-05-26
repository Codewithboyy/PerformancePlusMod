package com.performanceplus.mixin;

import com.performanceplus.BlockEntityOptimizer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderer.class)
public interface BlockEntityRendererMixin<T extends BlockEntity> {

    @Inject(
            method = "rendersOutsideBoundingBox",
            at = @At("HEAD"),
            cancellable = true
    )
    default void performanceplus$optimizeBERendering(
            T blockEntity,
            CallbackInfoReturnable<Boolean> cir
    ) {

        MinecraftClient client =
                MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        boolean shouldRender =
                BlockEntityOptimizer.shouldRender(
                        blockEntity,
                        client.player.getX(),
                        client.player.getY(),
                        client.player.getZ()
                );

        if (!shouldRender) {
            cir.setReturnValue(false);
        }
    }
}
