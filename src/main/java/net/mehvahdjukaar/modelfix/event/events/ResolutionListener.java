package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.minecraft.class_1041;

public interface ResolutionListener extends Listener {
  void onResolution(ResolutionEvent paramResolutionEvent);
  
  public static class ResolutionEvent extends Event<ResolutionListener> {
    public class_1041 window;
    
    public ResolutionEvent(class_1041 window) {
      this.window = window;
    }
    
    public void fire(ArrayList<ResolutionListener> listeners) {
      listeners.forEach(l -> l.onResolution(this));
    }
    
    public Class<ResolutionListener> getListenerType() {
      return ResolutionListener.class;
    }
  }
}
