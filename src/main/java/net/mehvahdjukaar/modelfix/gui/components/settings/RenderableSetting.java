package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;

/**
 * Base class for GUI settings (sliders, checkboxes, etc.).
 */
public abstract class RenderableSetting {
    protected final ModuleButton parent;
    protected final int offset;

    public RenderableSetting(ModuleButton parent, int offset) {
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtils.renderRoundedQuad(context.getMatrices(),
                new Color(45, 45, 45, 180),
                parent.getX(), parent.getY() + offset,
                parent.getX() + parentWidth(), parent.getY() + offset + parentHeight(),
                6, 6, 6, 6, 20);
    }

    public void onUpdate() {}
    public void onGuiClose() {}
    public void mouseClicked(double mouseX, double mouseY, int button) {}
    public void mouseReleased(double mouseX, double mouseY, int button) {}
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {}
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {}
    public void keyPressed(int keyCode, int scanCode, int modifiers) {}

    protected int parentWidth() { return parent != null ? parent.parent.getWidth() : 200; }
    protected int parentHeight() { return 20; }
}
