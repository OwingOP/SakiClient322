package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.minecraft.class_2596;

public class PacketSendEvent extends CancellableEvent<PacketSendListener> {
  public class_2596 packet;
  
  public PacketSendEvent(class_2596 packet) {
    this.packet = packet;
  }
  
  public void fire(ArrayList<PacketSendListener> listeners) {
    listeners.forEach(e -> e.onPacketSend(this));
  }
  
  public Class<PacketSendListener> getListenerType() {
    return PacketSendListener.class;
  }
}
