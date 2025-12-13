package net.mehvahdjukaar.modelfix.addons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.addons.addon.client.Friends;
import net.mehvahdjukaar.modelfix.addons.addon.client.SelfDestruct;
import net.mehvahdjukaar.modelfix.addons.addon.combat.Hitter;
import net.mehvahdjukaar.modelfix.addons.addon.misc.FakeLag;
import net.mehvahdjukaar.modelfix.addons.addon.misc.Freecam;
import net.mehvahdjukaar.modelfix.addons.addon.misc.NoBreakDelay;
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
        add(new Hitter());
        add(new Prevent());
        add(new FakeLag());
        add(new NoBreakDelay());
        add(new Freecam());
        // Removed PackSpoof if you don’t want it
        add(new HUD());
        add(new NoBounce());
        add(new TargetHud());
        add(new ClickGUI());
        add(new Friends());
        add(new SelfDestruct());
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
        saki.INSTANCE.getEventManager().add(ButtonListener.class, (Listener) this);
        for (Module module : this.modules) {
            module.addSetting(
                    new KeybindSetting(EncryptedString.of("Keybind"), module.getKey(), true)
                            .setDescription(EncryptedString.of("Key to enable the module"))
            );
        }
    }

    public List<Module> getModulesInCategory(Category category) {
        return this.modules.stream()
                .filter(module -> module.getCategory() == category)
                .toList();
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        Objects.requireNonNull(moduleClass);
        return (T) this.modules.stream()
                .filter(moduleClass::isInstance)
                .findFirst()
                .orElse(null);
    }

    public void add(Module module) {
        this.modules.add(module);
    }

    @Override
    public void onButtonPress(ButtonListener.ButtonEvent event) {
        if (!SelfDestruct.destruct) {
            this.modules.forEach(module -> {
                if (module.getKey() == event.button && event.action == 1) {
                    module.toggle();
                }
            });
        }
    }
}
