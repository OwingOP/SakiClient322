package net.mehvahdjukaar.modelfix.addons.addon.client;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.KeybindSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.ButtonListener;
import net.mehvahdjukaar.modelfix.event.events.HudListener;
import net.mehvahdjukaar.modelfix.managers.FriendManager;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.WorldUtils;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_239;
import net.minecraft.class_332;
import net.minecraft.class_3966;
import net.minecraft.class_4587;

public final class Friends extends Module implements ButtonListener, AttackListener, HudListener {
  private final KeybindSetting addFriendKey = (KeybindSetting)(new KeybindSetting((CharSequence)EncryptedString.of("Friend Key"), 2, false))
    .setDescription((CharSequence)EncryptedString.of("Key to add/remove friends"));
  
  public final BooleanSetting antiAttack = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Anti-Attack"), false))
    .setDescription((CharSequence)EncryptedString.of("Doesn't let you hit friends"));
  
  public final BooleanSetting disableAimAssist = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Anti-Aim"), false))
    .setDescription((CharSequence)EncryptedString.of("Disables aim assist for friends"));
  
  public final BooleanSetting friendStatus = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Friend Status"), false))
    .setDescription((CharSequence)EncryptedString.of("Tells you if you're aiming at a friend or not"));
  
  private FriendManager manager;
  
  public Friends() {
    super((CharSequence)EncryptedString.of("Friends"), -1, Category.CLIENT);
    addSettings(new Setting[] { (Setting)this.addFriendKey, (Setting)this.antiAttack, (Setting)this.disableAimAssist, (Setting)this.friendStatus });
    setKey(-1);
  }
  
  public void onEnable() {
    this.manager = saki.INSTANCE.getFriendManager();
    this.eventManager.add(ButtonListener.class, (Listener)this);
    this.eventManager.add(AttackListener.class, (Listener)this);
    this.eventManager.add(HudListener.class, (Listener)this);
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(ButtonListener.class, (Listener)this);
    this.eventManager.remove(AttackListener.class, (Listener)this);
    this.eventManager.remove(HudListener.class, (Listener)this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onButtonPress(ButtonListener.ButtonEvent event) {
    if (this.mc.field_1724 == null)
      return; 
    if (this.mc.field_1755 != null)
      return; 
    class_239 class_239 = this.mc.field_1765;
    if (class_239 instanceof class_3966) {
      class_3966 hitResult = (class_3966)class_239;
      class_1297 entity = hitResult.method_17782();
      if (entity instanceof class_1657) {
        class_1657 player = (class_1657)entity;
        if (event.button == this.addFriendKey.getKey() && event.action == 1)
          if (!this.manager.isFriend(player)) {
            this.manager.addFriend(player);
          } else {
            this.manager.removeFriend(player);
          }  
      } 
    } 
  }
  
  public void onAttack(AttackListener.AttackEvent event) {
    if (!this.antiAttack.getValue())
      return; 
    if (this.manager.isAimingOverFriend())
      event.cancel(); 
  }
  
  public void onRenderHud(HudListener.HudEvent event) {
    if (!this.friendStatus.getValue())
      return; 
    RenderUtils.unscaledProjection();
    class_239 class_239 = WorldUtils.getHitResult(100.0D);
    if (class_239 instanceof class_3966) {
      class_3966 hitResult = (class_3966)class_239;
      class_1297 entity = hitResult.method_17782();
      class_332 context = event.context;
      if (entity instanceof class_1657) {
        class_1657 player = (class_1657)entity;
        if (this.manager.isFriend(player))
          TextRenderer.drawCenteredString((CharSequence)EncryptedString.of("Player is friend"), context, this.mc.method_22683().method_4480() / 2, this.mc.method_22683().method_4507() / 2 + 25, Color.GREEN.getRGB()); 
      } 
    } 
    RenderUtils.scaledProjection();
  }
}
