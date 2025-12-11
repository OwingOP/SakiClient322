package net.mehvahdjukaar.modelfix.addons.addon.misc;

import com.google.common.collect.Queues;
import java.util.Queue;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.event.events.PacketSendListener;
import net.mehvahdjukaar.modelfix.event.events.PlayerTickListener;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.TimerUtils;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_4587;

public final class FakeLag extends Module implements PlayerTickListener, PacketReceiveListener, PacketSendListener {
  public final Queue<class_2596<?>> packetQueue = Queues.newConcurrentLinkedQueue();
  
  public boolean bool;
  
  public class_243 pos = class_243.field_1353;
  
  public TimerUtils timerUtil = new TimerUtils();
  
  private final MinMaxSetting lagDelay = new MinMaxSetting((CharSequence)EncryptedString.of("Lag Delay"), 0.0D, 1000.0D, 1.0D, 100.0D, 200.0D);
  
  private final BooleanSetting cancelOnElytra = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Cancel on Elytra"), false))
    .setDescription((CharSequence)EncryptedString.of("Cancel the lagging effect when you're wearing an elytra"));
  
  private int delay;
  
  public FakeLag() {
    super((CharSequence)EncryptedString.of("Fake Lag"), -1, Category.MISC);
    addSettings(new Setting[] { (Setting)this.lagDelay, (Setting)this.cancelOnElytra });
  }
  
  public void onEnable() {
    this.eventManager.add(PlayerTickListener.class, (Listener)this);
    this.eventManager.add(PacketSendListener.class, (Listener)this);
    this.eventManager.add(PacketReceiveListener.class, (Listener)this);
    this.timerUtil.reset();
    if (this.mc.field_1724 != null)
      this.pos = this.mc.field_1724.method_19538(); 
    this.delay = this.lagDelay.getRandomValueInt();
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(PlayerTickListener.class, (Listener)this);
    this.eventManager.remove(PacketSendListener.class, (Listener)this);
    this.eventManager.remove(PacketReceiveListener.class, (Listener)this);
    reset();
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onPacketReceive(PacketReceiveListener.PacketReceiveEvent event) {
    if (this.mc.field_1687 == null)
      return; 
    if (this.mc.field_1724.method_29504())
      return; 
    if (event.packet instanceof net.minecraft.class_2664)
      reset(); 
  }
  
  public void onPacketSend(PacketSendListener.PacketSendEvent event) {
    if (this.mc.field_1687 == null || this.mc.field_1724.method_6115() || this.mc.field_1724.method_29504())
      return; 
    if (event.packet instanceof net.minecraft.class_2824 || event.packet instanceof net.minecraft.class_2879 || event.packet instanceof net.minecraft.class_2885 || event.packet instanceof net.minecraft.class_2813) {
      reset();
      return;
    } 
    if (this.cancelOnElytra.getValue() && this.mc.field_1724.method_31548().method_7372(2).method_7909() == class_1802.field_8833) {
      reset();
      return;
    } 
    if (!this.bool) {
      this.packetQueue.add(event.packet);
      event.cancel();
    } 
  }
  
  public void onPlayerTick() {
    if (this.timerUtil.delay(this.delay) && 
      this.mc.field_1724 != null && !this.mc.field_1724.method_6115()) {
      reset();
      this.delay = this.lagDelay.getRandomValueInt();
    } 
  }
  
  private void reset() {
    if (this.mc.field_1724 == null || this.mc.field_1687 == null)
      return; 
    this.bool = true;
    synchronized (this.packetQueue) {
      while (!this.packetQueue.isEmpty())
        this.mc.method_1562().method_48296().method_52906(this.packetQueue.poll(), null, false); 
    } 
    this.bool = false;
    this.timerUtil.reset();
    this.pos = this.mc.field_1724.method_19538();
  }
}
