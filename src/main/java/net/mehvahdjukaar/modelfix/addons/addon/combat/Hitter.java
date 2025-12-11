package net.mehvahdjukaar.modelfix.addons.addon.combat;

import java.util.Base64;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.TickListener;
import net.mehvahdjukaar.modelfix.managers.FriendManager;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.MouseSimulation;
import net.mehvahdjukaar.modelfix.utils.TimerUtils;
import net.mehvahdjukaar.modelfix.utils.WorldUtils;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_3966;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;

public final class Hitter extends Module implements TickListener, AttackListener {
  private final BooleanSetting AttackEnemy = new BooleanSetting((CharSequence)EncryptedString.of("Attack Players"), true);
  
  private final BooleanSetting AttackMonster = new BooleanSetting((CharSequence)EncryptedString.of("Attack Mobs"), true);
  
  private final BooleanSetting AttackAlly = new BooleanSetting((CharSequence)EncryptedString.of("Attack Ally"), true);
  
  private final NumberSetting AttackSpeed = new NumberSetting((CharSequence)EncryptedString.of("Attack Speed"), 50.0D, 1000.0D, 500.0D, 5.0D);
  
  private final BooleanSetting AttackSimulation = new BooleanSetting((CharSequence)EncryptedString.of("Mouse Simulation"), true);
  
  private final NumberSetting HotbarSlot = new NumberSetting((CharSequence)EncryptedString.of("Hotbar Slot"), 1.0D, 9.0D, 1.0D, 1.0D);
  
  private final TimerUtils tempo = new TimerUtils();
  
  private long lastGroundedTime;
  
  private boolean onGroundLastTick;
  
  private FriendManager friendManager;
  
  public Hitter() {
    super(new String(Base64.getDecoder().decode("VHJpZ2dlciBCb3Q=")), 0, Category.COMBAT);
    addSettings(new Setting[] { (Setting)this.AttackEnemy });
    addSettings(new Setting[] { (Setting)this.AttackMonster });
    addSettings(new Setting[] { (Setting)this.AttackAlly });
    addSettings(new Setting[] { (Setting)this.AttackSimulation });
    addSettings(new Setting[] { (Setting)this.AttackSpeed });
    addSettings(new Setting[] { (Setting)this.HotbarSlot });
  }
  
  public void onEnable() {
    this.eventManager.add(TickListener.class, (Listener)this);
    this.eventManager.add(AttackListener.class, this);
    this.friendManager = saki.INSTANCE.getFriendManager();
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(TickListener.class, (Listener)this);
    this.eventManager.remove(AttackListener.class, this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onTick() {
    if (this.mc.field_1687 == null || this.mc.field_1724 == null || this.mc.field_1755 != null)
      return; 
    boolean isOnGround = this.mc.field_1724.method_24828();
    if (isOnGround && !this.onGroundLastTick)
      this.lastGroundedTime = System.currentTimeMillis(); 
    this.onGroundLastTick = isOnGround;
    class_239 hitResult = this.mc.field_1765;
    if (hitResult instanceof class_3966) {
      class_1297 target = ((class_3966)hitResult).method_17782();
      if (!isValidTarget(target))
        return; 
      int selectedSlotIndex = (int)this.HotbarSlot.getValue() - 1;
      if ((this.mc.field_1724.method_31548()).field_7545 != selectedSlotIndex)
        return; 
      if (this.tempo.hasReached(this.AttackSpeed.getValue())) {
        WorldUtils.hitEntity(target, true);
        this.tempo.reset();
        if (this.AttackSimulation.getValue())
          MouseSimulation.mouseClick(0); 
      } 
    } 
  }
  
  private boolean isValidTarget(class_1297 entity) {
    if (entity == null || !entity.method_5805())
      return false; 
    if (entity instanceof class_1657) {
      if (!this.AttackEnemy.getValue())
        return false; 
      if (this.AttackAlly.getValue() && this.friendManager != null && this.friendManager.isFriend((class_1657)entity))
        return false; 
      return true;
    } 
    return this.AttackMonster.getValue();
  }
  
  public void onAttack(AttackListener.AttackEvent event) {
    if (GLFW.glfwGetMouseButton(this.mc.method_22683().method_4490(), 0) != 1)
      event.cancel(); 
  }
}
