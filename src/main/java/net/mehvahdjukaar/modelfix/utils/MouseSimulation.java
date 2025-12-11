package net.mehvahdjukaar.modelfix.utils;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.mehvahdjukaar.modelfix.mixin.MinecraftClientAccessor;
import net.mehvahdjukaar.modelfix.mixin.MouseHandlerAccessor;
import net.mehvahdjukaar.modelfix.saki;

public final class MouseSimulation {
  public static HashMap<Integer, Boolean> mouseButtons = new HashMap<>();
  
  public static ExecutorService clickExecutor = Executors.newFixedThreadPool(100);
  
  public static MouseHandlerAccessor getMouseHandler() {
    return (MouseHandlerAccessor)((MinecraftClientAccessor)saki.mc).getMouse();
  }
  
  public static boolean isMouseButtonPressed(int keyCode) {
    Boolean key = mouseButtons.get(Integer.valueOf(keyCode));
    return (key != null) ? key.booleanValue() : false;
  }
  
  public static void mousePress(int keyCode) {
    mouseButtons.put(Integer.valueOf(keyCode), Boolean.valueOf(true));
    getMouseHandler().press(saki.mc.method_22683().method_4490(), keyCode, 1, 0);
  }
  
  public static void mouseRelease(int keyCode) {
    getMouseHandler().press(saki.mc.method_22683().method_4490(), keyCode, 0, 0);
  }
  
  public static void mouseClick(int keyCode, int millis) {
    clickExecutor.submit(() -> {
          try {
            mousePress(keyCode);
            Thread.sleep(millis);
            mouseRelease(keyCode);
          } catch (InterruptedException interruptedException) {}
        });
  }
  
  public static void mouseClick(int keyCode) {
    mouseClick(keyCode, 35);
  }
}
