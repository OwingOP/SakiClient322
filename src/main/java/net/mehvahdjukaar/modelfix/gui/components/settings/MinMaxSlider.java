package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.MathUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;

public final class MinMaxSlider extends RenderableSetting {
  public boolean draggingMin;
  
  public boolean draggingMax;
  
  public double offsetMinX;
  
  public double offsetMaxX;
  
  public double lerpedOffsetMinX = parentX();
  
  public double lerpedOffsetMaxX = (parentX() + parentWidth());
  
  public MinMaxSetting setting;
  
  public Color currentColor1;
  
  public Color currentColor2;
  
  private Color currentAlpha;
  
  public MinMaxSlider(ModuleButton parent, Setting<?> setting, int offset) {
    super(parent, setting, offset);
    this.setting = (MinMaxSetting)setting;
  }
  
  public void render(class_332 context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
    class_4587 matrices = context.method_51448();
    this.offsetMinX = (this.setting.getMinValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()) * parentWidth();
    this.offsetMaxX = (this.setting.getMaxValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()) * parentWidth();
    this.lerpedOffsetMinX = MathUtils.goodLerp((float)(0.5D * delta), this.lerpedOffsetMinX, this.offsetMinX);
    this.lerpedOffsetMaxX = MathUtils.goodLerp((float)(0.5D * delta), this.lerpedOffsetMaxX, this.offsetMaxX);
    CharSequence str = String.valueOf(this.setting.getName()) + ": " + String.valueOf(this.setting.getName());
    context.method_25296((int)(parentX() + this.lerpedOffsetMinX), parentY() + this.offset + parentOffset() + 25, (int)(parentX() + this.lerpedOffsetMinX + getLength()), parentY() + this.offset + parentOffset() + parentHeight(), this.currentColor1.getRGB(), this.currentColor2.getRGB());
    float scalable = 0.8F;
    matrices.method_22903();
    matrices.method_22905(scalable, scalable, 1.0F);
    TextRenderer.drawString(str, context, (int)((parentX() + 5) / scalable), (int)((parentY() + parentOffset() + this.offset + 9) / scalable), (new Color(245, 245, 245, 255)).getRGB());
    matrices.method_22905(1.0F, 1.0F, 1.0F);
    matrices.method_22909();
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
  
  public void mouseClicked(double mouseX, double mouseY, int button) {
    if (button == 0)
      if (isHoveredMin(mouseX, mouseY) || isMouseInMin(mouseX, mouseY)) {
        this.draggingMin = true;
        slideMin(mouseX);
      } else if (isHoveredMax(mouseX, mouseY) || isMouseInMax(mouseX, mouseY)) {
        this.draggingMax = true;
        slideMax(mouseX);
      }  
    super.mouseClicked(mouseX, mouseY, button);
  }
  
  public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.mouseOver && keyCode == 259) {
      this.setting.setMaxValue(this.setting.getOriginalMaxValue());
      this.setting.setMinValue(this.setting.getOriginalMinValue());
    } 
    super.keyPressed(keyCode, scanCode, modifiers);
  }
  
  public boolean isHoveredMin(double mouseX, double mouseY) {
    return (isHovered(mouseX, mouseY) && mouseX > 
      parentX() + this.offsetMinX - 4.0D && mouseX < 
      parentX() + this.offsetMinX + 4.0D);
  }
  
  public boolean isHoveredMax(double mouseX, double mouseY) {
    return (isHovered(mouseX, mouseY) && mouseX > 
      parentX() + this.offsetMaxX - 4.0D && mouseX < 
      parentX() + this.offsetMaxX + 4.0D);
  }
  
  public double getLength() {
    return this.lerpedOffsetMaxX - this.lerpedOffsetMinX;
  }
  
  public boolean isMouseInMin(double mouseX, double mouseY) {
    return (isHovered(mouseX, mouseY) && (mouseX <= 
      parentX() + this.offsetMinX || mouseX < 
      parentX() + this.offsetMinX + getLength() / 2.0D));
  }
  
  public boolean isMouseInMax(double mouseX, double mouseY) {
    return (isHovered(mouseX, mouseY) && (mouseX > 
      parentX() + this.offsetMaxX || mouseX > 
      parentX() + this.offsetMinX + getLength() / 2.0D));
  }
  
  public void mouseReleased(double mouseX, double mouseY, int button) {
    if (button == 0) {
      if (this.draggingMin)
        this.draggingMin = false; 
      if (this.draggingMax)
        this.draggingMax = false; 
    } 
    super.mouseReleased(mouseX, mouseY, button);
  }
  
  public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    if (this.draggingMin)
      slideMin(mouseX); 
    if (this.draggingMax && !this.draggingMin)
      slideMax(mouseX); 
    super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
  }
  
  public void onGuiClose() {
    this.currentColor1 = null;
    this.currentColor2 = null;
    super.onGuiClose();
  }
  
  private void slideMin(double mouseX) {
    double a = mouseX - parentX();
    double b = class_3532.method_15350(a / parentWidth(), 0.0D, 1.0D);
    this.setting.setMinValue(MathUtils.roundToDecimal(b * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), this.setting.getIncrement()));
  }
  
  private void slideMax(double mouseX) {
    double a = mouseX - parentX();
    double b = class_3532.method_15350(a / parentWidth(), 0.0D, 1.0D);
    this.setting.setMaxValue(MathUtils.roundToDecimal(b * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(), this.setting.getIncrement()));
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
    if (this.draggingMin)
      this.draggingMax = false; 
    if (this.setting.getMinValue() > this.setting.getMaxValue())
      this.setting.setMaxValue(this.setting.getMinValue()); 
    if (this.setting.getMaxValue() < this.setting.getMinValue())
      this.setting.setMinValue(this.setting.getMaxValue() - this.setting.getIncrement()); 
    super.onUpdate();
  }
}
