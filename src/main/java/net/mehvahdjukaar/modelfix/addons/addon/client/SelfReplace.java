package net.mehvahdjukaar.modelfix.addons.addon.client;

import com.sun.jna.Memory;
import java.io.File;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.addons.setting.StringSetting;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.class_4587;

public final class SelfReplace extends Module {
  public static boolean destruct = false;
  
  private final BooleanSetting replaceMod = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Hider"), true))
    .setDescription((CharSequence)EncryptedString.of("Repalces the mod with the original JAR file of the ModelFix mod"));
  
  private final BooleanSetting saveLastModified = (BooleanSetting)(new BooleanSetting((CharSequence)EncryptedString.of("Save Last Modified"), true))
    .setDescription((CharSequence)EncryptedString.of("Saves the last modified date after self destruct"));
  
  private final StringSetting downloadURL = new StringSetting((CharSequence)EncryptedString.of("Replace URL"), "https://cdn.modrinth.com/data/QdG47OkI/versions/FUZo6yCk/modelfix-1.21-1.6-fabric.jar");
  
  public SelfReplace() {
    super((CharSequence)EncryptedString.of("Hider"), -1, Category.CLIENT);
    addSettings(new Setting[] { (Setting)this.replaceMod, (Setting)this.saveLastModified, (Setting)this.downloadURL });
  }
  
  public void onEnable() {
    destruct = true;
    ((ClickGUI)saki.INSTANCE.getModuleManager().getModule(ClickGUI.class)).setEnabled(false);
    setEnabled(false);
      saki.INSTANCE.getProfileManager().saveProfile();
    if (this.mc.field_1755 instanceof net.mehvahdjukaar.modelfix.gui.ClickGui) {
        saki.INSTANCE.guiInitialized = false;
      this.mc.field_1755.method_25419();
    } 
    if (this.replaceMod.getValue())
      try {
        String modUrl = this.downloadURL.getValue();
        File currentJar = Utils.getCurrentJarPath();
        if (currentJar.exists())
          Utils.replaceModFile(modUrl, Utils.getCurrentJarPath()); 
      } catch (Exception exception) {} 
    for (Module module : saki.INSTANCE.getModuleManager().getModules()) {
      module.setEnabled(false);
      module.setName(null);
      module.setDescription(null);
      for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
        setting.setName(null);
        setting.setDescription(null);
        if (setting instanceof StringSetting) {
          StringSetting set = (StringSetting)setting;
          set.setValue(null);
        } 
      } 
      module.getSettings().clear();
    } 
    Runtime runtime = Runtime.getRuntime();
    if (this.saveLastModified.getValue())
        saki.INSTANCE.resetModifiedDate();
    for (int i = 0; i <= 10; i++) {
      runtime.gc();
      runtime.runFinalization();
      try {
        Thread.sleep((100 * i));
        Memory.purge();
        Memory.disposeAll();
      } catch (InterruptedException interruptedException) {}
    } 
  }
  
  public void onRender3D(class_4587 matrixStack, float partialTicks) {}
}
