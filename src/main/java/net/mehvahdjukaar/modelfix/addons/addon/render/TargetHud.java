package net.mehvahdjukaar.modelfix.addons.addon.render;

import java.awt.Color;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.events.HudListener;
import net.mehvahdjukaar.modelfix.event.events.PacketSendListener;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public final class TargetHud extends Module implements HudListener, PacketSendListener {
    private final NumberSetting xCoord = new NumberSetting("X", 0.0D, 1920.0D, 500.0D, 1.0D);
    private final NumberSetting yCoord = new NumberSetting("Y", 0.0D, 1080.0D, 500.0D, 1.0D);
    private final BooleanSetting hudTimeout = new BooleanSetting("Timeout", true);

    private long lastAttackTime = 0L;
    private static final long timeout = 10000L;

    public TargetHud() {
        super("Target HUD", -1, Category.RENDER);
        addSettings(new Setting[]{this.xCoord, this.yCoord, this.hudTimeout});
    }

    @Override
    public void onEnable() {
        this.eventManager.add(HudListener.class, this);
        this.eventManager.add(PacketSendListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(HudListener.class, this);
        this.eventManager.remove(PacketSendListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}
    @Override
    public void onRenderHud(HudListener.HudEvent event) {
        DrawContext context = event.context;
        int x = this.xCoord.getValueInt();
        int y = this.yCoord.getValueInt();

        RenderUtils.unscaledProjection();
        if (!this.hudTimeout.getValue() || System.currentTimeMillis() - this.lastAttackTime <= timeout) {
            LivingEntity target = this.mc.player.getAttacking();
            if (target instanceof PlayerEntity player && player.isAlive()) {
                PlayerListEntry entry = this.mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
                context.getMatrices().push();

                RenderUtils.renderRoundedQuad(context.getMatrices(), new Color(0, 0, 0, 150),
                        x, y, x + 250, y + 50,
                        10.0D, 10.0D, 10.0D, 10.0D, 5.0D);

                if (entry != null) {
                    // PlayerSkinDrawer is gone in 1.21 — use skin textures directly:
                    context.drawTexture(entry.getSkinTextures().texture(), x + 5, y + 5, 40, 40, 0, 0, 40, 40, 40, 40);
                }

                String playerName = player.getName().getString();
                TextRenderer.drawString(playerName, context, x + 50, y + 10, Color.WHITE.getRGB());

                float health = player.getHealth() + player.getAbsorptionAmount();
                float healthPercentage = Math.min(health / player.getMaxHealth(), 1.0F);

                int barWidth = 140;
                int barHeight = 5;
                int barX = x + 50;
                int barY = y + 30;

                context.fill(barX, barY, barX + barWidth, barY + barHeight,
                        new Color(50, 50, 50, 200).getRGB());
                context.fill(barX, barY, barX + Math.round(barWidth * healthPercentage), barY + barHeight,
                        Color.GREEN.getRGB());

                context.getMatrices().pop();
            }
        }
        RenderUtils.scaledProjection();
    }

    @Override
    public void onPacketSend(PacketSendListener.PacketSendEvent event) {
        Packet<?> packet = event.packet;
        if (packet instanceof PlayerInteractEntityC2SPacket interactPacket) {
            interactPacket.handle(new PlayerInteractEntityC2SPacket.Handler() {
                @Override
                public void interact(Hand hand) {}

                @Override
                public void interactAt(Hand hand, Vec3d pos) {}

                @Override
                public void attack() {
                    if (TargetHud.this.mc.interactionManager.getAttackCooldownProgress(0.0F) == 0.0F
                            && TargetHud.this.mc.player instanceof PlayerEntity) {
                        TargetHud.this.lastAttackTime = System.currentTimeMillis();
                    }
                }
            });
        }
    }
}
