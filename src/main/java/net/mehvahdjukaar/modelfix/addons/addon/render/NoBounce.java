package net.mehvahdjukaar.modelfix.addons.addon.render;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.minecraft.class_4587;

public final class NoBounce extends Module {
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public NoBounce() {
    super((CharSequence)EncryptedString.of("No Bounce"), -1, Category.RENDER);
  }
}
