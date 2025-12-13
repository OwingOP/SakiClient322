package net.mehvahdjukaar.modelfix.addons.addon.misc;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.mehvahdjukaar.modelfix.event.events.AttackListener;
import net.mehvahdjukaar.modelfix.event.events.BlockBreakingListener;
import net.mehvahdjukaar.modelfix.event.events.ItemUseListener;
import net.mehvahdjukaar.modelfix.utils.BlockUtils;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.block.Blocks;

public final class Prevent extends Module implements ItemUseListener, AttackListener, BlockBreakingListener {
    private final BooleanSetting doubleGlowstone = new BooleanSetting(EncryptedString.of("Double Glowstone"), false)
            .setDescription(EncryptedString.of("Makes it so you can't charge the anchor again if it's already charged"));

    private final BooleanSetting glowstoneMisplace = new BooleanSetting(EncryptedString.of("Glowstone Misplace"), false)
            .setDescription(EncryptedString.of("Makes it so you can only right-click with glowstone only when aiming at an anchor"));

    private final BooleanSetting anchorOnAnchor = new BooleanSetting(EncryptedString.of("Anchor on Anchor"), false)
            .setDescription(EncryptedString.of("Makes it so you can't place an anchor on/next to another anchor unless charged"));

    private final BooleanSetting obiPunch = new BooleanSetting(EncryptedString.of("Obi Punch"), false)
            .setDescription(EncryptedString.of("Makes it so you can crystal faster by not letting you left click/start breaking the obsidian"));

    private final BooleanSetting echestClick = new BooleanSetting(EncryptedString.of("E-chest click"), false)
            .setDescription(EncryptedString.of("Makes it so you can't click on e-chests with PvP items"));

    public Prevent() {
        super(EncryptedString.of("Prevent"), -1, Category.MISC);
        addSettings(new Setting[]{this.doubleGlowstone, this.glowstoneMisplace, this.anchorOnAnchor, this.obiPunch, this.echestClick});
    }

    @Override
    public void onEnable() {
        this.eventManager.add(BlockBreakingListener.class, this);
        this.eventManager.add(AttackListener.class, this);
        this.eventManager.add(ItemUseListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventManager.remove(BlockBreakingListener.class, this);
        this.eventManager.remove(AttackListener.class, this);
        this.eventManager.remove(ItemUseListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    @Override
    public void onAttack(AttackListener.AttackEvent event) {
        HitResult hitResult = this.mc.crosshairTarget;
        if (hitResult instanceof BlockHitResult) {
            BlockHitResult hit = (BlockHitResult) hitResult;
            if (BlockUtils.isBlock(hit.getBlockPos(), Blocks.OBSIDIAN) && this.obiPunch.getValue()
                    && this.mc.player.isHolding(Items.END_CRYSTAL)) {
                event.cancel();
            }
        }
    }

    @Override
    public void onBlockBreaking(BlockBreakingListener.BlockBreakingEvent event) {
        HitResult hitResult = this.mc.crosshairTarget;
        if (hitResult instanceof BlockHitResult) {
            BlockHitResult hit = (BlockHitResult) hitResult;
            if (BlockUtils.isBlock(hit.getBlockPos(), Blocks.OBSIDIAN) && this.obiPunch.getValue()
                    && this.mc.player.isHolding(Items.END_CRYSTAL)) {
                event.cancel();
            }
        }
    }

    @Override
    public void onItemUse(ItemUseListener.ItemUseEvent event) {
        HitResult hitResult = this.mc.crosshairTarget;
        if (hitResult instanceof BlockHitResult) {
            BlockHitResult hit = (BlockHitResult) hitResult;

            if (BlockUtils.isAnchorCharged(hit.getBlockPos()) && this.doubleGlowstone.getValue()
                    && this.mc.player.isHolding(Items.GLOWSTONE)) {
                event.cancel();
            }
            if (!BlockUtils.isBlock(hit.getBlockPos(), Blocks.RESPAWN_ANCHOR) && this.glowstoneMisplace.getValue()
                    && this.mc.player.isHolding(Items.GLOWSTONE)) {
                event.cancel();
            }
            if (BlockUtils.isAnchorNotCharged(hit.getBlockPos()) && this.anchorOnAnchor.getValue()
                    && this.mc.player.isHolding(Items.RESPAWN_ANCHOR)) {
                event.cancel();
            }
            if (BlockUtils.isBlock(hit.getBlockPos(), Blocks.ENDER_CHEST) && this.echestClick.getValue()
                    && (this.mc.player.getMainHandStack().getItem() instanceof EndCrystalItem
                    || this.mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL
                    || this.mc.player.getMainHandStack().getItem() == Items.OBSIDIAN
                    || this.mc.player.getMainHandStack().getItem() == Items.RESPAWN_ANCHOR
                    || this.mc.player.getMainHandStack().getItem() == Items.GLOWSTONE)) {
                event.cancel();
            }
        }
    }
}
