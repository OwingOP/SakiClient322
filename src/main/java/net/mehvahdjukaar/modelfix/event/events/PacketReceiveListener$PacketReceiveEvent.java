package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.minecraft.class_2596;

public class PacketReceiveEvent extends CancellableEvent<PacketReceiveListener> {
  public class_2596 packet;
  
  public PacketReceiveEvent(class_2596 packet) {
    this.packet = packet;
  }
  
  public void fire(ArrayList<PacketReceiveListener> listeners) {
    listeners.forEach(e -> e.onPacketReceive(this));
  }
  
  public Class<PacketReceiveListener> getListenerType() {
    return PacketReceiveListener.class;
  }
}
