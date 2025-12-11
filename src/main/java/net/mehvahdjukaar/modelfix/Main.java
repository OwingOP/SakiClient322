package net.mehvahdjukaar.modelfix;

import net.fabricmc.api.ModInitializer;

public final class Main implements ModInitializer {
  public void onInitialize() {
    try {
      new saki();
    } catch (InterruptedException|java.io.IOException interruptedException) {}
  }
}
