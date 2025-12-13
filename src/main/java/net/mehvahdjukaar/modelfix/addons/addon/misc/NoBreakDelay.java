package net.mehvahdjukaar.modelfix.addons.addon.misc;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;

import net.minecraft.client.util.math.MatrixStack;

public final class NoBreakDelay extends Module {
    public NoBreakDelay() {
        super(EncryptedString.of("No Break Delay"), -1, Category.MISC);
    }

    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}
}
