package net.mehvahdjukaar.modelfix.font;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.GameRenderer;
import org.lwjgl.BufferUtils;

public final class GlyphPage {
    private int imgSize;

    private int maxFontHeight = -1;

    private final Font font;

    private final boolean antiAliasing;

    private final boolean fractionalMetrics;

    public int getMaxFontHeight() {
        return this.maxFontHeight;
    }

    private final HashMap<Character, Glyph> glyphCharacterMap = new HashMap<>();

    private BufferedImage bufferedImage;

    private AbstractTexture loadedTexture;

    public GlyphPage(Font font, boolean antiAliasing, boolean fractionalMetrics) {
        this.font = font;
        this.antiAliasing = antiAliasing;
        this.fractionalMetrics = fractionalMetrics;
    }

    public void generateGlyphPage(char[] chars) {
        double maxWidth = -1.0D;
        double maxHeight = -1.0D;
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, this.antiAliasing, this.fractionalMetrics);
        for (char ch : chars) {
            Rectangle2D bounds = this.font.getStringBounds(Character.toString(ch), fontRenderContext);
            if (maxWidth < bounds.getWidth())
                maxWidth = bounds.getWidth();
            if (maxHeight < bounds.getHeight())
                maxHeight = bounds.getHeight();
        }
        maxWidth += 2.0D;
        maxHeight += 2.0D;
        this.imgSize = (int)Math.ceil(Math.max(Math.ceil(Math.sqrt(maxWidth * maxWidth * chars.length) / maxWidth),
                Math.ceil(Math.sqrt(maxHeight * maxHeight * chars.length) / maxHeight)) * Math.max(maxWidth, maxHeight)) + 1;
        this.bufferedImage = new BufferedImage(this.imgSize, this.imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = this.bufferedImage.createGraphics();
        g.setFont(this.font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, this.imgSize, this.imgSize);
        g.setColor(Color.white);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasing ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        FontMetrics fontMetrics = g.getFontMetrics();
        int currentCharHeight = 0;
        int posX = 0;
        int posY = 1;
        for (char ch : chars) {
            Glyph glyph = new Glyph();
            Rectangle2D bounds = fontMetrics.getStringBounds(Character.toString(ch), g);
            glyph.width = (bounds.getBounds()).width + 8;
            glyph.height = (bounds.getBounds()).height;
            if (posX + glyph.width >= this.imgSize) {
                posX = 0;
                posY += currentCharHeight;
                currentCharHeight = 0;
            }
            glyph.x = posX;
            glyph.y = posY;
            if (glyph.height > this.maxFontHeight)
                this.maxFontHeight = glyph.height;
            if (glyph.height > currentCharHeight)
                currentCharHeight = glyph.height;
            g.drawString(Character.toString(ch), posX + 2, posY + fontMetrics.getAscent());
            posX += glyph.width;
            this.glyphCharacterMap.put(ch, glyph);
        }
    }

    public void setupTexture() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(this.bufferedImage, "png", baos);
            byte[] bytes = baos.toByteArray();
            ByteBuffer data = BufferUtils.createByteBuffer(bytes.length).put(bytes);
            data.flip();
            NativeImage nativeImage = NativeImage.read(data);
            this.loadedTexture = new NativeImageBackedTexture(nativeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindTexture() {
        RenderSystem.setShaderTexture(0, this.loadedTexture.getGlId());
    }

    public void unbindTexture() {
        RenderSystem.setShaderTexture(0, 0);
    }

    public float drawChar(MatrixStack stack, char ch, float x, float y, float red, float blue, float green, float alpha) {
        Glyph glyph = this.glyphCharacterMap.get(ch);
        if (glyph == null)
            return 0.0F;
        float pageX = glyph.x / (float)this.imgSize;
        float pageY = glyph.y / (float)this.imgSize;
        float pageWidth = glyph.width / (float)this.imgSize;
        float pageHeight = glyph.height / (float)this.imgSize;
        float width = glyph.width;
        float height = glyph.height;
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        bindTexture();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(stack.peek().getPositionMatrix(), x, y + height, 0.0F).color(red, green, blue, alpha).texture(pageX, pageY + pageHeight).next();
        bufferBuilder.vertex(stack.peek().getPositionMatrix(), x + width, y + height, 0.0F).color(red, green, blue, alpha).texture(pageX + pageWidth, pageY + pageHeight).next();
        bufferBuilder.vertex(stack.peek().getPositionMatrix(), x + width, y, 0.0F).color(red, green, blue, alpha).texture(pageX + pageWidth, pageY).next();
        bufferBuilder.vertex(stack.peek().getPositionMatrix(), x, y, 0.0F).color(red, green, blue, alpha).texture(pageX, pageY).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());
        unbindTexture();
        return width - 8.0F;
    }

    public float getWidth(char ch) {
        return this.glyphCharacterMap.get(ch).width;
    }

    public boolean isAntiAliasingEnabled() {
        return this.antiAliasing;
    }

    public boolean isFractionalMetricsEnabled() {
        return this.fractionalMetrics;
    }

    static class Glyph {
        private int x;
        private int y;
        private int width;
        private int height;

        public int getX() { return this.x; }
        public int getY() { return this.y; }
        public int getWidth() { return this.width; }
        public int getHeight() { return this.height; }

        Glyph(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        Glyph() {}
    }
}
