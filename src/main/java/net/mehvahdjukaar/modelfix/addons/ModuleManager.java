package net.mehvahdjukaar.modelfix.addons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.addons.addon.client.Friends;
import net.mehvahdjukaar.modelfix.addons.addon.client.SelfReplace;
import net.mehvahdjukaar.modelfix.addons.addon.combat.Hitter;
import net.mehvahdjukaar.modelfix.addons.addon.combat.Tracker;
import net.mehvahdjukaar.modelfix.addons.addon.misc.FakeLag;
import net.mehvahdjukaar.modelfix.addons.addon.misc.Freecam;
import net.mehvahdjukaar.modelfix.addons.addon.misc.NoBreakDelay;
import net.mehvahdjukaar.modelfix.addons.addon.misc.PackSpoof;
import net.mehvahdjukaar.modelfix.addons.addon.misc.Prevent;
import net.mehvahdjukaar.modelfix.addons.addon.render.HUD;
import net.mehvahdjukaar.modelfix.addons.addon.render.NoBounce;
import net.mehvahdjukaar.modelfix.addons.addon.render.TargetHud;
import net.mehvahdjukaar.modelfix.addons.setting.KeybindSetting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.ButtonListener;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;

public final class ModuleManager implements ButtonListener {
  private final List<Module> modules = new ArrayList<>();
  
  public ModuleManager() {
    addModules();
    addKeybinds();
  }
  
  public void addModules() {
    add((Module)new Hitter());
    add((Module)new Tracker());
    add((Module)new Prevent());
    add((Module)new PingSpoof());
    add((Module)new FakeLag());
    add((Module)new NoBreakDelay());
    add((Module)new Freecam());
    add((Module)new PackSpoof());
    add((Module)new HUD());
    add((Module)new NoBounce());
    add((Module)new TargetHud());
    add((Module)new ClickGUI());
    add((Module)new Friends());
    add((Module)new SelfReplace());
  }
  
  public List<Module> getEnabledModules() {
    return this.modules.stream()
      .filter(Module::isEnabled)
      .toList();
  }
  
  public List<Module> getModules() {
    return this.modules;
  }
  
  public void addKeybinds() {
      saki.INSTANCE.getEventManager().add(ButtonListener.class, (Listener)this);
    for (Module module : this.modules)
      module.addSetting((new KeybindSetting((CharSequence)EncryptedString.of("Keybind"), module.getKey(), true)).setDescription((CharSequence)EncryptedString.of("Key to enabled the module"))); 
  }
  
  public List<Module> getModulesInCategory(Category category) {
    return this.modules.stream()
      .filter(module -> (module.getCategory() == category))
      .toList();
  }
  
  public <T extends Module> T getModule(Class<T> moduleClass) {
    Objects.requireNonNull(moduleClass);
    return (T)this.modules.stream().filter(moduleClass::isInstance)
      .findFirst()
      .orElse(null);
  }
  
  public void add(Module module) {
    this.modules.add(module);
  }
  
  public void onButtonPress(ButtonListener.ButtonEvent event) {
    if (!SelfReplace.destruct)
      this.modules.forEach(module -> {
            if (module.getKey() == event.button && event.action == 1)
              module.toggle(); 
          }); 
  }
}
