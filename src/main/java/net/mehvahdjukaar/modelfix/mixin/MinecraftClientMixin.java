package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.BlockBreakingListener;
import net.mehvahdjukaar.modelfix.event.events.ItemUseListener;
import net.mehvahdjukaar.modelfix.event.events.ResolutionListener;
import net.mehvahdjukaar.modelfix.event.events.TickListener;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.MouseSimulation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Nullable public ClientWorld world;
    @Shadow @Final private Window window;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (this.world != null) {
            TickListener.TickEvent event = new TickListener.TickEvent();
            EventManager.fire((Event) event);
        }
    }

    // Ensure target method exists for your MC version. If it's mapped differently, swap to window.setScale or Window resize hooks.
    @Inject(method = "onResolutionChanged", at = @At("HEAD"))
    private void onResolutionChanged(CallbackInfo ci) {
        EventManager.fire((Event) new ResolutionListener.ResolutionEvent(this.window));
    }

    @Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
    private void onItemUse(CallbackInfo ci) {
        ItemUseListener.ItemUseEvent event = new ItemUseListener.ItemUseEvent();
        EventManager.fire((Event) event);
        if (event.isCancelled()) ci.cancel();

        if (MouseSimulation.isMouseButtonPressed(1)) {
            MouseSimulation.mouseButtons.put(1, false);
            ci.cancel();
        }
    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        AttackListener.AttackEvent event = new AttackListener.AttackEvent();
        EventManager.fire((Event) event);
        if (event.isCancelled()) {
            cir.setReturnValue(false);
            return;
        }

        if (MouseSimulation.isMouseButtonPressed(0)) {
            MouseSimulation.mouseButtons.put(0, false);
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
    private void onBlockBreaking(boolean breaking, CallbackInfo ci) {
        BlockBreakingListener.BlockBreakingEvent event = new BlockBreakingListener.BlockBreakingEvent();
        EventManager.fire((Event) event);
        if (event.isCancelled()) {
            ci.cancel();
            return;
        }

        if (MouseSimulation.isMouseButtonPressed(0)) {
            MouseSimulation.mouseButtons.put(0, false);
            ci.cancel();
        }
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void onClose(CallbackInfo ci) {
        saki.INSTANCE.getProfileManager().saveProfile();
    }
}
