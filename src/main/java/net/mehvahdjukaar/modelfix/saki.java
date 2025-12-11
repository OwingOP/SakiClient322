package net.mehvahdjukaar.modelfix;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import net.mehvahdjukaar.modelfix.addons.ModuleManager;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.gui.ClickGui;
import net.mehvahdjukaar.modelfix.managers.FriendManager;
import net.mehvahdjukaar.modelfix.managers.ProfileManager;
import net.mehvahdjukaar.modelfix.utils.rotation.RotatorManager;
import net.minecraft.class_310;
import net.minecraft.class_437;

public final class saki {
  public RotatorManager rotatorManager;
  
  public ProfileManager profileManager;
  
  public ModuleManager moduleManager;
  
  public EventManager eventManager;
  
  public FriendManager friendManager;
  
  public static class_310 mc;
  
  public String version = "1.21-1.6-fabric";
  
  public static boolean BETA;
  
  public static saki INSTANCE;
  
  public boolean guiInitialized;
  
  public ClickGui clickGui;
  
  public class_437 previousScreen = null;
  
  public long lastModified;
  
  public File sakiJar;
  
  public saki() throws InterruptedException, IOException {
    INSTANCE = this;
    this.eventManager = new EventManager();
    this.moduleManager = new ModuleManager();
    this.clickGui = new ClickGui();
    this.rotatorManager = new RotatorManager();
    this.profileManager = new ProfileManager();
    this.friendManager = new FriendManager();
    getProfileManager().loadProfile();
    setLastModified();
    this.guiInitialized = false;
    mc = class_310.method_1551();
  }
  
  public ProfileManager getProfileManager() {
    return this.profileManager;
  }
  
  public ModuleManager getModuleManager() {
    return this.moduleManager;
  }
  
  public FriendManager getFriendManager() {
    return this.friendManager;
  }
  
  public EventManager getEventManager() {
    return this.eventManager;
  }
  
  public ClickGui getClickGui() {
    return this.clickGui;
  }
  
  public void resetModifiedDate() {
    this.sakiJar.setLastModified(this.lastModified);
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public void setLastModified() {
    try {
      this.sakiJar = new File(saki.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      this.lastModified = this.sakiJar.lastModified();
    } catch (URISyntaxException uRISyntaxException) {}
  }
}
