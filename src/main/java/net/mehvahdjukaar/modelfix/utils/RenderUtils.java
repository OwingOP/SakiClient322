package net.mehvahdjukaar.modelfix.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

/**
 * Utility methods for rendering overlays and UI elements.
 * Updated to Yarn names for modern Minecraft versions.
 */
public final class RenderUtils {

    private static Matrix4f savedProjection;
    public static boolean rendering3D = true;

    public static Vec3d getCameraPos() {
        return saki.mc.gameRenderer.getCamera().getPos();
    }

    public static double deltaTime() {
        return (saki.mc.getCurrentFps() > 0) ? (1.0D / saki.mc.getCurrentFps()) : 1.0D;
    }

    public static float fast(float end, float start, float multiple) {
        float t = MathHelper.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F);
        return (1.0F - t) * end + t * start;
    }

    public static Vec3d getPlayerLookVec(PlayerEntity player) {
        float f = 0.017453292F;
        float pi = (float) Math.PI;
        float f1 = MathHelper.sin(-player.getYaw() * f - pi);
        float f2 = MathHelper.cos(-player.getYaw() * f - pi);
        float f3 = -MathHelper.sin(-player.getPitch() * f);
        float f4 = MathHelper.cos(-player.getPitch() * f);
        return new Vec3d((f2 * f3), f4, (f1 * f3)).normalize();
    }

    public static void unscaledProjection() {
        savedProjection = RenderSystem.getProjectionMatrix();
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(
                0.0F,
                saki.mc.getWindow().getWidth(),
                saki.mc.getWindow().getHeight(),
                0.0F,
                1000.0F,
                21000.0F
        ));
        rendering3D = false;
    }

    public static void scaledProjection() {
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(
                0.0F,
                (float)(saki.mc.getWindow().getWidth() / saki.mc.getWindow().getScaleFactor()),
                (float)(saki.mc.getWindow().getHeight() / saki.mc.getWindow().getScaleFactor()),
                0.0F,
                1000.0F,
                21000.0F
        ));
        rendering3D = true;
    }

    // --- Stubbed helpers for UI calls ---

    public static void renderRoundedQuad(MatrixStack matrices, Color color,
                                         int x1, int y1, int x2, int y2,
                                         double r1, double r2, double r3, double r4, double feather) {
        DrawContext ctx = new DrawContext(null, matrices);
        ctx.fill(x1, y1, x2, y2, color.getRGB());
    }

    public static void renderRoundedQuad(MatrixStack matrices, Color color,
                                         double x1, double y1, double x2, double y2,
                                         double r1, double r2, double r3) {
        DrawContext ctx = new DrawContext(null, matrices);
        ctx.fill((int)x1, (int)y1, (int)x2, (int)y2, color.getRGB());
    }

    public static void renderRoundedOutline(DrawContext context, Color color,
                                            int x1, int y1, int x2, int y2,
                                            double r1, double r2, double r3, double r4,
                                            double thickness, double feather) {
        int argb = color.getRGB();
        int t = (int) thickness;
        context.fill(x1, y1, x2, y1 + t, argb);
        context.fill(x1, y2 - t, x2, y2, argb);
        context.fill(x1, y1, x1 + t, y2, argb);
        context.fill(x2 - t, y1, x2, y2, argb);
    }

    public static void renderFilledBox(MatrixStack matrices,
                                       float x, float y, float z,
                                       float w, float h, float d,
                                       Color color) {
        // TODO: implement 3D box rendering
    }

    public static void renderLine(MatrixStack matrices, Color color,
                                  Vec3d a, Vec3d b) {
        // TODO: implement line rendering
    }
}
