package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.ButtonListener;
import net.mehvahdjukaar.modelfix.event.events.MouseMoveListener;
import net.mehvahdjukaar.modelfix.event.events.MouseUpdateListener;
import net.minecraft.class_310;
import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_312.class})
public class MouseMixin {
  @Shadow
  @Final
  private class_310 field_1779;
  
  @Inject(method = {"updateMouse"}, at = {@At("RETURN")})
  private void onMouseUpdate(CallbackInfo ci) {
    EventManager.fire((Event)new MouseUpdateListener.MouseUpdateEvent());
  }
  
  @Inject(method = {"onCursorPos"}, at = {@At("HEAD")}, cancellable = true)
  private void onMouseMove(long window, double x, double y, CallbackInfo ci) {
    MouseMoveListener.MouseMoveEvent event = new MouseMoveListener.MouseMoveEvent(window, x, y);
    EventManager.fire((Event)event);
    if (event.isCancelled())
      ci.cancel(); 
  }
  
  @Inject(method = {"onMouseButton"}, at = {@At("HEAD")})
  private void onMousePress(long window, int button, int action, int mods, CallbackInfo ci) {
    EventManager.fire((Event)new ButtonListener.ButtonEvent(button, window, action));
  }
}
