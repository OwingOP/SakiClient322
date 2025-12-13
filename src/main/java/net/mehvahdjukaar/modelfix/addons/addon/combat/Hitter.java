package net.mehvahdjukaar.modelfix.addons.addon.combat;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.ModeSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.mehvahdjukaar.modelfix.managers.FriendManager;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;
import net.mehvahdjukaar.modelfix.utils.MathUtils;
import net.mehvahdjukaar.modelfix.utils.MouseSimulation;
import net.mehvahdjukaar.modelfix.utils.TimerUtils;
import net.mehvahdjukaar.modelfix.utils.WorldUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

public final class Hitter extends Module {
    // ✅ Toggles
    private final BooleanSetting onlyWeapon = new BooleanSetting(EncryptedString.of("Only Weapon"), true)
            .setDescription(EncryptedString.of("Restrict triggerbot to swords/axes"));
    private final BooleanSetting onLeftClick = new BooleanSetting(EncryptedString.of("On Left Click"), false)
            .setDescription(EncryptedString.of("Only triggers while holding left click"));
    private final BooleanSetting attackPlayers = new BooleanSetting(EncryptedString.of("Attack Players"), true);
    private final BooleanSetting attackMobs = new BooleanSetting(EncryptedString.of("Attack Mobs"), true);
    private final BooleanSetting attackFriends = new BooleanSetting(EncryptedString.of("Attack Friends"), false)
            .setDescription(EncryptedString.of("Skip attacking friends if disabled"));
    private final BooleanSetting mouseSimulation = new BooleanSetting(EncryptedString.of("Mouse Simulation"), true)
            .setDescription(EncryptedString.of("Simulate a real mouse click"));

    // ✅ Delay & speed
    private final NumberSetting attackDelay = new NumberSetting(EncryptedString.of("Attack Delay (ms)"), 0, 1000, 250, 1);
    private final MinMaxSetting randomDelay = new MinMaxSetting(EncryptedString.of("Random Delay"), 0, 200, 1, 0, 200);

    // ✅ Range
    private final NumberSetting range = new NumberSetting(EncryptedString.of("Range"), 0.1, 6, 4, 0.1);

    // ✅ Target mode
    private final ModeSetting<TargetMode> targetMode = new ModeSetting<>(EncryptedString.of("Target Mode"), TargetMode.Nearest, TargetMode.class);

    private final TimerUtils timer = new TimerUtils();
    private FriendManager friendManager;

    public enum TargetMode {
        Nearest, Crosshair
    }

    public Hitter() {
        super(EncryptedString.of("Triggerbot"),
                EncryptedString.of("Automatically attacks entities when conditions are met"),
                -1,
                Category.COMBAT);

        addSettings(onlyWeapon, onLeftClick, attackPlayers, attackMobs, attackFriends,
                mouseSimulation, attackDelay, randomDelay, range, targetMode);
    }

    @Override
    public void onEnable() {
        timer.reset();
        friendManager = saki.INSTANCE.getFriendManager();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.currentScreen != null) return;

        // Weapon check
        if (onlyWeapon.getValue() &&
                !(mc.player.getMainHandStack().getItem() instanceof SwordItem ||
                        mc.player.getMainHandStack().getItem() instanceof AxeItem)) return;

        // Left click check
        if (onLeftClick.getValue() &&
                GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) != GLFW.GLFW_PRESS) return;

        Entity target = null;

        if (targetMode.isMode(TargetMode.Crosshair) && mc.crosshairTarget instanceof EntityHitResult hit) {
            target = hit.getEntity();
        } else if (targetMode.isMode(TargetMode.Nearest)) {
            target = WorldUtils.findNearestEntity(mc.player, range.getValueFloat(), attackPlayers.getValue(), attackMobs.getValue());
        }

        if (target == null || !target.isAlive()) return;

        // Respect toggles
        if (target instanceof PlayerEntity player) {
            if (!attackPlayers.getValue()) return;

            // ✅ Friend check
            if (!attackFriends.getValue() && friendManager != null && friendManager.isFriend(player)) {
                return;
            }
        } else {
            if (!attackMobs.getValue()) return;
        }

        // Attack timing
        if (timer.delay(attackDelay.getValueInt() + MathUtils.randomInt(randomDelay.getMinInt(), randomDelay.getMaxInt()))) {
            WorldUtils.hitEntity(target, true);
            if (mouseSimulation.getValue()) {
                MouseSimulation.mouseClick(0);
            }
            timer.reset();
        }
    }
}
