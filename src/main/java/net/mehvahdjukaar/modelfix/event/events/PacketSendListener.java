package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.minecraft.network.packet.Packet;

public interface PacketSendListener extends Listener {
    void onPacketSend(PacketSendEvent paramPacketSendEvent);

    public static class PacketSendEvent extends CancellableEvent<PacketSendListener> {
        public Packet<?> packet;

        public PacketSendEvent(Packet<?> packet) {
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
