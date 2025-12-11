package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.imixin.IKeyBinding;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({class_304.class})
public abstract class KeyBindingMixin implements IKeyBinding {
  @Shadow
  private class_3675.class_306 field_1655;
  
  public boolean isActuallyPressed() {
    long handle = saki.mc.method_22683().method_4490();
    int code = this.field_1655.method_1444();
    return class_3675.method_15987(handle, code);
  }
  
  public void resetPressed() {
    method_23481(isActuallyPressed());
  }
  
  @Shadow
  public abstract void method_23481(boolean paramBoolean);
}
