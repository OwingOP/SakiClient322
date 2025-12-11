package net.mehvahdjukaar.modelfix.mixin;

import com.mojang.authlib.GameProfile;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.events.MovementPacketListener;
import net.mehvahdjukaar.modelfix.event.events.PlayerTickListener;
import net.minecraft.class_310;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_746.class})
public class ClientPlayerEntityMixin extends class_742 {
  @Shadow
  @Final
  protected class_310 field_3937;
  
  public ClientPlayerEntityMixin(class_638 world, GameProfile profile) {
    super(world, profile);
  }
  
  @Inject(method = {"sendMovementPackets"}, at = {@At("HEAD")})
  private void onSendMovementPackets(CallbackInfo ci) {
    EventManager.fire((Event)new MovementPacketListener.MovementPacketEvent());
  }
  
  @Inject(method = {"tick"}, at = {@At("HEAD")})
  private void onPlayerTick(CallbackInfo ci) {
    EventManager.fire((Event)new PlayerTickListener.PlayerTickEvent());
  }
}
