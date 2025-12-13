package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.CameraUpdateListener;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GameRenderer.class)
public class CameraMixin {

    @ModifyArgs(
            method = "updateCamera",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"
            )
    )
    private void update(Args args) {
        CameraUpdateListener.CameraUpdateEvent event =
                new CameraUpdateListener.CameraUpdateEvent(
                        ((Double) args.get(0)).doubleValue(),
                        ((Double) args.get(1)).doubleValue(),
                        ((Double) args.get(2)).doubleValue()
                );

        EventManager.fire((Event) event);

        args.set(0, event.getX());
        args.set(1, event.getY());
        args.set(2, event.getZ());
    }
}
