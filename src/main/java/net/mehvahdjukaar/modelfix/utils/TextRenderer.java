package net.mehvahdjukaar.modelfix.utils;

import net.mehvahdjukaar.modelfix.font.Fonts;
import net.mehvahdjukaar.modelfix.saki;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public final class TextRenderer {
    public static void drawString(CharSequence string, DrawContext context, int x, int y, int color) {
        boolean custom = ClickGUI.customFont.getValue();
        if (custom) {
            Fonts.QUICKSAND.drawString(context.getMatrices(), string, x, (y - 8), color);
        } else {
            drawMinecraftText(string, context, x, y, color);
        }
    }

    public static int getWidth(CharSequence string) {
        boolean custom = ClickGUI.customFont.getValue();
        if (custom) {
            return Fonts.QUICKSAND.getStringWidth(string);
        }
        return saki.mc.textRenderer.getWidth(string.toString()) * 2;
    }

    public static void drawCenteredString(CharSequence string, DrawContext context, int x, int y, int color) {
        boolean custom = ClickGUI.customFont.getValue();
        if (custom) {
            Fonts.QUICKSAND.drawString(context.getMatrices(), string,
                    x - Fonts.QUICKSAND.getStringWidth(string) / 2, (y - 8), color);
        } else {
            drawCenteredMinecraftText(string, context, x, y, color);
        }
    }

    public static void drawLargeString(CharSequence string, DrawContext context, int x, int y, int color) {
        boolean custom = ClickGUI.customFont.getValue();
        if (custom) {
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.scale(1.4F, 1.4F, 1.4F);
            Fonts.QUICKSAND.drawString(context.getMatrices(), string, x, (y - 8), color);
            matrices.scale(1.0F, 1.0F, 1.0F);
            matrices.pop();
        } else {
            drawLargerMinecraftText(string, context, x, y, color);
        }
    }

    public static void drawMinecraftText(CharSequence string, DrawContext context, int x, int y, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(2.0F, 2.0F, 2.0F);
        context.drawText(saki.mc.textRenderer, string.toString(), x / 2, y / 2, color, false);
        matrices.scale(1.0F, 1.0F, 1.0F);
        matrices.pop();
    }

    public static void drawLargerMinecraftText(CharSequence string, DrawContext context, int x, int y, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(3.0F, 3.0F, 3.0F);
        context.drawText(saki.mc.textRenderer, string.toString(), x / 3, y / 3, color, false);
        matrices.scale(1.0F, 1.0F, 1.0F);
        matrices.pop();
    }

    public static void drawCenteredMinecraftText(CharSequence string, DrawContext context, int x, int y, int color) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.scale(2.0F, 2.0F, 2.0F);
        context.drawText(saki.mc.textRenderer, string.toString(),
                x / 2 - saki.mc.textRenderer.getWidth(string.toString()) / 2,
                y / 2, color, false);
        matrices.scale(1.0F, 1.0F, 1.0F);
        matrices.pop();
    }
}
