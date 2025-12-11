package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.setting.ModeSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.minecraft.class_332;

public final class ModeBox extends RenderableSetting {
  public final ModeSetting<?> setting;
  
  private Color currentAlpha;
  
  public ModeBox(ModuleButton parent, Setting<?> setting, int offset) {
    super(parent, setting, offset);
    this.setting = (ModeSetting)setting;
  }
  
  public void render(class_332 context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
    int nameOffset = parentX() + 6;
    TextRenderer.drawString(String.valueOf(this.setting.getName()) + ": ", context, nameOffset, parentY() + parentOffset() + this.offset + 9, (new Color(245, 245, 245, 255)).getRGB());
    nameOffset += TextRenderer.getWidth(String.valueOf(this.setting.getName()) + ": ");
    int modeOffset = nameOffset;
    TextRenderer.drawString(this.setting.getMode().name(), context, modeOffset, parentY() + parentOffset() + this.offset + 9, (new Color(245, 245, 245, 255)).getRGB());
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
  
  public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.mouseOver && this.parent.extended && 
      keyCode == 259)
      this.setting.setModeIndex(this.setting.getOriginalValue()); 
    super.keyPressed(keyCode, scanCode, modifiers);
  }
  
  public void mouseClicked(double mouseX, double mouseY, int button) {
    if (isHovered(mouseX, mouseY) && button == 0)
      this.setting.cycle(); 
    super.mouseClicked(mouseX, mouseY, button);
  }
}
