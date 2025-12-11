package net.mehvahdjukaar.modelfix.addons.addon.misc;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.minecraft.class_2596;
import net.minecraft.class_2856;
import net.minecraft.class_4587;

public class PackSpoof extends Module implements PacketReceiveListener {
  public PackSpoof() {
    super((CharSequence)EncryptedString.of("Pack Spoof"), -1, Category.MISC);
  }
  
  public void onEnable() {
    this.eventManager.add(PacketReceiveListener.class, (Listener)this);
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(PacketReceiveListener.class, (Listener)this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onPacketReceive(PacketReceiveListener.PacketReceiveEvent event) {
    if (this.mc.method_1562() != null) {
      class_2596<?> packet = event.packet;
      if (packet instanceof net.minecraft.class_2720) {
        event.cancel();
        this.mc.method_1562().method_52787((class_2596)new class_2856(this.mc.field_1724.method_5667(), class_2856.class_2857.field_13016));
        this.mc.method_1562().method_52787((class_2596)new class_2856(this.mc.field_1724.method_5667(), class_2856.class_2857.field_13017));
      } 
    } 
  }
}
