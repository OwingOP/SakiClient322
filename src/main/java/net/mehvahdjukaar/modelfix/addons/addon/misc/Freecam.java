package net.mehvahdjukaar.modelfix.addons.addon.misc;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.CameraUpdateListener;
import net.mehvahdjukaar.modelfix.event.events.TickListener;
import net.mehvahdjukaar.modelfix.mixin.KeyBindingAccessor;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.RenderTickCounter;

import org.lwjgl.glfw.GLFW;

public final class Freecam extends Module implements TickListener, CameraUpdateListener {
    private final NumberSetting speed = new NumberSetting(
            EncryptedString.of("Speed"), 1.0D, 10.0D, 1.0D, 1.0D);

    public Vec3d oldPos;
    public Vec3d pos;

    public Freecam() {
        super(EncryptedString.of("Freecam"), -1, Category.MISC);
        addSettings(new Setting[]{this.speed});
        this.oldPos = Vec3d.ZERO;
        this.pos = Vec3d.ZERO;
    }

    @Override
    public void onEnable() {
        this.eventManager.add(TickListener.class, this);
        this.eventManager.add(CameraUpdateListener.class, this);
        if (this.mc.world != null)
            this.oldPos = this.pos = this.mc.player.getCameraPosVec(1.0F);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(TickListener.class, this);
        this.eventManager.remove(CameraUpdateListener.class, this);
        if (this.mc.world != null) {
            this.mc.player.setVelocity(Vec3d.ZERO);
            this.mc.gameRenderer.reset();
        }
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    @Override
    public void onTick() {
        if (this.mc.currentScreen != null) return;

        // Disable vanilla movement keys
        this.mc.options.forwardKey.setPressed(false);
        this.mc.options.backKey.setPressed(false);
        this.mc.options.leftKey.setPressed(false);
        this.mc.options.rightKey.setPressed(false);
        this.mc.options.jumpKey.setPressed(false);
        this.mc.options.sneakKey.setPressed(false);
        this.mc.options.sprintKey.setPressed(false);

        float rad = (float) Math.toRadians(1);
        float pi = (float) Math.PI;

        ClientPlayerEntity player = this.mc.player;
        Vec3d forward = new Vec3d(
                -MathHelper.sin(-player.getYaw() * rad - pi),
                0.0D,
                -MathHelper.cos(-player.getYaw() * rad - pi)
        );
        Vec3d up = new Vec3d(0.0D, 1.0D, 0.0D);
        Vec3d left = up.crossProduct(forward);
        Vec3d right = forward.crossProduct(up);

        Vec3d move = Vec3d.ZERO;

        KeyBinding forwardKey = this.mc.options.forwardKey;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) forwardKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
            move = move.add(forward);
        }

        KeyBinding backKey = this.mc.options.backKey;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) backKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
            move = move.subtract(forward);
        }

        KeyBinding leftKey = this.mc.options.leftKey;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) leftKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
            move = move.add(left);
        }

        KeyBinding rightKey = this.mc.options.rightKey;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) rightKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
            move = move.add(right);
        }

        KeyBinding jumpKey = this.mc.options.jumpKey;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) jumpKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
            move = move.add(0.0D, this.speed.getValue(), 0.0D);
        }

        KeyBinding sneakKey = this.mc.options.sneakKey;
        if (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) sneakKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) {
            move = move.add(0.0D, -this.speed.getValue(), 0.0D);
        }

        KeyBinding sprintKey = this.mc.options.sprintKey;
        double multiplier = (GLFW.glfwGetKey(this.mc.getWindow().getHandle(),
                ((KeyBindingAccessor) sprintKey).getBoundKey().getCode()) == GLFW.GLFW_PRESS) ? 2.0 : 1.0;

        move = move.normalize().multiply(this.speed.getValue() * multiplier);

        this.oldPos = this.pos;
        this.pos = this.pos.add(move);
    }

    @Override
    public void onCameraUpdate(CameraUpdateListener.CameraUpdateEvent event) {
        float tickDelta = TickCounter.INSTANCE.getTickDelta(true);
        if (this.mc.currentScreen != null) return;

        event.setX(MathHelper.lerp(tickDelta, this.oldPos.x, this.pos.x));
        event.setY(MathHelper.lerp(tickDelta, this.oldPos.y, this.pos.y));
        event.setZ(MathHelper.lerp(tickDelta, this.oldPos.z, this.pos.z));
    }
}
