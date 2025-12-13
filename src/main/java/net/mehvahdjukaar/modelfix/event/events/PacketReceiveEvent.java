package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.minecraft.network.packet.Packet;

public class PacketReceiveEvent extends CancellableEvent<PacketReceiveListener> {
    public Packet<?> packet;

    public PacketReceiveEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public void fire(ArrayList<PacketReceiveListener> listeners) {
        listeners.forEach(e -> e.onPacketReceive(this));
    }

    public Class<PacketReceiveListener> getListenerType() {
        return PacketReceiveListener.class;
    }
}
