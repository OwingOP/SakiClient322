package net.mehvahdjukaar.modelfix.gui.components;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.gui.Window;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public final class ModuleButton {
    private final Window parent;
    private final Module module;
    private final int offset;
    private boolean hovered;

    public ModuleButton(Window parent, Module module, int offset) {
        this.parent = parent; this.module = module; this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = parent.getX();
        int y = parent.getY() + offset;
        int w = parent.getWidth();
        int h = 20;

        hovered = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;

        Color bg = module.isEnabled() ? new Color(60, 180, 75, 180) : new Color(35, 35, 35, 180);
        if (hovered) bg = bg.brighter();

        RenderUtils.renderRoundedQuad(context.getMatrices(), bg, x, y, x + w, y + h, 6, 6, 6, 6, 20);
        TextRenderer.drawString(module.getName(), context, x + 6, y + (h / 2 - 4), Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (hovered && button == 0) module.toggle();
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {}
    public void mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {}
    public void mouseScrolled(double mouseX, double mouseY, double hAmt, double vAmt) {}
    public void keyPressed(int keyCode, int scanCode, int modifiers) {}
    public void onGuiClose() { hovered = false; }
}
