package net.wibot.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.wibot.Recenter.*;

@Mixin(Camera.class)
public abstract class CameraMixin
{
    @Unique
    boolean recentering = false;

    @Unique
    int target_angle = 5;
    @Unique
    float recenter_time = 0;

    @Inject(method = "update", at = @At("RETURN"))
    private void OnCameraUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci)
    {

        float deltaTime = MinecraftClient.getInstance().getLastFrameDuration();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        float angle =  (target_angle + player.getPitch()) / 2F;

        float angle_delta = (player.getPitch() - angle) * 2 * deltaTime;

        if (recentering) {
            player.setPitch(player.getPitch() - angle_delta);
            recenter_time += 1 * deltaTime;
        }

        if (recenter_keybinding.wasPressed()) {
            recentering = true;
        }

        boolean timeout = recenter_time > 5;

        if ((angle >= target_angle - 0.5 & target_angle + 0.5 >= angle) || timeout) {
            recentering = false;
            recenter_time = 0;
        }
    }
}