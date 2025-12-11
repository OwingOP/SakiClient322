package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.minecraft.class_4587;

public class GameRenderEvent extends Event<GameRenderListener> {
  public class_4587 matrices;
  
  public float delta;
  
  public GameRenderEvent(class_4587 matrices, float delta) {
    this.matrices = matrices;
    this.delta = delta;
  }
  
  public void fire(ArrayList<GameRenderListener> listeners) {
    listeners.forEach(e -> e.onGameRender(this));
  }
  
  public Class<GameRenderListener> getListenerType() {
    return GameRenderListener.class;
  }
}
