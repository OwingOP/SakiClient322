package net.mehvahdjukaar.modelfix.addons.addon.misc;

import com.google.common.collect.Queues;
import java.util.Queue;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.event.events.PacketSendListener;
import net.mehvahdjukaar.modelfix.event.events.PlayerTickListener;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.TimerUtils;

import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.Packet;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;

public final class FakeLag extends Module implements PlayerTickListener, PacketReceiveListener, PacketSendListener {
    public final Queue<Packet<?>> packetQueue = Queues.newConcurrentLinkedQueue();

    public boolean bool;

    public Vec3d pos = Vec3d.ZERO;

    public TimerUtils timerUtil = new TimerUtils();

    private final MinMaxSetting lagDelay = new MinMaxSetting(
            EncryptedString.of("Lag Delay"), 0.0D, 1000.0D, 1.0D, 100.0D, 200.0D);

    private final BooleanSetting cancelOnElytra = (BooleanSetting) (new BooleanSetting(
            EncryptedString.of("Cancel on Elytra"), false))
            .setDescription(EncryptedString.of("Cancel the lagging effect when you're wearing an elytra"));

    private int delay;

    public FakeLag() {
        super(EncryptedString.of("Fake Lag"), -1, Category.MISC);
        addSettings(new Setting[]{this.lagDelay, this.cancelOnElytra});
    }

    @Override
    public void onEnable() {
        this.eventManager.add(PlayerTickListener.class, this);
        this.eventManager.add(PacketSendListener.class, this);
        this.eventManager.add(PacketReceiveListener.class, this);
        this.timerUtil.reset();
        if (this.mc.player != null)
            this.pos = this.mc.player.getPos();
        this.delay = this.lagDelay.getRandomValueInt();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(PlayerTickListener.class, this);
        this.eventManager.remove(PacketSendListener.class, this);
        this.eventManager.remove(PacketReceiveListener.class, this);
        reset();
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    @Override
    public void onPacketReceive(PacketReceiveListener.PacketReceiveEvent event) {
        if (this.mc.world == null) return;
        if (this.mc.player.isDead()) return;
        if (event.packet instanceof DisconnectS2CPacket) reset();
    }

    @Override
    public void onPacketSend(PacketSendListener.PacketSendEvent event) {
        if (this.mc.world == null || this.mc.player.isSpectator() || this.mc.player.isDead()) return;

        if (event.packet instanceof PlayerMoveC2SPacket
                || event.packet instanceof PlayerInteractEntityC2SPacket
                || event.packet instanceof PlayerInteractBlockC2SPacket
                || event.packet instanceof PlayerActionC2SPacket) {
            reset();
            return;
        }

        if (this.cancelOnElytra.getValue()
                && this.mc.player.getInventory().armor.get(2).getItem() == Items.ELYTRA) {
            reset();
            return;
        }

        if (!this.bool) {
            this.packetQueue.add(event.packet);
            event.cancel();
        }
    }

    @Override
    public void onPlayerTick() {
        if (this.timerUtil.delay(this.delay)
                && this.mc.player != null && !this.mc.player.isSpectator()) {
            reset();
            this.delay = this.lagDelay.getRandomValueInt();
        }
    }

    private void reset() {
        if (this.mc.player == null || this.mc.world == null) return;
        this.bool = true;
        synchronized (this.packetQueue) {
            while (!this.packetQueue.isEmpty()) {
                this.mc.getNetworkHandler().sendPacket(this.packetQueue.poll());
            }
        }
        this.bool = false;
        this.timerUtil.reset();
        this.pos = this.mc.player.getPos();
    }
}
