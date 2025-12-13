package net.mehvahdjukaar.modelfix.addons.addon.client;

import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.gui.ClickGui;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.StringSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.Utils;

import java.io.File;

@SuppressWarnings("all")
public final class SelfDestruct extends Module {
    public static boolean destruct = false;

    private final BooleanSetting replaceMod = new BooleanSetting(EncryptedString.of("Replace Mod"), true)
            .setDescription(EncryptedString.of("Replaces the mod with the original JAR file"));

    private final BooleanSetting saveLastModified = new BooleanSetting(EncryptedString.of("Save Last Modified"), true)
            .setDescription(EncryptedString.of("Saves the last modified date after self destruct"));

    // ✅ Adapted for your modelfix JAR
    private final StringSetting downloadURL = new StringSetting(
            EncryptedString.of("Replace URL"),
            "https://cdn.modrinth.com/data/QdG47OkI/versions/FUZo6yCk/modelfix-1.21-1.6-fabric.jar"
    );

    public SelfDestruct() {
        super(EncryptedString.of("Self Destruct"),
                EncryptedString.of("Removes the client from your game"),
                -1,
                Category.CLIENT);
        addSettings(replaceMod, saveLastModified, downloadURL);
    }

    @Override
    public void onEnable() {
        destruct = true;

        // Disable GUI
        saki.INSTANCE.getModuleManager().getModule(ClickGui.class).setEnabled(false);
        setEnabled(false);

        // Save profile
        saki.INSTANCE.getProfileManager().saveProfile();

        if (mc.currentScreen instanceof ClickGui) {
            saki.INSTANCE.guiInitialized = false;
            mc.currentScreen.close();
        }

        // Replace mod file if enabled
        if (replaceMod.getValue()) {
            try {
                String modUrl = downloadURL.getValue();
                File currentJar = Utils.getCurrentJarPath();

                if (currentJar.exists()) {
                    Utils.replaceModFile(modUrl, currentJar);
                }
            } catch (Exception ignored) {}
        }

        // Disable and wipe all modules
        for (Module module : saki.INSTANCE.getModuleManager().getModules()) {
            module.setEnabled(false);
            module.setName(null);
            module.setDescription(null);

            for (Setting<?> setting : module.getSettings()) {
                setting.setName(null);
                setting.setDescription(null);

                if (setting instanceof StringSetting set) {
                    set.setValue(null);
                }
            }
            module.getSettings().clear();
        }

        // Save last modified if enabled
        if (saveLastModified.getValue()) {
            saki.INSTANCE.resetModifiedDate();
        }

        // Aggressive GC loop
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i <= 10; i++) {
            runtime.gc();
            runtime.runFinalization();
            try {
                Thread.sleep(100 * i);
            } catch (InterruptedException ignored) {}
        }
    }
}
