package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.addons.addon.misc.NoBreakDelay;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.client.network.ClientPlayerInteractionManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Shadow
    private int blockBreakingCooldown;

    @Redirect(
            method = "updateBlockBreakingProgress",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I",
                    opcode = 180,
                    ordinal = 0
            )
    )
    public int updateBlockBreakingProgress(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        int cooldown = this.blockBreakingCooldown;
        return ((NoBreakDelay) saki.INSTANCE.getModuleManager().getModule(NoBreakDelay.class)).isEnabled() ? 0 : cooldown;
    }
}
