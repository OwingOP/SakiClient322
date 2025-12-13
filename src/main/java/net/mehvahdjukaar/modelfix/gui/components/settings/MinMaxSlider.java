package net.mehvahdjukaar.modelfix.gui.components.settings;

import java.awt.Color;
import net.mehvahdjukaar.modelfix.addons.setting.MinMaxSetting;
import net.mehvahdjukaar.modelfix.addons.setting.Setting;
import net.mehvahdjukaar.modelfix.gui.components.ModuleButton;
import net.mehvahdjukaar.modelfix.utils.ColorUtils;
import net.mehvahdjukaar.modelfix.utils.MathUtils;
import net.mehvahdjukaar.modelfix.utils.TextRenderer;
import net.mehvahdjukaar.modelfix.utils.Utils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

public final class MinMaxSlider extends RenderableSetting {
    public boolean draggingMin;
    public boolean draggingMax;

    public double offsetMinX;
    public double offsetMaxX;

    public double lerpedOffsetMinX = 0.0D;
    public double lerpedOffsetMaxX = 0.0D;

    private final MinMaxSetting setting;

    public Color currentColor1;
    public Color currentColor2;
    private Color currentAlpha;

    public MinMaxSlider(ModuleButton parent, Setting<?> setting, int offset) {
        super(parent, setting, offset);
        this.setting = (MinMaxSetting) setting;
    }

    @Override
    public void onUpdate() {
        Color clr1 = Utils.getMainColor(0, this.parent.settings.indexOf(this)).darker();
        Color clr2 = Utils.getMainColor(0, this.parent.settings.indexOf(this) + 1).darker();

        if (this.currentColor1 == null) {
            this.currentColor1 = new Color(clr1.getRed(), clr1.getGreen(), clr1.getBlue(), 0);
        } else {
            this.currentColor1 = new Color(clr1.getRed(), clr1.getGreen(), clr1.getBlue(), this.currentColor1.getAlpha());
        }

        if (this.currentColor2 == null) {
            this.currentColor2 = new Color(clr2.getRed(), clr2.getGreen(), clr2.getBlue(), 0);
        } else {
            this.currentColor2 = new Color(clr2.getRed(), clr2.getGreen(), clr2.getBlue(), this.currentColor2.getAlpha());
        }

        int toAlpha = 255;
        if (this.currentColor1.getAlpha() != toAlpha)
            this.currentColor1 = ColorUtils.smoothAlphaTransition(0.05F, toAlpha, this.currentColor1);
        if (this.currentColor2.getAlpha() != toAlpha)
            this.currentColor2 = ColorUtils.smoothAlphaTransition(0.05F, toAlpha, this.currentColor2);

        super.onUpdate();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        this.offsetMinX = (this.setting.getMinValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()) * parentWidth();
        this.offsetMaxX = (this.setting.getMaxValue() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin()) * parentWidth();

        this.lerpedOffsetMinX = MathUtils.goodLerp((float)(0.5D * delta), this.lerpedOffsetMinX, this.offsetMinX);
        this.lerpedOffsetMaxX = MathUtils.goodLerp((float)(0.5D * delta), this.lerpedOffsetMaxX, this.offsetMaxX);

        // Draw the selected range. DrawContext no longer has drawGradientRect in newer Fabric.
        // We render two segments to mimic a gradient: left half with color1, right half with color2.
        int x1 = parentX() + (int) this.lerpedOffsetMinX;
        int x2 = parentX() + (int) this.lerpedOffsetMaxX;
        int y1 = parentY() + this.offset + parentOffset() + 25;
        int y2 = parentY() + this.offset + parentOffset() + parentHeight();

        if (x2 > x1) {
            int mid = x1 + (x2 - x1) / 2;
            context.fill(x1, y1, mid, y2, this.currentColor1.getRGB());
            context.fill(mid, y1, x2, y2, this.currentColor2.getRGB());
        }

        TextRenderer.drawString(
                this.setting.getName() + ": " + this.setting.getMinValue() + " - " + this.setting.getMaxValue(),
                context,
                parentX() + 5,
                parentY() + parentOffset() + this.offset + 9,
                new Color(245, 245, 245, 255).getRGB()
        );

        if (!this.parent.parent.dragging) {
            int toHoverAlpha = isHovered(mouseX, mouseY) ? 15 : 0;
            if (this.currentAlpha == null) {
                this.currentAlpha = new Color(255, 255, 255, toHoverAlpha);
            } else {
                this.currentAlpha = new Color(255, 255, 255, this.currentAlpha.getAlpha());
            }
            if (this.currentAlpha.getAlpha() != toHoverAlpha)
                this.currentAlpha = ColorUtils.smoothAlphaTransition(0.05F, toHoverAlpha, this.currentAlpha);

            context.fill(
                    parentX(),
                    parentY() + parentOffset() + this.offset,
                    parentX() + parentWidth(),
                    parentY() + parentOffset() + this.offset + parentHeight(),
                    this.currentAlpha.getRGB()
            );
        }
    }

    @Override
    public void onGuiClose() {
        this.currentColor1 = null;
        this.currentColor2 = null;
        super.onGuiClose();
    }

    private void slideMin(double mouseX) {
        double a = mouseX - parentX();
        double b = MathHelper.clamp(a / parentWidth(), 0.0D, 1.0D);
        this.setting.setMinValue(MathUtils.roundToDecimal(
                b * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(),
                this.setting.getIncrement()
        ));
    }

    private void slideMax(double mouseX) {
        double a = mouseX - parentX();
        double b = MathHelper.clamp(a / parentWidth(), 0.0D, 1.0D);
        this.setting.setMaxValue(MathUtils.roundToDecimal(
                b * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin(),
                this.setting.getIncrement()
        ));
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            if (mouseX < parentX() + this.lerpedOffsetMinX + 5) {
                this.draggingMin = true;
                slideMin(mouseX);
            } else if (mouseX > parentX() + this.lerpedOffsetMaxX - 5) {
                this.draggingMax = true;
                slideMax(mouseX);
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (this.draggingMin && button == 0) this.draggingMin = false;
        if (this.draggingMax && button == 0) this.draggingMax = false;
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.draggingMin) slideMin(mouseX);
        if (this.draggingMax) slideMax(mouseX);
        super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}
