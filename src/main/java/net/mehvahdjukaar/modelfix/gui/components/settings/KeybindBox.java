package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.setting.KeybindSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.minecraft.class_332;

public final class KeybindBox extends RenderableSetting {
  public KeybindSetting keybind;
  
  private Color currentAlpha;
  
  public KeybindBox(ModuleButton parent, Setting<?> setting, int offset) {
    super(parent, setting, offset);
    this.keybind = (KeybindSetting)setting;
  }
  
  public void mouseClicked(double mouseX, double mouseY, int button) {
    if (isHovered(mouseX, mouseY))
      if (!this.keybind.isListening()) {
        if (button == 0) {
          this.keybind.toggleListening();
          this.keybind.setListening(true);
        } 
      } else {
        if (this.keybind.isModuleKey())
          this.parent.module.setKey(button); 
        this.keybind.setKey(button);
        this.keybind.setListening(false);
      }  
    super.mouseClicked(mouseX, mouseY, button);
  }
  
  public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (keyCode == 259) {
      if (this.mouseOver) {
        if (this.keybind.isModuleKey())
          this.parent.module.setKey(this.keybind.getOriginalKey()); 
        this.keybind.setKey(this.keybind.getOriginalKey());
        this.keybind.setListening(false);
      } 
    } else {
      if (this.keybind.isListening() && keyCode != 256) {
        if (this.keybind.isModuleKey())
          this.parent.module.setKey(keyCode); 
        this.keybind.setKey(keyCode);
        this.keybind.setListening(false);
      } 
      if (this.keybind.getKey() == 256) {
        if (this.keybind.isModuleKey())
          this.parent.module.setKey(this.parent.module.getKey()); 
        this.keybind.setKey(this.keybind.getKey());
        this.keybind.setListening(false);
      } 
    } 
    super.keyPressed(keyCode, scanCode, modifiers);
  }
  
  public void render(class_332 context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
    int off = parentX() + 6;
    if (!this.keybind.isListening()) {
      TextRenderer.drawString(String.valueOf(this.setting.getName()) + ": " + String.valueOf(this.setting.getName()), context, off, parentY() + parentOffset() + this.offset + 9, (new Color(245, 245, 245, 255)).getRGB());
    } else {
      TextRenderer.drawString("Listening...", context, off, parentY() + parentOffset() + this.offset + 6, (new Color(245, 245, 245, 255)).getRGB());
    } 
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
}
