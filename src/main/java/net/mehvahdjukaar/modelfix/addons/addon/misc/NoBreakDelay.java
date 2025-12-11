package net.mehvahdjukaar.modelfix.addons.addon.misc;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.minecraft.class_4587;

public final class NoBreakDelay extends Module {
  public NoBreakDelay() {
    super((CharSequence)EncryptedString.of("No Break Delay"), -1, Category.MISC);
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
}
