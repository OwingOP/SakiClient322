package net.mehvahdjukaar.modelfix.addons.addon.render;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.HudListener;
import net.mehvahdjukaar.modelfix.event.events.PacketSendListener;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2824;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_640;
import net.minecraft.class_7532;

public final class TargetHud extends Module implements HudListener, PacketSendListener {
  private final NumberSetting xCoord = new NumberSetting("X", 0.0D, 1920.0D, 500.0D, 1.0D);
  
  private final NumberSetting yCoord = new NumberSetting("Y", 0.0D, 1080.0D, 500.0D, 1.0D);
  
  private final BooleanSetting hudTimeout = new BooleanSetting("Timeout", true);
  
  private long lastAttackTime = 0L;
  
  private static final long timeout = 10000L;
  
  public TargetHud() {
    super("Target HUD", -1, Category.RENDER);
    addSettings(new Setting[] { (Setting)this.xCoord, (Setting)this.yCoord, (Setting)this.hudTimeout });
  }
  
  public void onEnable() {
    this.eventManager.add(HudListener.class, (Listener)this);
    this.eventManager.add(PacketSendListener.class, (Listener)this);
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(HudListener.class, (Listener)this);
    this.eventManager.remove(PacketSendListener.class, (Listener)this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onRenderHud(HudListener.HudEvent event) {
    class_332 context = event.context;
    int x = this.xCoord.getValueInt();
    int y = this.yCoord.getValueInt();
    RenderUtils.unscaledProjection();
    if (!this.hudTimeout.getValue() || System.currentTimeMillis() - this.lastAttackTime <= 10000L) {
      class_1309 class_1309 = this.mc.field_1724.method_6052();
      if (class_1309 instanceof class_1657) {
        class_1657 player = (class_1657)class_1309;
        if (player.method_5805()) {
          class_640 entry = this.mc.method_1562().method_2871(player.method_5667());
          context.method_51448().method_22903();
          RenderUtils.renderRoundedQuad(context.method_51448(), new Color(0, 0, 0, 150), x, y, (x + 250), (y + 50), 10.0D, 10.0D, 10.0D, 10.0D, 5.0D);
          if (entry != null)
            class_7532.method_44443(context, entry.method_52810().comp_1626(), x + 5, y + 5, 40); 
          String playerName = player.method_5477().getString();
          TextRenderer.drawString(playerName, context, x + 50, y + 10, Color.WHITE.getRGB());
          float health = player.method_6032() + player.method_6067();
          float healthPercentage = Math.min(health / player.method_6063(), 1.0F);
          int barWidth = 140;
          int barHeight = 5;
          int barX = x + 50;
          int barY = y + 30;
          context.method_25294(barX, barY, barX + barWidth, barY + barHeight, (new Color(50, 50, 50, 200)).getRGB());
          context.method_25294(barX, barY, barX + Math.round(barWidth * healthPercentage), barY + barHeight, Color.GREEN.getRGB());
          context.method_51448().method_22909();
        } 
      } 
    } 
    RenderUtils.scaledProjection();
  }
  
  public void onPacketSend(PacketSendListener.PacketSendEvent event) {
    class_2596 class_2596 = event.packet;
    if (class_2596 instanceof class_2824) {
      class_2824 packet = (class_2824)class_2596;
      packet.method_34209(new class_2824.class_5908() {
            public void method_34219(class_1268 hand) {}
            
            public void method_34220(class_1268 hand, class_243 pos) {}
            
            public void method_34218() {
              if (TargetHud.this.mc.field_1692 instanceof class_1657)
                TargetHud.this.lastAttackTime = System.currentTimeMillis(); 
            }
          });
    } 
  }
}
