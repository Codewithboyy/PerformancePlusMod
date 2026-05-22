package com.performanceplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(
            method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void performanceplus$limitFarParticles(
            Particle particle,
            CallbackInfo ci
    ) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        Box box = particle.getBoundingBox();

        double px = box.minX;
        double py = box.minY;
        double pz = box.minZ;

        double dx = px - client.player.getX();
        double dy = py - client.player.getY();
        double dz = pz - client.player.getZ();

        double distanceSq = dx * dx + dy * dy + dz * dz;

        if (distanceSq > 2500) {
            ci.cancel();
        }
    }
}
