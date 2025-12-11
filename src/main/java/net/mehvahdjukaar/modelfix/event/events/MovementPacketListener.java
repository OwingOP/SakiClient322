package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.Listener;

public interface MovementPacketListener extends Listener {
  void onSendMovementPackets();
  
  public static class MovementPacketEvent extends Event<MovementPacketListener> {
    public void fire(ArrayList<MovementPacketListener> listeners) {
      listeners.forEach(MovementPacketListener::onSendMovementPackets);
    }
    
    public Class<MovementPacketListener> getListenerType() {
      return MovementPacketListener.class;
    }
  }
}
