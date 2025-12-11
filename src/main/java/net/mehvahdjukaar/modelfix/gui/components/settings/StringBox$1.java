package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.lwjgl.glfw.GLFW;

class null extends class_437 {
  private String content;
  
  null(class_2561 title) {
    super(title);
    this.content = StringBox.this.setting.getValue();
  }
  
  public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
    RenderUtils.unscaledProjection();
    mouseX *= (int)class_310.method_1551().method_22683().method_4495();
    mouseY *= (int)class_310.method_1551().method_22683().method_4495();
    super.method_25394(context, mouseX, mouseY, delta);
    context.method_25294(0, 0, StringBox.this.mc.method_22683().method_4480(), StringBox.this.mc.method_22683().method_4507(), (new Color(0, 0, 0, ClickGUI.background.getValue() ? 200 : 0)).getRGB());
    int screenMidX = StringBox.this.mc.method_22683().method_4480() / 2;
    int screenMidY = StringBox.this.mc.method_22683().method_4507() / 2;
    int contentWidth = Math.max(TextRenderer.getWidth(this.content), 600);
    int width = contentWidth + 30;
    int startX = screenMidX - width / 2;
    int startY = screenMidY - 30;
    RenderUtils.renderRoundedQuad(context.method_51448(), new Color(0, 0, 0, ClickGUI.alphaWindow.getValueInt()), startX, startY, (startX + width), (screenMidY + 30), 5.0D, 5.0D, 0.0D, 0.0D, 20.0D);
    TextRenderer.drawCenteredString(StringBox.this.setting.getName(), context, screenMidX, startY + 10, (new Color(245, 245, 245, 255)).getRGB());
    context.method_25294(startX, screenMidY, startX + width, screenMidY + 30, (new Color(0, 0, 0, 120)).getRGB());
    RenderUtils.renderRoundedOutline(context, new Color(50, 50, 50, 255), (startX + 10), (screenMidY + 5), (startX + width - 10), (screenMidY + 25), 5.0D, 5.0D, 5.0D, 5.0D, 2.0D, 20.0D);
    TextRenderer.drawString(this.content, context, startX + 15, screenMidY + 8, (new Color(245, 245, 245, 255)).getRGB());
    context.method_25294(startX, screenMidY, startX + width, screenMidY + 1, Utils.getMainColor(255, 1).getRGB());
    RenderUtils.scaledProjection();
  }
  
  public boolean method_25404(int keyCode, int scanCode, int modifiers) {
    if (keyCode == 256) {
      StringBox.this.setting.setValue(this.content.strip());
      StringBox.this.mc.method_1507((class_437)saki.INSTANCE.clickGui);
    } 
    if (method_25437(keyCode))
      this.content += this.content; 
    if (method_25438(keyCode))
      GLFW.glfwSetClipboardString(StringBox.this.mc.method_22683().method_4490(), this.content); 
    if (keyCode == 259 && 
      !this.content.isEmpty())
      this.content = this.content.substring(0, this.content.length() - 1); 
    return super.method_25404(keyCode, scanCode, modifiers);
  }
  
  public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {}
  
  public boolean method_25400(char chr, int modifiers) {
    this.content += this.content;
    return super.method_25400(chr, modifiers);
  }
  
  public boolean method_25422() {
    return false;
  }
}
