package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.setting.BooleanSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.client.gui.DrawContext;

public final class CheckBox extends RenderableSetting {
    private final BooleanSetting setting;

    private Color currentAlpha;

    public CheckBox(ModuleButton parent, Setting<?> setting, int offset) {
        super(parent, setting, offset);
        this.setting = (BooleanSetting)setting;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int nameOffset = parentX() + 31;
        CharSequence chars = this.setting.getName();
        TextRenderer.drawString(chars, context, nameOffset,
                parentY() + parentOffset() + this.offset + 9,
                new Color(245, 245, 245, 255).getRGB());

        // Outer gradient box
        context.drawGradientRect(
                parentX() + 5,
                parentY() + parentOffset() + this.offset + 5,
                parentX() + 25,
                parentY() + parentOffset() + this.offset + parentHeight() - 5,
                Utils.getMainColor(255, this.parent.settings.indexOf(this)).getRGB(),
                Utils.getMainColor(255, this.parent.settings.indexOf(this) + 1).getRGB()
        );

        // Inner dark gray box
        context.fill(
                parentX() + 7,
                parentY() + parentOffset() + this.offset + 7,
                parentX() + 23,
                parentY() + parentOffset() + this.offset + parentHeight() - 7,
                Color.darkGray.getRGB()
        );

        // Inner gradient if checked
        context.drawGradientRect(
                parentX() + 9,
                parentY() + parentOffset() + this.offset + 9,
                parentX() + 21,
                parentY() + parentOffset() + this.offset + parentHeight() - 9,
                this.setting.getValue()
                        ? Utils.getMainColor(255, this.parent.settings.indexOf(this)).getRGB()
                        : Color.darkGray.getRGB(),
                this.setting.getValue()
                        ? Utils.getMainColor(255, this.parent.settings.indexOf(this) + 1).getRGB()
                        : Color.darkGray.getRGB()
        );

        if (!this.parent.parent.dragging) {
            int toHoverAlpha = isHovered(mouseX, mouseY) ? 15 : 0;
            if (this.currentAlpha == null) {
                this.currentAlpha = new Color(255, 255, 255, toHoverAlpha);
            } else {
                this.currentAlpha = new Color(255, 255, 255, this.currentAlpha.getAlpha());
            }
            if (this.currentAlpha.getAlpha() != toHoverAlpha) {
                this.currentAlpha = ColorUtils.smoothAlphaTransition(0.05F, toHoverAlpha, this.currentAlpha);
            }
            context.fill(
                    parentX(),
                    parentY() + parentOffset() + this.offset,
                    parentX() + parentWidth(),
                    parentY() + parentOffset() + this.offset + parentHeight(),
                    this.currentAlpha.getRGB()
            );
        }
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.mouseOver && this.parent.extended && keyCode == 259) {
            this.setting.setValue(this.setting.getOriginalValue());
        }
        super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            this.setting.toggle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
