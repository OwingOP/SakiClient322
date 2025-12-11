package net.mehvahdjukaar.modelfix.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public final class ClickGui extends class_437 {
  public List<Window> windows = new ArrayList<>();
  
  public Color currentColor;
  
  private static final StackWalker sw = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
  
  public ClickGui() {
    super((class_2561)class_2561.method_43473());
    int offsetX = 50;
    for (Category category : Category.values()) {
      this.windows.add(new Window(offsetX, 50, 230, 30, category, this));
      offsetX += 250;
    } 
  }
  
  public boolean isDraggingAlready() {
    for (Window window : this.windows) {
      if (window.dragging)
        return true; 
    } 
    return false;
  }
  
  protected void method_56131() {
    if (this.field_22787 == null)
      return; 
    super.method_56131();
  }
  
  public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
    if (saki.mc.field_1755 == this) {
      if (saki.INSTANCE.previousScreen != null)
          saki.INSTANCE.previousScreen.method_25394(context, 0, 0, delta);
      if (this.currentColor == null) {
        this.currentColor = new Color(0, 0, 0, 0);
      } else {
        this.currentColor = new Color(0, 0, 0, this.currentColor.getAlpha());
      } 
      if (this.currentColor.getAlpha() != (ClickGUI.background.getValue() ? 200 : 0))
        this.currentColor = ColorUtils.smoothAlphaTransition(0.05F, ClickGUI.background.getValue() ? 200 : 0, this.currentColor); 
      if (saki.mc.field_1755 instanceof ClickGui)
        context.method_25294(0, 0, saki.mc.method_22683().method_4480(), saki.mc.method_22683().method_4507(), this.currentColor.getRGB());
      RenderUtils.unscaledProjection();
      mouseX *= (int)class_310.method_1551().method_22683().method_4495();
      mouseY *= (int)class_310.method_1551().method_22683().method_4495();
      super.method_25394(context, mouseX, mouseY, delta);
      for (Window window : this.windows) {
        window.render(context, mouseX, mouseY, delta);
        window.updatePosition(mouseX, mouseY, delta);
      } 
      RenderUtils.scaledProjection();
    } 
  }
  
  public boolean method_25404(int keyCode, int scanCode, int modifiers) {
    for (Window window : this.windows)
      window.keyPressed(keyCode, scanCode, modifiers); 
    return super.method_25404(keyCode, scanCode, modifiers);
  }
  
  public boolean method_25402(double mouseX, double mouseY, int button) {
    mouseX *= (int)class_310.method_1551().method_22683().method_4495();
    mouseY *= (int)class_310.method_1551().method_22683().method_4495();
    for (Window window : this.windows)
      window.mouseClicked(mouseX, mouseY, button); 
    return super.method_25402(mouseX, mouseY, button);
  }
  
  public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    mouseX *= (int)class_310.method_1551().method_22683().method_4495();
    mouseY *= (int)class_310.method_1551().method_22683().method_4495();
    for (Window window : this.windows)
      window.mouseDragged(mouseX, mouseY, button, deltaX, deltaY); 
    return super.method_25403(mouseX, mouseY, button, deltaX, deltaY);
  }
  
  public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    class_310 mc = class_310.method_1551();
    mouseY *= mc.method_22683().method_4495();
    for (Window window : this.windows)
      window.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount); 
    return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
  }
  
  public boolean method_25421() {
    return false;
  }
  
  public void method_25419() {
    ((ClickGUI)saki.INSTANCE.getModuleManager().getModule(ClickGUI.class)).setEnabledStatus(false);
    onGuiClose();
  }
  
  public void onGuiClose() {
      saki.mc.method_29970(saki.INSTANCE.previousScreen);
    this.currentColor = null;
    for (Window window : this.windows)
      window.onGuiClose(); 
  }
  
  public boolean method_25406(double mouseX, double mouseY, int button) {
    mouseX *= (int)class_310.method_1551().method_22683().method_4495();
    mouseY *= (int)class_310.method_1551().method_22683().method_4495();
    for (Window window : this.windows)
      window.mouseReleased(mouseX, mouseY, button); 
    return super.method_25406(mouseX, mouseY, button);
  }
}
