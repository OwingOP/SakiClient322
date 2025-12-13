package net.mehvahdjukaar.modelfix.addons.addon.client;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.KeybindSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.ButtonListener;
import net.mehvahdjukaar.modelfix.event.events.HudListener;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.WorldUtils;
import net.mehvahdjukaar.modelfix.managers.FriendManager;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.EntityHitResult;

public final class Friends extends Module implements ButtonListener, AttackListener, HudListener {
    private final KeybindSetting addFriendKey = (KeybindSetting) (new KeybindSetting(
            EncryptedString.of("Friend Key"), 2, false))
            .setDescription(EncryptedString.of("Key to add/remove friends"));

    public final BooleanSetting antiAttack = (BooleanSetting) (new BooleanSetting(
            EncryptedString.of("Anti-Attack"), false))
            .setDescription(EncryptedString.of("Doesn't let you hit friends"));

    public final BooleanSetting disableAimAssist = (BooleanSetting) (new BooleanSetting(
            EncryptedString.of("Anti-Aim"), false))
            .setDescription(EncryptedString.of("Disables aim assist for friends"));

    public final BooleanSetting friendStatus = (BooleanSetting) (new BooleanSetting(
            EncryptedString.of("Friend Status"), false))
            .setDescription(EncryptedString.of("Tells you if you're aiming at a friend or not"));

    private FriendManager manager;

    public Friends() {
        super(EncryptedString.of("Friends"), -1, Category.CLIENT);
        addSettings(new Setting[]{this.addFriendKey, this.antiAttack, this.disableAimAssist, this.friendStatus});
        setKey(-1);
    }

    @Override
    public void onEnable() {
        this.manager = saki.INSTANCE.getFriendManager();
        this.eventManager.add(ButtonListener.class, this);
        this.eventManager.add(AttackListener.class, this);
        this.eventManager.add(HudListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(ButtonListener.class, this);
        this.eventManager.remove(AttackListener.class, this);
        this.eventManager.remove(HudListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    @Override
    public void onButtonPress(ButtonListener.ButtonEvent event) {
        if (this.mc.player == null) return;
        if (this.mc.currentScreen != null) return;

        HitResult hit = this.mc.crosshairTarget;
        if (hit instanceof EntityHitResult) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            Entity entity = entityHit.getEntity();
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (event.button == this.addFriendKey.getKey() && event.action == 1) {
                    if (!this.manager.isFriend(player)) {
                        this.manager.addFriend(player);
                    } else {
                        this.manager.removeFriend(player);
                    }
                }
            }
        }
    }

    @Override
    public void onAttack(AttackListener.AttackEvent event) {
        if (!this.antiAttack.getValue()) return;
        if (this.manager.isAimingOverFriend()) event.cancel();
    }

    @Override
    public void onRenderHud(HudListener.HudEvent event) {
        if (!this.friendStatus.getValue()) return;

        RenderUtils.unscaledProjection();
        HitResult hit = WorldUtils.getHitResult(100.0D);
        if (hit instanceof EntityHitResult) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            Entity entity = entityHit.getEntity();
            DrawContext context = event.context;
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (this.manager.isFriend(player)) {
                    TextRenderer.drawCenteredString(
                            EncryptedString.of("Player is friend"),
                            context,
                            this.mc.getWindow().getScaledWidth() / 2,
                            this.mc.getWindow().getScaledHeight() / 2 + 25,
                            Color.GREEN.getRGB()
                    );
                }
            }
        }
        RenderUtils.scaledProjection();
    }
}
