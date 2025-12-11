package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.event.events.PacketSendListener;
import net.minecraft.class_2535;
import net.minecraft.class_2547;
import net.minecraft.class_2596;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_2535.class})
public class ClientConnectionMixin {
  @Inject(method = {"handlePacket"}, at = {@At("HEAD")}, cancellable = true)
  private static <T extends class_2547> void onPacketReceive(class_2596<T> packet, class_2547 listener, CallbackInfo ci) {
    PacketReceiveListener.PacketReceiveEvent event = new PacketReceiveListener.PacketReceiveEvent(packet);
    EventManager.fire((Event)event);
    if (event.isCancelled())
      ci.cancel(); 
  }
  
  @Inject(method = {"send(Lnet/minecraft/network/packet/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
  private void onPacketSend(class_2596<?> packet, CallbackInfo ci) {
    PacketSendListener.PacketSendEvent event = new PacketSendListener.PacketSendEvent(packet);
    EventManager.fire((Event)event);
    if (event.isCancelled())
      ci.cancel(); 
  }
}
