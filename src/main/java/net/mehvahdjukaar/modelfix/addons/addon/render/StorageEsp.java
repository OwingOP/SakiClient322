package net.mehvahdjukaar.modelfix.addons.addon.render;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.GameRenderListener;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.WorldUtils;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2586;
import net.minecraft.class_2818;
import net.minecraft.class_4184;
import net.minecraft.class_4587;

public final class StorageEsp extends Module implements GameRenderListener, PacketReceiveListener {
  private final NumberSetting alpha = new NumberSetting((CharSequence)EncryptedString.of("Alpha"), 1.0D, 255.0D, 125.0D, 1.0D);
  
  private final BooleanSetting donutBypass = new BooleanSetting((CharSequence)EncryptedString.of("Donut Bypass"), false);
  
  private final BooleanSetting tracers = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Tracers"), false))
    .setDescription((CharSequence)EncryptedString.of("Draws a line from your player to the storage block"));
  
  public StorageEsp() {
    super((CharSequence)EncryptedString.of("Storage ESP"), -1, Category.RENDER);
    addSettings(new Setting[] { (Setting)this.donutBypass, (Setting)this.alpha, (Setting)this.tracers });
  }
  
  public void onEnable() {
    this.eventManager.add(PacketReceiveListener.class, (Listener)this);
    this.eventManager.add(GameRenderListener.class, (Listener)this);
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(PacketReceiveListener.class, (Listener)this);
    this.eventManager.remove(GameRenderListener.class, (Listener)this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onGameRender(GameRenderListener.GameRenderEvent event) {
    renderStorages(event);
  }
  
  private Color getColor(class_2586 blockEntity, int a) {
    if (blockEntity instanceof net.minecraft.class_2646)
      return new Color(200, 91, 0, a); 
    if (blockEntity instanceof net.minecraft.class_2595)
      return new Color(156, 91, 0, a); 
    if (blockEntity instanceof net.minecraft.class_2611)
      return new Color(117, 0, 255, a); 
    if (blockEntity instanceof net.minecraft.class_2636)
      return new Color(138, 126, 166, a); 
    if (blockEntity instanceof net.minecraft.class_2627)
      return new Color(134, 0, 158, a); 
    if (blockEntity instanceof net.minecraft.class_3866)
      return new Color(125, 125, 125, a); 
    if (blockEntity instanceof net.minecraft.class_3719)
      return new Color(255, 140, 140, a); 
    if (blockEntity instanceof net.minecraft.class_2605)
      return new Color(80, 80, 255, a); 
    return new Color(255, 255, 255, 0);
  }
  
  private void renderStorages(GameRenderListener.GameRenderEvent event) {
    class_4184 cam = this.mc.field_1773.method_19418();
    if (cam != null) {
      class_4587 matrices = event.matrices;
      matrices.method_22903();
      class_243 vec = cam.method_19326();
      matrices.method_22904(-vec.field_1352, -vec.field_1351, -vec.field_1350);
    } 
    for (class_2818 chunk : WorldUtils.getLoadedChunks().toList()) {
      for (class_2338 blockPos : chunk.method_12021()) {
        class_2586 blockEntity = this.mc.field_1687.method_8321(blockPos);
        RenderUtils.renderFilledBox(event.matrices, blockPos.method_10263() + 0.1F, blockPos.method_10264() + 0.05F, blockPos.method_10260() + 0.1F, blockPos.method_10263() + 0.9F, blockPos.method_10264() + 0.85F, blockPos.method_10260() + 0.9F, getColor(blockEntity, this.alpha.getValueInt()));
        if (this.tracers.getValue())
          RenderUtils.renderLine(event.matrices, getColor(blockEntity, 255), this.mc.field_1765.method_17784(), new class_243(blockPos.method_10263() + 0.5D, blockPos.method_10264() + 0.5D, blockPos.method_10260() + 0.5D)); 
      } 
    } 
    class_4587 matrixStack = event.matrices;
    matrixStack.method_22909();
  }
  
  public void onPacketReceive(PacketReceiveListener.PacketReceiveEvent event) {
    if (this.donutBypass.getValue() && 
      event.packet instanceof net.minecraft.class_2637)
      event.cancel(); 
  }
}
