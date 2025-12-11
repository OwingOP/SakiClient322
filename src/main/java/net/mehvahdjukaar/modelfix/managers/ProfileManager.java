package net.mehvahdjukaar.modelfix.managers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.KeybindSetting;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.ModeSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.addons.setting.StringSetting;
import net.mehvahdjukaar.modelfix.saki;

public final class ProfileManager {
  private final Gson g = new Gson();
  
  private Path profileFolderPath;
  
  private Path profilePath;
  
  private String temp = System.getProperty("java.io.tmpdir");
  
  private String folderName = "UJHfsGGjbPfVZ";
  
  Path folder = Paths.get(this.temp, new String[] { this.folderName });
  
  private JsonObject profile;
  
  public ProfileManager() {
    this.profileFolderPath = this.folder;
    this.profilePath = this.profileFolderPath.resolve("a.json");
  }
  
  public void loadProfile() {
    try {
      if (!System.getProperty("os.name").toLowerCase().contains("win")) {
        this.temp = System.getProperty("user.home");
        this.folderName = "UJHfsGGjbPfVZ";
        this.profileFolderPath = this.folder;
        this.profilePath = this.profileFolderPath.resolve("a.json");
        if (!Files.isRegularFile(this.profilePath, new java.nio.file.LinkOption[0]))
          return; 
        this.profile = (JsonObject)this.g.fromJson(Files.readString(this.profilePath), JsonObject.class);
        for (Module module : saki.INSTANCE.getModuleManager().getModules()) {
          JsonElement moduleJson = this.profile.get(String.valueOf(saki.INSTANCE.getModuleManager().getModules().indexOf(module)));
          if (moduleJson == null || !moduleJson.isJsonObject())
            continue; 
          JsonObject moduleConfig = moduleJson.getAsJsonObject();
          JsonElement enabledJson = moduleConfig.get("enabled");
          if (enabledJson == null || !enabledJson.isJsonPrimitive())
            continue; 
          if (enabledJson.getAsBoolean())
            module.setEnabled(true); 
          for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
            JsonElement settingJson = moduleConfig.get(String.valueOf(module.getSettings().indexOf(setting)));
            if (settingJson == null)
              continue; 
            if (setting instanceof BooleanSetting) {
              BooleanSetting booleanSetting = (BooleanSetting)setting;
              booleanSetting.setValue(settingJson.getAsBoolean());
              continue;
            } 
            if (setting instanceof ModeSetting) {
              ModeSetting<?> modeSetting = (ModeSetting)setting;
              modeSetting.setModeIndex(settingJson.getAsInt());
              continue;
            } 
            if (setting instanceof NumberSetting) {
              NumberSetting numberSetting = (NumberSetting)setting;
              numberSetting.setValue(settingJson.getAsDouble());
              continue;
            } 
            if (setting instanceof KeybindSetting) {
              KeybindSetting keybindSetting = (KeybindSetting)setting;
              keybindSetting.setKey(settingJson.getAsInt());
              if (keybindSetting.isModuleKey())
                module.setKey(settingJson.getAsInt()); 
              continue;
            } 
            if (setting instanceof StringSetting) {
              StringSetting stringSetting = (StringSetting)setting;
              stringSetting.setValue(settingJson.getAsString());
              continue;
            } 
            if (setting instanceof MinMaxSetting) {
              MinMaxSetting minMaxSetting = (MinMaxSetting)setting;
              if (settingJson.isJsonObject()) {
                JsonObject minMaxObject = settingJson.getAsJsonObject();
                double minValue = minMaxObject.get("1").getAsDouble();
                double maxValue = minMaxObject.get("2").getAsDouble();
                minMaxSetting.setMinValue(minValue);
                minMaxSetting.setMaxValue(maxValue);
              } 
            } 
          } 
        } 
      } else {
        if (!Files.isRegularFile(this.profilePath, new java.nio.file.LinkOption[0]))
          return; 
        this.profile = (JsonObject)this.g.fromJson(Files.readString(this.profilePath), JsonObject.class);
        for (Module module : saki.INSTANCE.getModuleManager().getModules()) {
          JsonElement moduleJson = this.profile.get(String.valueOf(saki.INSTANCE.getModuleManager().getModules().indexOf(module)));
          if (moduleJson == null || !moduleJson.isJsonObject())
            continue; 
          JsonObject moduleConfig = moduleJson.getAsJsonObject();
          JsonElement enabledJson = moduleConfig.get("enabled");
          if (enabledJson == null || !enabledJson.isJsonPrimitive())
            continue; 
          if (enabledJson.getAsBoolean())
            module.setEnabled(true); 
          for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
            JsonElement settingJson = moduleConfig.get(String.valueOf(module.getSettings().indexOf(setting)));
            if (settingJson == null)
              continue; 
            if (setting instanceof BooleanSetting) {
              BooleanSetting booleanSetting = (BooleanSetting)setting;
              booleanSetting.setValue(settingJson.getAsBoolean());
              continue;
            } 
            if (setting instanceof ModeSetting) {
              ModeSetting<?> modeSetting = (ModeSetting)setting;
              modeSetting.setModeIndex(settingJson.getAsInt());
              continue;
            } 
            if (setting instanceof NumberSetting) {
              NumberSetting numberSetting = (NumberSetting)setting;
              numberSetting.setValue(settingJson.getAsDouble());
              continue;
            } 
            if (setting instanceof KeybindSetting) {
              KeybindSetting keybindSetting = (KeybindSetting)setting;
              keybindSetting.setKey(settingJson.getAsInt());
              if (keybindSetting.isModuleKey())
                module.setKey(settingJson.getAsInt()); 
              continue;
            } 
            if (setting instanceof StringSetting) {
              StringSetting stringSetting = (StringSetting)setting;
              stringSetting.setValue(settingJson.getAsString());
              continue;
            } 
            if (setting instanceof MinMaxSetting) {
              MinMaxSetting minMaxSetting = (MinMaxSetting)setting;
              if (settingJson.isJsonObject()) {
                JsonObject minMaxObject = settingJson.getAsJsonObject();
                double minValue = minMaxObject.get("1").getAsDouble();
                double maxValue = minMaxObject.get("2").getAsDouble();
                minMaxSetting.setMinValue(minValue);
                minMaxSetting.setMaxValue(maxValue);
              } 
            } 
          } 
        } 
      } 
    } catch (Exception exception) {}
  }
  
  public void saveProfile() {
    try {
      if (!System.getProperty("os.name").toLowerCase().contains("win")) {
        this.temp = System.getProperty("user.home");
        this.folderName = "UJHfsGGjbPfVZ";
        this.profileFolderPath = this.folder;
        this.profilePath = this.profileFolderPath.resolve("a.json");
        Files.createDirectories(this.profileFolderPath, (FileAttribute<?>[])new FileAttribute[0]);
        this.profile = new JsonObject();
        for (Module module : saki.INSTANCE.getModuleManager().getModules()) {
          JsonObject moduleConfig = new JsonObject();
          moduleConfig.addProperty("enabled", Boolean.valueOf(module.isEnabled()));
          for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
            if (setting instanceof BooleanSetting) {
              BooleanSetting booleanSetting = (BooleanSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Boolean.valueOf(booleanSetting.getValue()));
              continue;
            } 
            if (setting instanceof ModeSetting) {
              ModeSetting<?> modeSetting = (ModeSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Integer.valueOf(modeSetting.getModeIndex()));
              continue;
            } 
            if (setting instanceof NumberSetting) {
              NumberSetting numberSetting = (NumberSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Float.valueOf(numberSetting.getValue()));
              continue;
            } 
            if (setting instanceof KeybindSetting) {
              KeybindSetting keybindSetting = (KeybindSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Integer.valueOf(keybindSetting.getKey()));
              continue;
            } 
            if (setting instanceof StringSetting) {
              StringSetting stringSetting = (StringSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), stringSetting.getValue());
              continue;
            } 
            if (setting instanceof MinMaxSetting) {
              MinMaxSetting minMaxSetting = (MinMaxSetting)setting;
              JsonObject minMaxObject = new JsonObject();
              minMaxObject.addProperty("1", Double.valueOf(minMaxSetting.getMinValue()));
              minMaxObject.addProperty("2", Double.valueOf(minMaxSetting.getMaxValue()));
              moduleConfig.add(String.valueOf(module.getSettings().indexOf(setting)), (JsonElement)minMaxObject);
            } 
          } 
          this.profile.add(String.valueOf(saki.INSTANCE.getModuleManager().getModules().indexOf(module)), (JsonElement)moduleConfig);
        } 
        Files.writeString(this.profilePath, this.g.toJson((JsonElement)this.profile), new java.nio.file.OpenOption[0]);
      } else {
        Files.createDirectories(this.profileFolderPath, (FileAttribute<?>[])new FileAttribute[0]);
        this.profile = new JsonObject();
        for (Module module : saki.INSTANCE.getModuleManager().getModules()) {
          JsonObject moduleConfig = new JsonObject();
          moduleConfig.addProperty("enabled", Boolean.valueOf(module.isEnabled()));
          for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
            if (setting instanceof BooleanSetting) {
              BooleanSetting booleanSetting = (BooleanSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Boolean.valueOf(booleanSetting.getValue()));
              continue;
            } 
            if (setting instanceof ModeSetting) {
              ModeSetting<?> modeSetting = (ModeSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Integer.valueOf(modeSetting.getModeIndex()));
              continue;
            } 
            if (setting instanceof NumberSetting) {
              NumberSetting numberSetting = (NumberSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Float.valueOf(numberSetting.getValue()));
              continue;
            } 
            if (setting instanceof KeybindSetting) {
              KeybindSetting keybindSetting = (KeybindSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), Integer.valueOf(keybindSetting.getKey()));
              continue;
            } 
            if (setting instanceof StringSetting) {
              StringSetting stringSetting = (StringSetting)setting;
              moduleConfig.addProperty(String.valueOf(module.getSettings().indexOf(setting)), stringSetting.getValue());
              continue;
            } 
            if (setting instanceof MinMaxSetting) {
              MinMaxSetting minMaxSetting = (MinMaxSetting)setting;
              JsonObject minMaxObject = new JsonObject();
              minMaxObject.addProperty("1", Double.valueOf(minMaxSetting.getMinValue()));
              minMaxObject.addProperty("2", Double.valueOf(minMaxSetting.getMaxValue()));
              moduleConfig.add(String.valueOf(module.getSettings().indexOf(setting)), (JsonElement)minMaxObject);
            } 
          } 
          this.profile.add(String.valueOf(saki.INSTANCE.getModuleManager().getModules().indexOf(module)), (JsonElement)moduleConfig);
        } 
        Files.writeString(this.profilePath, this.g.toJson((JsonElement)this.profile), new java.nio.file.OpenOption[0]);
      } 
    } catch (Exception exception) {}
  }
}
