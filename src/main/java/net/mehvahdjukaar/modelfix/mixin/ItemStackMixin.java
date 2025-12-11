package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.addons.addon.render.NoBounce;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1799.class})
public class ItemStackMixin {
  @Inject(method = {"getBobbingAnimationTime"}, at = {@At("HEAD")}, cancellable = true)
  private void removeBounceAnimation(CallbackInfoReturnable<Integer> cir) {
    if (saki.mc.field_1724 == null)
      return; 
    NoBounce noBounce = (NoBounce)saki.INSTANCE.getModuleManager().getModule(NoBounce.class);
    if (saki.INSTANCE != null && saki.mc.field_1724 != null && noBounce.isEnabled()) {
      class_1799 mainHandStack = saki.mc.field_1724.method_6047();
      if (mainHandStack.method_31574(class_1802.field_8301))
        cir.setReturnValue(Integer.valueOf(0)); 
    } 
  }
}
