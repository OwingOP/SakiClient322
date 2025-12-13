package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;

public class MouseMoveEvent extends CancellableEvent<MouseMoveListener> {
  public long windowHandle;
  
  public double x;
  
  public double y;
  
  public MouseMoveEvent(long windowHandle, double x, double y) {
    this.windowHandle = windowHandle;
    this.x = x;
    this.y = y;
  }
  
  public void fire(ArrayList<MouseMoveListener> listeners) {
    listeners.forEach(e -> e.onMouseMove(this));
  }
  
  public Class<MouseMoveListener> getListenerType() {
    return MouseMoveListener.class;
  }
}
