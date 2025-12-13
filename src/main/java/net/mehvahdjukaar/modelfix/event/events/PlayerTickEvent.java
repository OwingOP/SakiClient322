package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;

public class PlayerTickEvent extends Event<PlayerTickListener> {
  public void fire(ArrayList<PlayerTickListener> listeners) {
    listeners.forEach(PlayerTickListener::onPlayerTick);
  }
  
  public Class<PlayerTickListener> getListenerType() {
    return PlayerTickListener.class;
  }
}
