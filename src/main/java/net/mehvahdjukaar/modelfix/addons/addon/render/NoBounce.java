package net.mehvahdjukaar.modelfix.addons.addon.render;

import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.utils.EncryptedString;

import net.minecraft.client.util.math.MatrixStack;

public final class NoBounce extends Module {
    @Override
    public void onRender3D(MatrixStack matrixStack, float partialTicks) {}

    public NoBounce() {
        super(EncryptedString.of("No Bounce"), -1, Category.RENDER);
    }
}
