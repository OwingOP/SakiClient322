package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;

public class ButtonEvent extends Event<ButtonListener> {
  public int button;
  
  public int action;
  
  public long window;
  
  public ButtonEvent(int button, long window, int action) {
    this.button = button;
    this.window = window;
    this.action = action;
  }
  
  public void fire(ArrayList<ButtonListener> listeners) {
    listeners.forEach(e -> e.onButtonPress(this));
  }
  
  public Class<ButtonListener> getListenerType() {
    return ButtonListener.class;
  }
}
