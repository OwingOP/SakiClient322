package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.minecraft.class_332;

public class HudEvent extends Event<HudListener> {
  public class_332 context;
  
  public float delta;
  
  public HudEvent(class_332 context, float delta) {
    this.context = context;
    this.delta = delta;
  }
  
  public void fire(ArrayList<HudListener> listeners) {
    listeners.forEach(e -> e.onRenderHud(this));
  }
  
  public Class<HudListener> getListenerType() {
    return HudListener.class;
  }
}
