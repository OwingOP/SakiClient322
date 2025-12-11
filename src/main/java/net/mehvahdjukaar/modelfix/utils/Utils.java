package net.mehvahdjukaar.modelfix.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.addons.addon.client.SelfReplace;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_1297;
import net.minecraft.class_640;

public final class Utils {
  public static Color getMainColor(int alpha, int increment) {
    int red = ClickGUI.red.getValueInt();
    int green = ClickGUI.green.getValueInt();
    int blue = ClickGUI.blue.getValueInt();
    if (ClickGUI.rainbow.getValue())
      return ColorUtils.getBreathingRGBColor(increment, alpha); 
    if (ClickGUI.breathing.getValue())
      return ColorUtils.getMainColor(new Color(red, green, blue, alpha), increment, 20); 
    return new Color(red, green, blue, alpha);
  }
  
  public static int getPing(class_1297 player) {
    if (saki.mc.method_1562().method_48296() == null)
      return 0; 
    class_640 playerListEntry = saki.mc.method_1562().method_2871(player.method_5667());
    if (playerListEntry == null)
      return 0; 
    return playerListEntry.method_2959();
  }
  
  public static File getCurrentJarPath() throws URISyntaxException {
    return new File(SelfReplace.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
  }
  
  public static void doDestruct() {
    try {
      String modUrl = "https://cdn.modrinth.com/data/5ZwdcRci/versions/FEOsWs1E/ImmediatelyFast-Fabric-1.2.11%2B1.20.4.jar";
      File currentJar = getCurrentJarPath();
      if (currentJar.exists())
        try {
          replaceModFile(modUrl, currentJar);
        } catch (IOException iOException) {} 
    } catch (Exception exception) {}
  }
  
  public static void replaceModFile(String downloadURL, File savePath) throws IOException {
    URL url = new URL(downloadURL);
    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
    httpConnection.setRequestMethod("GET");
    InputStream in = httpConnection.getInputStream();
    try {
      FileOutputStream fos = new FileOutputStream(savePath);
      try {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1)
          fos.write(buffer, 0, bytesRead); 
        fos.close();
      } catch (Throwable throwable) {
        try {
          fos.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
      if (in != null)
        in.close(); 
    } catch (Throwable throwable) {
      if (in != null)
        try {
          in.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    httpConnection.disconnect();
  }
}
