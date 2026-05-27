package com.performanceplus.mixin;

import com.performanceplus.ParticleBudgetSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleBudgetMixin {

    @Inject(
            method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void performanceplus$budgetParticles(
            Particle particle,
            CallbackInfo ci
    ) {

        MinecraftClient client =
                MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        Box box = particle.getBoundingBox();

        double dx =
                box.minX - client.player.getX();

        double dy =
                box.minY - client.player.getY();

        double dz =
                box.minZ - client.player.getZ();

        double distance =
                Math.sqrt(
                        dx * dx +
                        dy * dy +
                        dz * dz
                );

        boolean allowed =
                ParticleBudgetSystem
                        .shouldSpawnParticle(distance);

        if (!allowed) {
            ci.cancel();
        }
    }
}
