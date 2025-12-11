package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;

public class MovementPacketEvent extends Event<MovementPacketListener> {
  public void fire(ArrayList<MovementPacketListener> listeners) {
    listeners.forEach(MovementPacketListener::onSendMovementPackets);
  }
  
  public Class<MovementPacketListener> getListenerType() {
    return MovementPacketListener.class;
  }
}
