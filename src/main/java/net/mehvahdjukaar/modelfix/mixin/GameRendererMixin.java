package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.addons.addon.misc.Freecam;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.GameRenderListener;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_757.class})
public abstract class GameRendererMixin {
  @Shadow
  @Final
  private class_4184 field_18765;
  
  @Shadow
  public abstract Matrix4f method_22973(double paramDouble);
  
  @Shadow
  protected abstract double method_3196(class_4184 paramclass_4184, float paramFloat, boolean paramBoolean);
  
  @Inject(method = {"renderWorld"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1)})
  private void onWorldRender(class_9779 tickCounter, CallbackInfo ci) {
    double d = method_3196(this.field_18765, tickCounter.method_60637(true), true);
    Matrix4f matrix4f = method_22973(d);
    class_4587 matrixStack = new class_4587();
    EventManager.fire((Event)new GameRenderListener.GameRenderEvent(matrixStack, tickCounter.method_60637(true)));
  }
  
  @Inject(method = {"shouldRenderBlockOutline"}, at = {@At("HEAD")}, cancellable = true)
  private void onShouldRenderBlockOutline(CallbackInfoReturnable<Boolean> cir) {
    if (((Freecam)saki.INSTANCE.getModuleManager().getModule(Freecam.class)).isEnabled())
      cir.setReturnValue(Boolean.valueOf(false)); 
  }
}
