package net.mehvahdjukaar.modelfix.utils.rotation;

import net.mehvahdjukaar.modelfix.event.EventManager;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.BlockBreakingListener;
import net.mehvahdjukaar.modelfix.event.events.ItemUseListener;
import net.mehvahdjukaar.modelfix.event.events.MovementPacketListener;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.event.events.PacketSendListener;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.RotationUtils;
import net.minecraft.class_2596;
import net.minecraft.class_2708;
import net.minecraft.class_2828;

public final class RotatorManager implements PacketSendListener, BlockBreakingListener, ItemUseListener, AttackListener, MovementPacketListener, PacketReceiveListener {
  private boolean enabled;
  
  private boolean rotateBack;
  
  private boolean resetRotation;
  
  private final EventManager eventManager = saki.INSTANCE.eventManager;
  
  private Rotation currentRotation;
  
  private float clientYaw;
  
  private float clientPitch;
  
  private float serverYaw;
  
  private float serverPitch;
  
  private boolean wasDisabled;
  
  public RotatorManager() {
    this.eventManager.remove(PacketSendListener.class, (Listener)this);
    this.eventManager.remove(AttackListener.class, (Listener)this);
    this.eventManager.remove(ItemUseListener.class, (Listener)this);
    this.eventManager.remove(MovementPacketListener.class, (Listener)this);
    this.eventManager.remove(PacketReceiveListener.class, (Listener)this);
    this.eventManager.remove(BlockBreakingListener.class, (Listener)this);
    this.enabled = true;
    this.rotateBack = false;
    this.resetRotation = false;
    this.serverYaw = 0.0F;
    this.serverPitch = 0.0F;
    this.clientYaw = 0.0F;
    this.clientPitch = 0.0F;
  }
  
  public void shutDown() {
    this.eventManager.remove(PacketSendListener.class, (Listener)this);
    this.eventManager.remove(AttackListener.class, (Listener)this);
    this.eventManager.remove(ItemUseListener.class, (Listener)this);
    this.eventManager.remove(MovementPacketListener.class, (Listener)this);
    this.eventManager.remove(PacketReceiveListener.class, (Listener)this);
    this.eventManager.remove(BlockBreakingListener.class, (Listener)this);
  }
  
  public Rotation getServerRotation() {
    return new Rotation(this.serverYaw, this.serverPitch);
  }
  
  public void enable() {
    this.enabled = true;
    this.rotateBack = false;
  }
  
  public boolean isEnabled() {
    return this.enabled;
  }
  
  public void disable() {
    if (isEnabled()) {
      this.enabled = false;
      if (!this.rotateBack)
        this.rotateBack = true; 
    } 
  }
  
  public void setRotation(Rotation rotation) {
    this.currentRotation = rotation;
  }
  
  public void setRotation(double yaw, double pitch) {
    setRotation(new Rotation(yaw, pitch));
  }
  
  private void resetClientRotation() {
    polar.mc.field_1724.method_36456(this.clientYaw);
    polar.mc.field_1724.method_36457(this.clientPitch);
    this.resetRotation = false;
  }
  
  public void setClientRotation(Rotation rotation) {
    this.clientYaw = polar.mc.field_1724.method_36454();
    this.clientPitch = polar.mc.field_1724.method_36455();
    polar.mc.field_1724.method_36456((float)rotation.yaw());
    polar.mc.field_1724.method_36457((float)rotation.pitch());
    this.resetRotation = true;
  }
  
  public void setServerRotation(Rotation rotation) {
    this.serverYaw = (float)rotation.yaw();
    this.serverPitch = (float)rotation.pitch();
  }
  
  public void onAttack(AttackListener.AttackEvent event) {
    if (!isEnabled() && this.wasDisabled) {
      this.enabled = true;
      this.wasDisabled = false;
    } 
  }
  
  public void onItemUse(ItemUseListener.ItemUseEvent event) {
    if (!event.isCancelled() && isEnabled()) {
      this.enabled = false;
      this.wasDisabled = true;
    } 
  }
  
  public void onPacketSend(PacketSendListener.PacketSendEvent event) {
    class_2596 class_2596 = event.packet;
    if (class_2596 instanceof class_2828) {
      class_2828 packet = (class_2828)class_2596;
      this.serverYaw = packet.method_12271(this.serverYaw);
      this.serverPitch = packet.method_12270(this.serverPitch);
    } 
  }
  
  public void onBlockBreaking(BlockBreakingListener.BlockBreakingEvent event) {
    if (!event.isCancelled() && isEnabled()) {
      this.enabled = false;
      this.wasDisabled = true;
    } 
  }
  
  public void onSendMovementPackets() {
    if (isEnabled() && this.currentRotation != null) {
      setClientRotation(this.currentRotation);
      setServerRotation(this.currentRotation);
      return;
    } 
    if (this.rotateBack) {
      Rotation serverRot = new Rotation(this.serverYaw, this.serverPitch);
      Rotation clientRot = new Rotation(polar.mc.field_1724.method_36454(), polar.mc.field_1724.method_36455());
      if (RotationUtils.getTotalDiff(serverRot, clientRot) > 1.0D) {
        Rotation smoothRotation = RotationUtils.getSmoothRotation(serverRot, clientRot, 0.2D);
        setClientRotation(smoothRotation);
        setServerRotation(smoothRotation);
      } else {
        this.rotateBack = false;
      } 
    } 
  }
  
  public void onPacketReceive(PacketReceiveListener.PacketReceiveEvent event) {
    class_2596 class_2596 = event.packet;
    if (class_2596 instanceof class_2708) {
      class_2708 packet = (class_2708)class_2596;
      this.serverYaw = packet.method_11736();
      this.serverPitch = packet.method_11739();
    } 
  }
}
