package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.minecraft.class_2596;

public interface PacketSendListener extends Listener {
  void onPacketSend(PacketSendEvent paramPacketSendEvent);
  
  public static class PacketSendEvent extends CancellableEvent<PacketSendListener> {
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
}
