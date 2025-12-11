package net.mehvahdjukaar.modelfix.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.KeybindSetting;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.ModeSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.addons.setting.StringSetting;
import net.mehvahdjukaar.modelfix.gui.Window;
import net.mehvahdjukaar.modelfix.gui.components.settings.CheckBox;
import net.mehvahdjukaar.modelfix.gui.components.settings.KeybindBox;
import net.mehvahdjukaar.modelfix.gui.components.settings.MinMaxSlider;
import net.mehvahdjukaar.modelfix.gui.components.settings.ModeBox;
import net.mehvahdjukaar.modelfix.gui.components.settings.RenderableSetting;
import net.mehvahdjukaar.modelfix.gui.components.settings.Slider;
import net.mehvahdjukaar.modelfix.gui.components.settings.StringBox;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.AnimationUtils;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.class_310;
import net.minecraft.class_332;

public final class ModuleButton {
  public List<RenderableSetting> settings = new ArrayList<>();
  
  public Window parent;
  
  public Module module;
  
  public int offset;
  
  public boolean extended;
  
  public int settingOffset;
  
  public Color currentColor;
  
  public Color defaultColor = Color.WHITE;
  
  public Color currentAlpha;
  
  public AnimationUtils animation = new AnimationUtils(0.0D);
  
  public ModuleButton(Window parent, Module module, int offset) {
    this.parent = parent;
    this.module = module;
    this.offset = offset;
    this.extended = false;
    this.settingOffset = parent.getHeight();
    for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
      if (setting instanceof BooleanSetting) {
        BooleanSetting booleanSetting = (BooleanSetting)setting;
        this.settings.add(new CheckBox(this, (Setting)booleanSetting, this.settingOffset));
      } else if (setting instanceof NumberSetting) {
        NumberSetting numberSetting = (NumberSetting)setting;
        this.settings.add(new Slider(this, (Setting)numberSetting, this.settingOffset));
      } else if (setting instanceof ModeSetting) {
        ModeSetting<?> modeSetting = (ModeSetting)setting;
        this.settings.add(new ModeBox(this, (Setting)modeSetting, this.settingOffset));
      } else if (setting instanceof KeybindSetting) {
        KeybindSetting keybindSetting = (KeybindSetting)setting;
        this.settings.add(new KeybindBox(this, (Setting)keybindSetting, this.settingOffset));
      } else if (setting instanceof StringSetting) {
        StringSetting stringSetting = (StringSetting)setting;
        this.settings.add(new StringBox(this, (Setting)stringSetting, this.settingOffset));
      } else if (setting instanceof MinMaxSetting) {
        MinMaxSetting minMaxSetting = (MinMaxSetting)setting;
        this.settings.add(new MinMaxSlider(this, (Setting)minMaxSetting, this.settingOffset));
      } 
      this.settingOffset += parent.getHeight();
    } 
  }
  
  public void render(class_332 context, int mouseX, int mouseY, float delta) {
    if (this.parent.getY() + this.offset > class_310.method_1551().method_22683().method_4507())
      return; 
    for (RenderableSetting renderableSetting : this.settings)
      renderableSetting.onUpdate(); 
    if (this.currentColor == null) {
      this.currentColor = new Color(0, 0, 0, 0);
    } else {
      this.currentColor = new Color(0, 0, 0, this.currentColor.getAlpha());
    } 
    int toAlpha = 170;
    this.currentColor = ColorUtils.smoothAlphaTransition(0.05F, toAlpha, this.currentColor);
    Color toColor = this.module.isEnabled() ? Utils.getMainColor(255, saki.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module)) : Color.WHITE;
    if (this.defaultColor != toColor)
      this.defaultColor = ColorUtils.smoothColorTransition(0.1F, toColor, this.defaultColor); 
    if (this.parent.moduleButtons.get(this.parent.moduleButtons.size() - 1) != this) {
      context.method_25294(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.parent.getHeight() + this.offset, this.currentColor.getRGB());
      context.method_25296(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + 2, this.parent.getY() + this.parent.getHeight() + this.offset, Utils.getMainColor(255, saki.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module)).getRGB(), Utils.getMainColor(255, saki.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module) + 1).getRGB());
    } else {
      RenderUtils.renderRoundedQuad(context.method_51448(), this.currentColor, this.parent.getX(), (this.parent.getY() + this.offset), (this.parent.getX() + this.parent.getWidth()), (this.parent.getY() + this.parent.getHeight() + this.offset), 0.0D, 0.0D, 3.0D, (this.animation.getValue() > 30.0D) ? 0.0D : ClickGUI.roundQuads.getValueInt(), 50.0D);
      RenderUtils.renderRoundedQuad(context.method_51448(), Utils.getMainColor(255, saki.INSTANCE.getModuleManager().getModulesInCategory(this.module.getCategory()).indexOf(this.module)), this.parent.getX(), (this.parent.getY() + this.offset), (this.parent.getX() + 2), (this.parent.getY() + this.parent.getHeight() - 1 + this.offset), 0.0D, 0.0D, this.extended ? 0.0D : 2.0D, 0.0D, 50.0D);
    } 
    CharSequence nameChars = this.module.getName();
    int totalWidth = TextRenderer.getWidth(nameChars);
    int parentCenterX = this.parent.getX() + this.parent.getWidth() / 2;
    int textCenterX = parentCenterX - totalWidth / 2;
    TextRenderer.drawString(nameChars, context, textCenterX, this.parent.getY() + this.offset + 8, this.defaultColor.getRGB());
    renderHover(context, mouseX, mouseY, delta);
    renderSettings(context, mouseX, mouseY, delta);
    for (RenderableSetting renderableSetting : this.settings) {
      if (this.extended)
        renderableSetting.renderDescription(context, mouseX, mouseY, delta); 
    } 
    if (isHovered(mouseX, mouseY) && !this.parent.dragging) {
      CharSequence chars = this.module.getDescription();
      int tw = TextRenderer.getWidth(chars);
      int parentCenter = saki.mc.method_22683().method_4489() / 2;
      int textCenter = parentCenter - tw / 2;
      RenderUtils.renderRoundedQuad(context
          .method_51448(), new Color(100, 100, 100, 100), (textCenter - 5), saki.mc
          
          .method_22683().method_4506() / 2.0D + 294.0D, (textCenter + tw + 5), saki.mc
          
          .method_22683().method_4506() / 2.0D + 318.0D, 3.0D, 10.0D);
      TextRenderer.drawString(chars, context, textCenter, saki.mc.method_22683().method_4506() / 2 + 300, Color.WHITE.getRGB());
    } 
  }
  
  private void renderHover(class_332 context, int mouseX, int mouseY, float delta) {
    if (!this.parent.dragging) {
      int toHoverAlpha = isHovered(mouseX, mouseY) ? 15 : 0;
      if (this.currentAlpha == null) {
        this.currentAlpha = new Color(255, 255, 255, toHoverAlpha);
      } else {
        this.currentAlpha = new Color(255, 255, 255, this.currentAlpha.getAlpha());
      } 
      if (this.currentAlpha.getAlpha() != toHoverAlpha)
        this.currentAlpha = ColorUtils.smoothAlphaTransition(0.05F, toHoverAlpha, this.currentAlpha); 
      context.method_25294(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + this.parent.getHeight() + this.offset, this.currentAlpha.getRGB());
    } 
  }
  
  private void renderSettings(class_332 context, int mouseX, int mouseY, float delta) {
    int scissorX = this.parent.getX();
    int scissorY = (int)(saki.mc.method_22683().method_4507() - (this.parent.getY() + this.offset) + this.animation.getValue());
    int scissorWidth = this.parent.getWidth();
    int scissorHeight = (int)this.animation.getValue();
    RenderSystem.enableScissor(scissorX, scissorY, scissorWidth, scissorHeight);
    for (RenderableSetting renderableSetting : this.settings) {
      if (this.animation.getValue() > this.parent.getHeight())
        renderableSetting.render(context, mouseX, mouseY, delta); 
    } 
    for (RenderableSetting renderableSetting : this.settings) {
      if (this.animation.getValue() > this.parent.getHeight()) {
        if (renderableSetting instanceof Slider) {
          Slider slider = (Slider)renderableSetting;
          RenderUtils.renderCircle(context.method_51448(), new Color(0, 0, 0, 170), slider.parentX() + Math.max(slider.lerpedOffsetX, 2.5D), (slider.parentY() + slider.offset + slider.parentOffset()) + 27.5D, 6.0D, 15);
          RenderUtils.renderCircle(context.method_51448(), slider.currentColor1.brighter(), slider.parentX() + Math.max(slider.lerpedOffsetX, 2.5D), (slider.parentY() + slider.offset + slider.parentOffset()) + 27.5D, 5.0D, 15);
          continue;
        } 
        if (renderableSetting instanceof MinMaxSlider) {
          MinMaxSlider slider = (MinMaxSlider)renderableSetting;
          RenderUtils.renderCircle(context.method_51448(), new Color(0, 0, 0, 170), slider.parentX() + Math.max(slider.lerpedOffsetMinX, 2.5D), (slider.parentY() + slider.offset + slider.parentOffset()) + 27.5D, 6.0D, 15);
          RenderUtils.renderCircle(context.method_51448(), slider.currentColor1.brighter(), slider.parentX() + Math.max(slider.lerpedOffsetMinX, 2.5D), (slider.parentY() + slider.offset + slider.parentOffset()) + 27.5D, 5.0D, 15);
          RenderUtils.renderCircle(context.method_51448(), new Color(0, 0, 0, 170), slider.parentX() + Math.max(slider.lerpedOffsetMaxX, 2.5D), (slider.parentY() + slider.offset + slider.parentOffset()) + 27.5D, 6.0D, 15);
          RenderUtils.renderCircle(context.method_51448(), slider.currentColor1.brighter(), slider.parentX() + Math.max(slider.lerpedOffsetMaxX, 2.5D), (slider.parentY() + slider.offset + slider.parentOffset()) + 27.5D, 5.0D, 15);
        } 
      } 
    } 
    RenderSystem.disableScissor();
  }
  
  public void onExtend() {
    for (ModuleButton moduleButton : this.parent.moduleButtons)
      moduleButton.extended = false; 
  }
  
  public void keyPressed(int keyCode, int scanCode, int modifiers) {
    for (RenderableSetting setting : this.settings)
      setting.keyPressed(keyCode, scanCode, modifiers); 
  }
  
  public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    if (this.extended)
      for (RenderableSetting renderableSetting : this.settings)
        renderableSetting.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);  
  }
  
  public void mouseClicked(double mouseX, double mouseY, int button) {
    if (isHovered(mouseX, mouseY)) {
      if (button == 0)
        this.module.toggle(); 
      if (button == 1) {
        if (this.module.getSettings().isEmpty())
          return; 
        if (!this.extended)
          onExtend(); 
        this.extended = !this.extended;
      } 
    } 
    if (this.extended)
      for (RenderableSetting renderableSetting : this.settings)
        renderableSetting.mouseClicked(mouseX, mouseY, button);  
  }
  
  public void onGuiClose() {
    this.currentAlpha = null;
    this.currentColor = null;
    for (RenderableSetting renderableSetting : this.settings)
      renderableSetting.onGuiClose(); 
  }
  
  public void mouseReleased(double mouseX, double mouseY, int button) {
    for (RenderableSetting renderableSetting : this.settings)
      renderableSetting.mouseReleased(mouseX, mouseY, button); 
  }
  
  public boolean isHovered(double mouseX, double mouseY) {
    return (mouseX > this.parent.getX() && mouseX < (this.parent
      .getX() + this.parent.getWidth()) && mouseY > (this.parent
      .getY() + this.offset) && mouseY < (this.parent
      .getY() + this.offset + this.parent.getHeight()));
  }
}
