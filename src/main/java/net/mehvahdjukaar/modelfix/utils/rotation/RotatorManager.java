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

import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

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
        this.eventManager.remove(PacketSendListener.class, this);
        this.eventManager.remove(AttackListener.class, this);
        this.eventManager.remove(ItemUseListener.class, this);
        this.eventManager.remove(MovementPacketListener.class, this);
        this.eventManager.remove(PacketReceiveListener.class, this);
        this.eventManager.remove(BlockBreakingListener.class, this);
        this.enabled = true;
        this.rotateBack = false;
        this.resetRotation = false;
        this.serverYaw = 0.0F;
        this.serverPitch = 0.0F;
        this.clientYaw = 0.0F;
        this.clientPitch = 0.0F;
    }

    public void shutDown() {
        this.eventManager.remove(PacketSendListener.class, this);
        this.eventManager.remove(AttackListener.class, this);
        this.eventManager.remove(ItemUseListener.class, this);
        this.eventManager.remove(MovementPacketListener.class, this);
        this.eventManager.remove(PacketReceiveListener.class, this);
        this.eventManager.remove(BlockBreakingListener.class, this);
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
            if (!this.rotateBack) {
                this.rotateBack = true;
            }
        }
    }

    public void setRotation(Rotation rotation) {
        this.currentRotation = rotation;
    }

    public void setRotation(double yaw, double pitch) {
        setRotation(new Rotation(yaw, pitch));
    }

    private void resetClientRotation() {
        saki.mc.player.setYaw(this.clientYaw);
        saki.mc.player.setPitch(this.clientPitch);
        this.resetRotation = false;
    }

    public void setClientRotation(Rotation rotation) {
        this.clientYaw = saki.mc.player.getYaw();
        this.clientPitch = saki.mc.player.getPitch();
        saki.mc.player.setYaw((float) rotation.yaw());
        saki.mc.player.setPitch((float) rotation.pitch());
        this.resetRotation = true;
    }

    public void setServerRotation(Rotation rotation) {
        this.serverYaw = (float) rotation.yaw();
        this.serverPitch = (float) rotation.pitch();
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
        Packet<?> packet = event.packet;
        if (packet instanceof PlayerMoveC2SPacket movePacket) {
            this.serverYaw = movePacket.getYaw(this.serverYaw);
            this.serverPitch = movePacket.getPitch(this.serverPitch);
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
            Rotation clientRot = new Rotation(saki.mc.player.getYaw(), saki.mc.player.getPitch());
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
        Packet<?> packet = event.packet;
        if (packet instanceof PlayerPositionLookS2CPacket lookPacket) {
            this.serverYaw = lookPacket.getYaw();
            this.serverPitch = lookPacket.getPitch();
        }
    }
}
