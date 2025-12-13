package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.addons.addon.render.NoBounce;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "getBobbingAnimationTime", at = @At("HEAD"), cancellable = true)
    private void removeBounceAnimation(CallbackInfoReturnable<Integer> cir) {
        if (saki.mc.player == null) return;

        NoBounce noBounce = (NoBounce) saki.INSTANCE.getModuleManager().getModule(NoBounce.class);
        if (saki.INSTANCE != null && saki.mc.player != null && noBounce.isEnabled()) {
            ItemStack mainHandStack = saki.mc.player.getMainHandStack();
            if (mainHandStack.isOf(Items.END_CRYSTAL)) {
                cir.setReturnValue(0);
            }
        }
    }
}
