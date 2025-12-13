package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;

public class MouseUpdateEvent extends Event<MouseUpdateListener> {
  public void fire(ArrayList<MouseUpdateListener> listeners) {
    listeners.forEach(MouseUpdateListener::onMouseUpdate);
  }
  
  public Class<MouseUpdateListener> getListenerType() {
    return MouseUpdateListener.class;
  }
}
