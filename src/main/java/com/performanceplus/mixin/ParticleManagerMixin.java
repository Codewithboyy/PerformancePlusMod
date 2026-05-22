package com.performanceplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
            at = @At("HEAD"),
            cancellable = true)
    private void performanceplus$limitFarParticles(
            Particle particle,
            CallbackInfo ci
    ) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        double distanceSq =
                client.player.squaredDistanceTo(
                        particle.getX(),
                        particle.getY(),
                        particle.getZ()
                );

        // Skip extremely far particles
        if (distanceSq > 2500) {
            ci.cancel();
        }
    }
}
