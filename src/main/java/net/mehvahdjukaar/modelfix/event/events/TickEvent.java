package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;

public class TickEvent extends Event<TickListener> {
  public void fire(ArrayList<TickListener> listeners) {
    listeners.forEach(TickListener::onTick);
  }
  
  public Class<TickListener> getListenerType() {
    return TickListener.class;
  }
}
