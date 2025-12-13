package net.mehvahdjukaar.modelfix.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.Module;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public final class Window {
    private int x, y, width, height;
    public final Category category;
    public final ClickGui parent;
    public final List<ModuleButton> moduleButtons = new ArrayList<>();
    public boolean dragging;
    private int dragOffsetX, dragOffsetY;
    public Color currentColor;

    public Window(int x, int y, int width, int height, Category category, ClickGui parent) {
        this.x = x; this.y = y; this.width = width; this.height = height;
        this.category = category; this.parent = parent;

        // Create buttons for each module in this category (if needed later)
        // Module list population likely happens elsewhere in your codebase.
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (currentColor == null) currentColor = new Color(35, 35, 35, 200);

        RenderUtils.renderRoundedQuad(context.getMatrices(), currentColor,
                x, y, x + width, y + height, 6, 6, 6, 6, 20);

        String title = category.name();
        int titleWidth = TextRenderer.getWidth(title);
        int startX = x + (width / 2) - (titleWidth / 2);
        TextRenderer.drawString(title, context, startX, y + 6, Color.WHITE.getRGB());

        for (ModuleButton button : moduleButtons) {
            button.render(context, mouseX, mouseY, delta);
        }
    }

    public void updatePosition(int mouseX, int mouseY, float delta) {
        if (dragging) { this.x = mouseX - dragOffsetX; this.y = mouseY - dragOffsetY; }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0 && !parent.isDraggingAlready()) {
            dragging = true;
            dragOffsetX = (int) mouseX - x;
            dragOffsetY = (int) mouseY - y;
        }
        for (ModuleButton b : moduleButtons) b.mouseClicked(mouseX, mouseY, button);
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging && button == 0) dragging = false;
        for (ModuleButton b : moduleButtons) b.mouseReleased(mouseX, mouseY, button);
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        for (ModuleButton b : moduleButtons) b.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    public void mouseScrolled(double mouseX, double mouseY, double hAmt, double vAmt) {
        for (ModuleButton b : moduleButtons) b.mouseScrolled(mouseX, mouseY, hAmt, vAmt);
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (ModuleButton b : moduleButtons) b.keyPressed(keyCode, scanCode, modifiers);
    }

    public void onGuiClose() {
        for (ModuleButton b : moduleButtons) b.onGuiClose();
    }

    private boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
