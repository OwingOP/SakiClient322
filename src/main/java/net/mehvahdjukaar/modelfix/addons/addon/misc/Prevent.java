package net.mehvahdjukaar.modelfix.addons.addon.misc;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.BlockBreakingListener;
import net.mehvahdjukaar.modelfix.event.events.ItemUseListener;
import net.mehvahdjukaar.modelfix.utils.BlockUtils;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import net.minecraft.class_4587;

public final class Prevent extends Module implements ItemUseListener, AttackListener, BlockBreakingListener {
  private final BooleanSetting doubleGlowstone = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Double Glowstone"), false))
    .setDescription((CharSequence)EncryptedString.of("Makes it so you can't charge the anchor again if it's already charged"));
  
  private final BooleanSetting glowstoneMisplace = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Glowstone Misplace"), false))
    .setDescription((CharSequence)EncryptedString.of("Makes it so you can only right-click with glowstone only when aiming at an anchor"));
  
  private final BooleanSetting anchorOnAnchor = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Anchor on Anchor"), false))
    .setDescription((CharSequence)EncryptedString.of("Makes it so you can't place an anchor on/next to another anchor unless charged"));
  
  private final BooleanSetting obiPunch = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Obi Punch"), false))
    .setDescription((CharSequence)EncryptedString.of("Makes it so you can crystal faster by not letting you left click/start breaking the obsidian"));
  
  private final BooleanSetting echestClick = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("E-chest click"), false))
    .setDescription((CharSequence)EncryptedString.of("Makes it so you can't click on e-chests with PvP items"));
  
  public Prevent() {
    super((CharSequence)EncryptedString.of("Prevent"), -1, Category.MISC);
    addSettings(new Setting[] { (Setting)this.doubleGlowstone, (Setting)this.glowstoneMisplace, (Setting)this.anchorOnAnchor, (Setting)this.obiPunch, (Setting)this.echestClick });
  }
  
  public void onEnable() {
    this.eventManager.add(BlockBreakingListener.class, (Listener)this);
    this.eventManager.add(AttackListener.class, (Listener)this);
    this.eventManager.add(ItemUseListener.class, (Listener)this);
    super.onEnable();
  }
  
  public void onDisable() {
    this.eventManager.remove(BlockBreakingListener.class, (Listener)this);
    this.eventManager.remove(AttackListener.class, (Listener)this);
    this.eventManager.remove(ItemUseListener.class, (Listener)this);
    super.onDisable();
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
  
  public void onAttack(AttackListener.AttackEvent event) {
    class_239 class_239 = this.mc.field_1765;
    if (class_239 instanceof class_3965) {
      class_3965 hit = (class_3965)class_239;
      if (BlockUtils.isBlock(hit.method_17777(), class_2246.field_10540) && this.obiPunch.getValue() && this.mc.field_1724.method_24518(class_1802.field_8301))
        event.cancel(); 
    } 
  }
  
  public void onBlockBreaking(BlockBreakingListener.BlockBreakingEvent event) {
    class_239 class_239 = this.mc.field_1765;
    if (class_239 instanceof class_3965) {
      class_3965 hit = (class_3965)class_239;
      if (BlockUtils.isBlock(hit.method_17777(), class_2246.field_10540) && this.obiPunch.getValue() && this.mc.field_1724.method_24518(class_1802.field_8301))
        event.cancel(); 
    } 
  }
  
  public void onItemUse(ItemUseListener.ItemUseEvent event) {
    class_239 class_239 = this.mc.field_1765;
    if (class_239 instanceof class_3965) {
      class_3965 hit = (class_3965)class_239;
      if (BlockUtils.isAnchorCharged(hit.method_17777()) && this.doubleGlowstone.getValue() && this.mc.field_1724.method_24518(class_1802.field_8801))
        event.cancel(); 
      if (!BlockUtils.isBlock(hit.method_17777(), class_2246.field_23152) && this.glowstoneMisplace.getValue() && this.mc.field_1724.method_24518(class_1802.field_8801))
        event.cancel(); 
      if (BlockUtils.isAnchorNotCharged(hit.method_17777()) && this.anchorOnAnchor.getValue() && this.mc.field_1724.method_24518(class_1802.field_23141))
        event.cancel(); 
      if (BlockUtils.isBlock(hit.method_17777(), class_2246.field_10443) && this.echestClick.getValue() && (this.mc.field_1724
        .method_6047().method_7909() instanceof net.minecraft.class_1829 || this.mc.field_1724
        .method_6047().method_7909() == class_1802.field_8301 || this.mc.field_1724
        .method_6047().method_7909() == class_1802.field_8281 || this.mc.field_1724
        .method_6047().method_7909() == class_1802.field_23141 || this.mc.field_1724
        .method_6047().method_7909() == class_1802.field_8801))
        event.cancel(); 
    } 
  }
}
