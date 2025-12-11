package net.mehvahdjukaar.modelfix.addons.addon.render;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.HudListener;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_640;

public final class HUD extends Module implements HudListener {
  private static final CharSequence argon = (CharSequence)EncryptedString.of("saki |");
  
  private final BooleanSetting info = new BooleanSetting((CharSequence)EncryptedString.of("Info"), true);
  
  private final BooleanSetting modules = (BooleanSetting)(new BooleanSetting("Modules", true))
    .setDescription((CharSequence)EncryptedString.of("Renders module array list"));
  
  public HUD() {
    super((CharSequence)EncryptedString.of("HUD"), -1, Category.RENDER);
    addSettings(new Setting[] { (Setting)this.info, (Setting)this.modules });
  }
  
  public void onEnable() {
    this.eventManager.add(HudListener.class, (Listener)this);
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(HudListener.class, (Listener)this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onRenderHud(HudListener.HudEvent event) {
    if (this.mc.field_1755 != saki.INSTANCE.clickGui) {
      List<Module> enabledModules = saki.INSTANCE.getModuleManager().getEnabledModules().stream().sorted((module1, module2) -> {
            CharSequence name1 = module1.getName();
            CharSequence name2 = module2.getName();
            int filteredLength1 = TextRenderer.getWidth(name1);
            int filteredLength2 = TextRenderer.getWidth(name2);
            return Integer.compare(filteredLength2, filteredLength1);
          }).toList();
      class_332 context = event.context;
      boolean customFont = ClickGUI.customFont.getValue();
      if (!(this.mc.field_1755 instanceof net.mehvahdjukaar.modelfix.gui.ClickGui)) {
        if (this.info.getValue() && this.mc.field_1724 != null) {
          RenderUtils.unscaledProjection();
          int argonOffset = 10;
          int argonOffset2 = 10 + TextRenderer.getWidth(argon);
          String ping = "Ping: ";
          String fps = "FPS: " + this.mc.method_47599() + " |";
          String server = (this.mc.method_1558() == null) ? "None" : (this.mc.method_1558()).field_3761;
          if (this.mc != null && this.mc.field_1724 != null && this.mc.method_1562() != null) {
            class_640 entry = this.mc.method_1562().method_2871(this.mc.field_1724.method_5667());
            if (entry != null) {
              ping = ping + ping + " |";
            } else {
              ping = ping + "N/A |";
            } 
          } else {
            ping = ping + "N/A |";
          } 
          RenderUtils.renderRoundedQuad(context.method_51448(), new Color(35, 35, 35, 255), 5.0D, 6.0D, (argonOffset2 + TextRenderer.getWidth(fps) + TextRenderer.getWidth(ping) + TextRenderer.getWidth(server) + 35), 30.0D, 5.0D, 15.0D);
          TextRenderer.drawString(argon, context, argonOffset, 12, Utils.getMainColor(255, 4).getRGB());
          argonOffset += TextRenderer.getWidth(argon);
          TextRenderer.drawString(fps, context, argonOffset + 10, 12, Utils.getMainColor(255, 3).getRGB());
          TextRenderer.drawString(ping, context, argonOffset + 10 + TextRenderer.getWidth(fps) + 10, 12, Utils.getMainColor(255, 2).getRGB());
          TextRenderer.drawString(server, context, argonOffset + 10 + TextRenderer.getWidth(fps) + TextRenderer.getWidth(ping) + 20, 12, Utils.getMainColor(255, 1).getRGB());
          RenderUtils.scaledProjection();
        } 
        if (this.modules.getValue()) {
          int offset = 55;
          for (Module module : enabledModules) {
            RenderUtils.unscaledProjection();
            int charOffset = 6 + TextRenderer.getWidth(module.getName());
            Objects.requireNonNull(this.mc.field_1772);
            RenderUtils.renderRoundedQuad(context.method_51448(), new Color(0, 0, 0, 175), 0.0D, (offset - 4), (charOffset + 5), (offset + 9 * 2 - 1), 0.0D, 0.0D, 0.0D, 5.0D, 10.0D);
            Objects.requireNonNull(this.mc.field_1772);
            context.method_25296(0, offset - 4, 2, offset + 9 * 2, Utils.getMainColor(255, enabledModules.indexOf(module)).getRGB(), Utils.getMainColor(255, enabledModules.indexOf(module) + 1).getRGB());
            int charOffset2 = customFont ? 5 : 8;
            TextRenderer.drawString(module.getName(), context, charOffset2, offset + (customFont ? 1 : 0), Utils.getMainColor(255, enabledModules.indexOf(module)).getRGB());
            Objects.requireNonNull(this.mc.field_1772);
            offset += 9 * 2 + 3;
            RenderUtils.scaledProjection();
          } 
        } 
      } 
    } 
  }
}
