package net.mehvahdjukaar.modelfix.addons.addon.client;

import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.NumberSetting;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Module that opens the ClickGui and holds its settings.
 */
public final class ClickGUI extends Module {

    public static final BooleanSetting background = new BooleanSetting("background", false);
    public static final NumberSetting alphaWindow = new NumberSetting("alphaWindow", 150, 0, 255, 1);
    public static final NumberSetting roundQuads = new NumberSetting("roundQuads", 6, 0, 12, 1);
    public static final BooleanSetting customFont = new BooleanSetting("customFont", false);

    public ClickGUI() {
        super("ClickGUI", Category.CLIENT);
        addSettings(background, alphaWindow, roundQuads, customFont);
    }

    public void setEnabledStatus(boolean enabled) {
        setEnabled(enabled);
    }

    @Override
    public void onRender3D(MatrixStack matrices, float tickDelta) {
        // no-op
    }
}
