package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.Listener;

public interface TickListener extends Listener {
  void onTick();
  
  public static class TickEvent extends Event<TickListener> {
    public void fire(ArrayList<TickListener> listeners) {
      listeners.forEach(TickListener::onTick);
    }
    
    public Class<TickListener> getListenerType() {
      return TickListener.class;
    }
  }
}
