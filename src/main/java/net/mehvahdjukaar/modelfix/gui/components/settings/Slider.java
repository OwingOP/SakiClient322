package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.MathUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.class_332;
import net.minecraft.class_3532;

public final class Slider extends RenderableSetting {
  public boolean dragging;
  
  public double offsetX;
  
  public double lerpedOffsetX = 0.0D;
  
  private final NumberSetting setting;
  
  public Color currentColor1;
  
  public Color currentColor2;
  
  private Color currentAlpha;
  
  public Slider(ModuleButton parent, Setting<?> setting, int offset) {
    super(parent, setting, offset);
    this.setting = (NumberSetting)setting;
  }
  
  public void onUpdate() {
    Color clr = Utils.getMainColor(0, this.parent.settings.indexOf(this)).darker();
    Color clr2 = Utils.getMainColor(0, this.parent.settings.indexOf(this) + 1).darker();
    if (this.currentColor1 == null) {
      this.currentColor1 = new Color(clr.getRed(), clr.getGreen(), clr.getBlue(), 0);
    } else {
      this.currentColor1 = new Color(clr.getRed(), clr.getGreen(), clr.getBlue(), this.currentColor1.getAlpha());
    } 
    if (this.currentColor2 == null) {
      this.currentColor2 = new Color(clr2.getRed(), clr2.getGreen(), clr2.getBlue(), 0);
    } else {
      this.currentColor2 = new Color(clr2.getRed(), clr2.getGreen(), clr2.getBlue(), this.currentColor2.getAlpha());
    } 
    int toAlpha = 255;
    if (this.currentColor1.getAlpha() != toAlpha)
      this.currentColor1 = ColorUtils.smoothAlphaTransition(0.05F, toAlpha, this.currentColor1); 
    if (this.currentColor2.getAlpha() != toAlpha)
      this.currentColor2 = ColorUtils.smoothAlphaTransition(0.05F, toAlpha, this.currentColor2); 
    super.onUpdate();
  }
  
  public void render(class_332 context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
    this.offsetX = (this.setting.getValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()) * parentWidth();
    this.lerpedOffsetX = MathUtils.goodLerp((float)(0.5D * delta), this.lerpedOffsetX, this.offsetX);
    context.method_25296(parentX(), parentY() + this.offset + parentOffset() + 25, (int)(parentX() + this.lerpedOffsetX), parentY() + this.offset + parentOffset() + parentHeight(), this.currentColor1.getRGB(), this.currentColor2.getRGB());
    TextRenderer.drawString(String.valueOf(this.setting.getName()) + ": " + String.valueOf(this.setting.getName()), context, parentX() + 5, parentY() + parentOffset() + this.offset + 9, (new Color(245, 245, 245, 255)).getRGB());
    if (!this.parent.parent.dragging) {
      int toHoverAlpha = isHovered(mouseX, mouseY) ? 15 : 0;
      if (this.currentAlpha == null) {
        this.currentAlpha = new Color(255, 255, 255, toHoverAlpha);
      } else {
        this.currentAlpha = new Color(255, 255, 255, this.currentAlpha.getAlpha());
      } 
      if (this.currentAlpha.getAlpha() != toHoverAlpha)
        this.currentAlpha = ColorUtils.smoothAlphaTransition(0.05F, toHoverAlpha, this.currentAlpha); 
      context.method_25294(parentX(), parentY() + parentOffset() + this.offset, parentX() + parentWidth(), parentY() + parentOffset() + this.offset + parentHeight(), this.currentAlpha.getRGB());
    } 
  }
  
  public void onGuiClose() {
    this.currentColor1 = null;
    this.currentColor2 = null;
    super.onGuiClose();
  }
  
  private void slide(double mouseX) {
    double a = mouseX - parentX();
    double b = class_3532.method_15350(a / parentWidth(), 0.0D, 1.0D);
    this.setting.setValue(MathUtils.roundToDecimal(b * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), this.setting.getIncrement()));
  }
  
  public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.mouseOver && this.parent.extended && 
      keyCode == 259)
      this.setting.setValue(this.setting.getOriginalValue()); 
    super.keyPressed(keyCode, scanCode, modifiers);
  }
  
  public void mouseClicked(double mouseX, double mouseY, int button) {
    if (isHovered(mouseX, mouseY) && button == 0) {
      this.dragging = true;
      slide(mouseX);
    } 
    super.mouseClicked(mouseX, mouseY, button);
  }
  
  public void mouseReleased(double mouseX, double mouseY, int button) {
    if (this.dragging && button == 0)
      this.dragging = false; 
    super.mouseReleased(mouseX, mouseY, button);
  }
  
  public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    if (this.dragging)
      slide(mouseX); 
    super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
  }
}
