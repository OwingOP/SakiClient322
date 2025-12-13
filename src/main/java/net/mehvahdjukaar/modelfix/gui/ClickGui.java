package net.mehvahdjukaar.modelfix.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.mehvahdjukaar.modelfix.addons.Category;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ClickGui extends Screen {
    public List<Window> windows = new ArrayList<>();
    public Color currentColor;

    public ClickGui() {
        super(Text.literal(""));
        int offsetX = 50;
        for (Category category : Category.values()) {
            this.windows.add(new Window(offsetX, 50, 230, 30, category, this));
            offsetX += 250;
        }
    }

    public boolean isDraggingAlready() {
        for (Window window : this.windows) {
            if (window.dragging) return true;
        }
        return false;
    }

    @Override
    protected void init() {
        if (this.client == null) return;
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (saki.mc.currentScreen == this) {
            if (saki.INSTANCE.previousScreen != null) {
                saki.INSTANCE.previousScreen.render(context, 0, 0, delta);
            }

            if (this.currentColor == null) {
                this.currentColor = new Color(0, 0, 0, 0);
            } else {
                this.currentColor = new Color(0, 0, 0, this.currentColor.getAlpha());
            }

            int targetAlpha = ClickGUI.background.getValue() ? 200 : 0;
            if (this.currentColor.getAlpha() != targetAlpha) {
                this.currentColor = ColorUtils.smoothAlphaTransition(0.05F, targetAlpha, this.currentColor);
            }

            context.fill(0, 0,
                    saki.mc.getWindow().getScaledWidth(),
                    saki.mc.getWindow().getScaledHeight(),
                    this.currentColor.getRGB());

            RenderUtils.unscaledProjection();
            mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
            mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();

            super.render(context, mouseX, mouseY, delta);
            for (Window window : this.windows) {
                window.render(context, mouseX, mouseY, delta);
                window.updatePosition(mouseX, mouseY, delta);
            }
            RenderUtils.scaledProjection();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Window window : this.windows) window.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (Window window : this.windows) window.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (Window window : this.windows) window.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    // Modern signature: double mouseX, double mouseY, double amount
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        MinecraftClient mc = MinecraftClient.getInstance();
        double scaledMouseY = mouseY * mc.getWindow().getScaleFactor();
        for (Window window : this.windows) window.mouseScrolled(mouseX, scaledMouseY, 0, amount);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void close() {
        ClickGUI clickGUIModule = (ClickGUI) saki.INSTANCE.getModuleManager().getModule(ClickGUI.class);
        if (clickGUIModule != null) clickGUIModule.setEnabledStatus(false);
        onGuiClose();
    }

    public void onGuiClose() {
        saki.mc.setScreen(saki.INSTANCE.previousScreen);
        this.currentColor = null;
        for (Window window : this.windows) window.onGuiClose();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseX *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        mouseY *= (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
        for (Window window : this.windows) window.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
