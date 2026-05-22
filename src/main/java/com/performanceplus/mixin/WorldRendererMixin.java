package com.performanceplus.mixin;

import com.performanceplus.VisibilityOptimizer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.BuiltChunk.class)
public class WorldRendererMixin {

    @Inject(method = "scheduleRebuild", at = @At("HEAD"), cancellable = true)
    private void performanceplus$reduceHiddenChunkUpdates(
            boolean important,
            CallbackInfo ci
    ) {

        ChunkBuilder.BuiltChunk self =
                (ChunkBuilder.BuiltChunk)(Object)this;

        BlockPos pos = self.getOrigin();

        // Delay rebuilds for hidden underground chunks
        if (VisibilityOptimizer.isUndergroundHidden(pos)) {

            // Ignore non-important rebuilds
            if (!important) {
                ci.cancel();
            }
        }
    }
}
