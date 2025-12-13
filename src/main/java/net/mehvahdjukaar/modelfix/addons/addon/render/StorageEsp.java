package net.mehvahdjukaar.modelfix.addons.addon.render;

import java.awt.Color;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.GameRenderListener;
import net.mehvahdjukaar.modelfix.event.events.PacketReceiveListener;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.WorldUtils;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;

public final class StorageEsp extends Module implements GameRenderListener, PacketReceiveListener {
    private final NumberSetting alpha = new NumberSetting(EncryptedString.of("Alpha"), 1.0D, 255.0D, 125.0D, 1.0D);

    private final BooleanSetting donutBypass = new BooleanSetting(EncryptedString.of("Donut Bypass"), false);

    private final BooleanSetting tracers = new BooleanSetting(EncryptedString.of("Tracers"), false)
            .setDescription(EncryptedString.of("Draws a line from your player to the storage block"));

    public StorageEsp() {
        super(EncryptedString.of("Storage ESP"), -1, Category.RENDER);
        addSettings(new Setting[]{this.donutBypass, this.alpha, this.tracers});
    }

    @Override
    public void onEnable() {
        this.eventManager.add(PacketReceiveListener.class, this);
        this.eventManager.add(GameRenderListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(PacketReceiveListener.class, this);
        this.eventManager.remove(GameRenderListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    @Override
    public void onGameRender(GameRenderListener.GameRenderEvent event) {
        renderStorages(event);
    }

    private Color getColor(BlockEntity blockEntity, int a) {
        if (blockEntity instanceof net.minecraft.block.entity.ChestBlockEntity)
            return new Color(200, 91, 0, a);
        if (blockEntity instanceof net.minecraft.block.entity.BarrelBlockEntity)
            return new Color(156, 91, 0, a);
        if (blockEntity instanceof net.minecraft.block.entity.EnderChestBlockEntity)
            return new Color(117, 0, 255, a);
        if (blockEntity instanceof net.minecraft.block.entity.ShulkerBoxBlockEntity)
            return new Color(138, 126, 166, a);
        if (blockEntity instanceof net.minecraft.block.entity.DispenserBlockEntity)
            return new Color(134, 0, 158, a);
        if (blockEntity instanceof net.minecraft.block.entity.HopperBlockEntity)
            return new Color(125, 125, 125, a);
        if (blockEntity instanceof net.minecraft.block.entity.FurnaceBlockEntity)
            return new Color(255, 140, 140, a);
        if (blockEntity instanceof net.minecraft.block.entity.BlastFurnaceBlockEntity)
            return new Color(80, 80, 255, a);
        return new Color(255, 255, 255, 0);
    }

    private void renderStorages(GameRenderListener.GameRenderEvent event) {
        Camera cam = this.mc.gameRenderer.getCamera();
        if (cam != null) {
            MatrixStack matrices = event.matrices;
            matrices.push();
            Vec3d vec = cam.getPos();
            matrices.translate(-vec.x, -vec.y, -vec.z);
        }

        for (WorldChunk chunk : WorldUtils.getLoadedChunks().toList()) {
            for (BlockPos blockPos : chunk.getBlockEntityPositions()) {
                BlockEntity blockEntity = this.mc.world.getBlockEntity(blockPos);
                RenderUtils.renderFilledBox(event.matrices,
                        blockPos.getX() + 0.1F, blockPos.getY() + 0.05F, blockPos.getZ() + 0.1F,
                        blockPos.getX() + 0.9F, blockPos.getY() + 0.85F, blockPos.getZ() + 0.9F,
                        getColor(blockEntity, this.alpha.getValueInt()));

                if (this.tracers.getValue()) {
                    RenderUtils.renderLine(event.matrices,
                            getColor(blockEntity, 255),
                            this.mc.crosshairTarget.getPos(),
                            new Vec3d(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D));
                }
            }
        }

        MatrixStack matrixStack = event.matrices;
        matrixStack.pop();
    }

    @Override
    public void onPacketReceive(PacketReceiveListener.PacketReceiveEvent event) {
        if (this.donutBypass.getValue() && event.packet instanceof BlockEntityUpdateS2CPacket) {
            event.cancel();
        }
    }
}
