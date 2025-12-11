package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.Listener;

public interface PlayerTickListener extends Listener {
  void onPlayerTick();
  
  public static class PlayerTickEvent extends Event<PlayerTickListener> {
    public void fire(ArrayList<PlayerTickListener> listeners) {
      listeners.forEach(PlayerTickListener::onPlayerTick);
    }
    
    public Class<PlayerTickListener> getListenerType() {
      return PlayerTickListener.class;
    }
  }
}
