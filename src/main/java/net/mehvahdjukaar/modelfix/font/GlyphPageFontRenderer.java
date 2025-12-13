package net.mehvahdjukaar.modelfix.font;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Font;
import java.util.Random;
import net.minecraft.client.util.math.MatrixStack;

public final class GlyphPageFontRenderer {
    public Random fontRandom = new Random();

    private float posX;
    private float posY;

    private final int[] colorCode = new int[32];

    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;

    private final GlyphPage regularGlyphPage;
    private final GlyphPage boldGlyphPage;
    private final GlyphPage italicGlyphPage;
    private final GlyphPage boldItalicGlyphPage;

    public GlyphPageFontRenderer(GlyphPage regularGlyphPage, GlyphPage boldGlyphPage, GlyphPage italicGlyphPage, GlyphPage boldItalicGlyphPage) {
        this.regularGlyphPage = regularGlyphPage;
        this.boldGlyphPage = boldGlyphPage;
        this.italicGlyphPage = italicGlyphPage;
        this.boldItalicGlyphPage = boldItalicGlyphPage;
        for (int i = 0; i < 32; i++) {
            int j = (i >> 3 & 0x1) * 85;
            int k = (i >> 2 & 0x1) * 170 + j;
            int l = (i >> 1 & 0x1) * 170 + j;
            int i1 = (i & 0x1) * 170 + j;
            if (i == 6) k += 85;
            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }
            this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
        }
    }

    // … create() and createFromID() methods unchanged …

    private int renderString(MatrixStack matrices, CharSequence text, float x, float y, int color, boolean dropShadow) {
        if (text == null) return 0;
        if ((color & 0xFC000000) == 0) color |= 0xFF000000;
        if (dropShadow) color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        this.posX = x * 2.0F;
        this.posY = y * 2.0F;
        renderStringAtPos(matrices, text, dropShadow, color);
        return (int) (this.posX / 4.0F);
    }

    private int renderString(MatrixStack matrices, CharSequence text, float x, float y, float scale, int color, boolean dropShadow) {
        if (text == null) return 0;
        if ((color & 0xFC000000) == 0) color |= 0xFF000000;
        if (dropShadow) color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;

        this.posX = x * 2.0F;
        this.posY = y * 2.0F;

        GlStateManager._pushMatrix();
        GlStateManager._scalef(scale, scale, scale);

        renderStringAtPos(matrices, text, dropShadow, color);

        GlStateManager._popMatrix();

        return (int) (this.posX / 4.0F);
    }

    private void resetStyles() {
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }

    private void renderStringAtPos(MatrixStack matrices, CharSequence text, boolean dropShadow, int color) {
        // Implementation of actual glyph rendering goes here
    }
}
