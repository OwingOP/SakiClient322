package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;

import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.addons.setting.StringSetting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class StringBox extends RenderableSetting {
    private final StringSetting setting;

    private Color currentAlpha;

    public StringBox(ModuleButton parent, Setting<?> setting, int offset) {
        super(parent, setting, offset);
        this.setting = (StringSetting)setting;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        TextRenderer.drawString(this.setting.getName() + ": " + this.setting.getName(), context, parentX() + 9, parentY() + parentOffset() + this.offset + 9, (new Color(245, 245, 245, 255)).getRGB());
        if (!this.parent.parent.dragging) {
            int toHoverAlpha = isHovered(mouseX, mouseY) ? 15 : 0;
            if (this.currentAlpha == null) {
                this.currentAlpha = new Color(255, 255, 255, toHoverAlpha);
            } else {
                this.currentAlpha = new Color(255, 255, 255, this.currentAlpha.getAlpha());
            }
            if (this.currentAlpha.getAlpha() != toHoverAlpha)
                this.currentAlpha = ColorUtils.smoothAlphaTransition(0.05F, toHoverAlpha, this.currentAlpha);
            context.fill(parentX(), parentY() + parentOffset() + this.offset, parentX() + parentWidth(), parentY() + parentOffset() + this.offset + parentHeight(), this.currentAlpha.getRGB());
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            this.mc.setScreen(new Screen(Text.literal("")) {
                private String content = StringBox.this.setting.getValue();

                public void render(DrawContext context, int mouseX, int mouseY, float delta) {
                    RenderUtils.unscaledProjection();
                    mouseX *= (int)MinecraftClient.getInstance().getWindow().getScaleFactor();
                    mouseY *= (int)MinecraftClient.getInstance().getWindow().getScaleFactor();
                    super.render(context, mouseX, mouseY, delta);
                    context.fill(0, 0, StringBox.this.mc.getWindow().getScaledWidth(), StringBox.this.mc.getWindow().getScaledHeight(), (new Color(0, 0, 0, ClickGUI.background.getValue() ? 200 : 0)).getRGB());
                    int screenMidX = StringBox.this.mc.getWindow().getScaledWidth() / 2;
                    int screenMidY = StringBox.this.mc.getWindow().getScaledHeight() / 2;
                    int contentWidth = Math.max(TextRenderer.getWidth(this.content), 600);
                    int width = contentWidth + 30;
                    int startX = screenMidX - width / 2;
                    int startY = screenMidY - 30;
                    RenderUtils.renderRoundedQuad(context.getMatrices(), new Color(0, 0, 0, ClickGUI.alphaWindow.getValueInt()), startX, startY, (startX + width), (screenMidY + 30), 5.0D, 5.0D, 0.0D, 0.0D, 20.0D);
                    TextRenderer.drawCenteredString(StringBox.this.setting.getName(), context, screenMidX, startY + 10, (new Color(245, 245, 245, 255)).getRGB());
                    context.fill(startX, screenMidY, startX + width, screenMidY + 30, (new Color(0, 0, 0, 120)).getRGB());
                    RenderUtils.renderRoundedOutline(context, new Color(50, 50, 50, 255), (startX + 10), (screenMidY + 5), (startX + width - 10), (screenMidY + 25), 5.0D, 5.0D, 5.0D, 5.0D, 2.0D, 20.0D);
                    TextRenderer.drawString(this.content, context, startX + 15, screenMidY + 8, (new Color(245, 245, 245, 255)).getRGB());
                    context.fill(startX, screenMidY, startX + width, screenMidY + 1, Utils.getMainColor(255, 1).getRGB());
                    RenderUtils.scaledProjection();
                }

                public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                    if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                        StringBox.this.setting.setValue(this.content.strip());
                        StringBox.this.mc.setScreen(saki.INSTANCE.clickGui);
                    }
                    if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
                    return false;
                }

                public boolean charTyped(char chr, int modifiers) {
                    this.content += chr;
                    return super.charTyped(chr, modifiers);
                }

                public boolean shouldCloseOnEsc() {
                    return false;
                }
            });
        super.mouseClicked(mouseX, mouseY, button);
    }
}
