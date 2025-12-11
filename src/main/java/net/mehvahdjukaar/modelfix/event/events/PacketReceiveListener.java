package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.minecraft.class_2596;

public interface PacketReceiveListener extends Listener {
  void onPacketReceive(PacketReceiveEvent paramPacketReceiveEvent);
  
  public static class PacketReceiveEvent extends CancellableEvent<PacketReceiveListener> {
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
}
