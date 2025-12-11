package net.mehvahdjukaar.modelfix.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_5944;
import net.minecraft.class_757;
import net.minecraft.class_7833;
import net.minecraft.class_8251;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
  public static class_8251 vertexSorter;
  
  public static boolean rendering3D = true;
  
  public static class_243 getCameraPos() {
    return (saki.mc.method_31975()).field_4344.method_19326();
  }
  
  public static double deltaTime() {
    return (saki.mc.method_47599() > 0) ? (1.0D / saki.mc.method_47599()) : 1.0D;
  }
  
  public static float fast(float end, float start, float multiple) {
    return (1.0F - class_3532.method_15363((float)(deltaTime() * multiple), 0.0F, 1.0F)) * end + class_3532.method_15363((float)(deltaTime() * multiple), 0.0F, 1.0F) * start;
  }
  
  public static class_243 getPlayerLookVec(class_1657 player) {
    float f = 0.017453292F;
    float pi = 3.1415927F;
    float f1 = class_3532.method_15362(-player.method_36454() * f - pi);
    float f2 = class_3532.method_15374(-player.method_36454() * f - pi);
    float f3 = -class_3532.method_15362(-player.method_36455() * f);
    float f4 = class_3532.method_15374(-player.method_36455() * f);
    return (new class_243((f2 * f3), f4, (f1 * f3))).method_1029();
  }
  
  public static void unscaledProjection() {
    vertexSorter = RenderSystem.getVertexSorting();
    RenderSystem.setProjectionMatrix((new Matrix4f()).setOrtho(0.0F, saki.mc.method_22683().method_4489(), saki.mc.method_22683().method_4506(), 0.0F, 1000.0F, 21000.0F), class_8251.field_43361);
    rendering3D = false;
  }
  
  public static void scaledProjection() {
    RenderSystem.setProjectionMatrix((new Matrix4f()).setOrtho(0.0F, (float)(saki.mc.method_22683().method_4489() / saki.mc.method_22683().method_4495()), (float)(saki.mc.method_22683().method_4506() / saki.mc.method_22683().method_4495()), 0.0F, 1000.0F, 21000.0F), vertexSorter);
    rendering3D = true;
  }
  
  public static void renderRoundedQuad(class_4587 matrices, Color c, double x, double y, double x2, double y2, double corner1, double corner2, double corner3, double corner4, double samples) {
    int color = c.getRGB();
    Matrix4f matrix = matrices.method_23760().method_23761();
    float f = (color >> 24 & 0xFF) / 255.0F;
    float g = (color >> 16 & 0xFF) / 255.0F;
    float h = (color >> 8 & 0xFF) / 255.0F;
    float k = (color & 0xFF) / 255.0F;
    RenderSystem.enableBlend();
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShader(class_757::method_34540);
    renderRoundedQuadInternal(matrix, g, h, k, f, x, y, x2, y2, corner1, corner2, corner3, corner4, samples);
    RenderSystem.enableCull();
    RenderSystem.disableBlend();
  }
  
  private static void setup() {
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
  }
  
  private static void cleanup() {
    RenderSystem.enableCull();
    RenderSystem.disableBlend();
  }
  
  public static void renderRoundedQuad(class_4587 matrices, Color c, double x, double y, double x1, double y1, double rad, double samples) {
    renderRoundedQuad(matrices, c, x, y, x1, y1, rad, rad, rad, rad, samples);
  }
  
  public static void renderRoundedOutlineInternal(Matrix4f matrix, float cr, float cg, float cb, float ca, double fromX, double fromY, double toX, double toY, double radC1, double radC2, double radC3, double radC4, double width, double samples) {
    class_287 bufferBuilder = class_289.method_1348().method_60827(class_293.class_5596.field_27380, class_290.field_1576);
    double[][] map = { { toX - radC4, toY - radC4, radC4 }, { toX - radC2, fromY + radC2, radC2 }, { fromX + radC1, fromY + radC1, radC1 }, { fromX + radC3, toY - radC3, radC3 } };
    int i;
    for (i = 0; i < 4; i++) {
      double[] arrayOfDouble = map[i];
      double d1 = arrayOfDouble[2];
      double r;
      for (r = i * 90.0D; r < 90.0D + i * 90.0D; r += 90.0D / samples) {
        float f2 = (float)Math.toRadians(r);
        double d2 = Math.sin(f2);
        float f3 = (float)(d2 * d1);
        double d3 = Math.cos(f2);
        float f4 = (float)(d3 * d1);
        bufferBuilder.method_22918(matrix, (float)arrayOfDouble[0] + f3, (float)arrayOfDouble[1] + f4, 0.0F).method_22915(cr, cg, cb, ca);
        bufferBuilder.method_22918(matrix, (float)(arrayOfDouble[0] + f3 + d2 * width), (float)(arrayOfDouble[1] + f4 + d3 * width), 0.0F).method_22915(cr, cg, cb, ca);
      } 
      float rad1 = (float)Math.toRadians(90.0D + i * 90.0D);
      double sin1 = Math.sin(rad1);
      float sin = (float)(sin1 * d1);
      double cos1 = Math.cos(rad1);
      float f1 = (float)(cos1 * d1);
      bufferBuilder.method_22918(matrix, (float)arrayOfDouble[0] + sin, (float)arrayOfDouble[1] + f1, 0.0F).method_22915(cr, cg, cb, ca);
      bufferBuilder.method_22918(matrix, (float)(arrayOfDouble[0] + sin + sin1 * width), (float)(arrayOfDouble[1] + f1 + cos1 * width), 0.0F).method_22915(cr, cg, cb, ca);
    } 
    i = 0;
    double[] current = map[i];
    double rad = current[2];
    float cos = (float)rad;
    bufferBuilder.method_22918(matrix, (float)current[0], (float)current[1] + cos, 0.0F).method_22915(cr, cg, cb, ca);
    bufferBuilder.method_22918(matrix, (float)current[0], (float)(current[1] + cos + width), 0.0F).method_22915(cr, cg, cb, ca);
    class_286.method_43433(bufferBuilder.method_60800());
  }
  
  public static void setScissorRegion(int x, int y, int width, int height) {
    int screenHeight;
    class_437 currentScreen = (class_310.method_1551()).field_1755;
    if (currentScreen == null) {
      screenHeight = 0;
    } else {
      screenHeight = currentScreen.field_22790 - height;
    } 
    double scaleFactor = class_310.method_1551().method_22683().method_4495();
    GL11.glScissor((int)(x * scaleFactor), (int)(screenHeight * scaleFactor), (int)((width - x) * scaleFactor), (int)((height - y) * scaleFactor));
    GL11.glEnable(3089);
  }
  
  public static void renderCircle(class_4587 matrices, Color c, double originX, double originY, double rad, int segments) {
    int segments1 = class_3532.method_15340(segments, 4, 360);
    int color = c.getRGB();
    Matrix4f matrix = matrices.method_23760().method_23761();
    float f = (color >> 24 & 0xFF) / 255.0F;
    float g = (color >> 16 & 0xFF) / 255.0F;
    float h = (color >> 8 & 0xFF) / 255.0F;
    float k = (color & 0xFF) / 255.0F;
    setup();
    RenderSystem.setShader(class_757::method_34540);
    class_287 bufferBuilder = class_289.method_1348().method_60827(class_293.class_5596.field_27381, class_290.field_1576);
    int i;
    for (i = 0; i < 360; i += Math.min(360 / segments1, 360 - i)) {
      double radians = Math.toRadians(i);
      double sin = Math.sin(radians) * rad;
      double cos = Math.cos(radians) * rad;
      bufferBuilder.method_22918(matrix, (float)(originX + sin), (float)(originY + cos), 0.0F).method_22915(g, h, k, f);
    } 
    class_286.method_43433(bufferBuilder.method_60800());
    cleanup();
  }
  
  public static void renderShaderRect(class_4587 matrixStack, Color color, Color color2, Color color3, Color color4, float f, float f2, float f3, float f4, float f5, float f6) {
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    class_289 tessellator = class_289.method_1348();
    class_287 bufferBuilder = tessellator.method_60827(class_293.class_5596.field_27382, class_290.field_1592);
    bufferBuilder.method_22918(matrixStack.method_23760().method_23761(), f - 10.0F, f2 - 10.0F, 0.0F);
    bufferBuilder.method_22918(matrixStack.method_23760().method_23761(), f - 10.0F, f2 + f4 + 20.0F, 0.0F);
    bufferBuilder.method_22918(matrixStack.method_23760().method_23761(), f + f3 + 20.0F, f2 + f4 + 20.0F, 0.0F);
    bufferBuilder.method_22918(matrixStack.method_23760().method_23761(), f + f3 + 20.0F, f2 - 10.0F, 0.0F);
    class_286.method_43433(bufferBuilder.method_60800());
    RenderSystem.disableBlend();
  }
  
  public static void renderRoundedOutline(class_332 poses, Color c, double fromX, double fromY, double toX, double toY, double rad1, double rad2, double rad3, double rad4, double width, double samples) {
    int color = c.getRGB();
    Matrix4f matrix = poses.method_51448().method_23760().method_23761();
    float f = (color >> 24 & 0xFF) / 255.0F;
    float g = (color >> 16 & 0xFF) / 255.0F;
    float h = (color >> 8 & 0xFF) / 255.0F;
    float k = (color & 0xFF) / 255.0F;
    setup();
    RenderSystem.setShader(class_757::method_34540);
    renderRoundedOutlineInternal(matrix, g, h, k, f, fromX, fromY, toX, toY, rad1, rad2, rad3, rad4, width, samples);
    cleanup();
  }
  
  public static class_4587 matrixFrom(double x, double y, double z) {
    class_4587 matrices = new class_4587();
    class_4184 camera = (class_310.method_1551()).field_1773.method_19418();
    matrices.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
    matrices.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
    matrices.method_22904(x - (camera.method_19326()).field_1352, y - (camera.method_19326()).field_1351, z - (camera.method_19326()).field_1350);
    return matrices;
  }
  
  public static void renderQuad(class_4587 matrices, float x, float y, float width, float height, int color) {
    float alpha = (color >> 24 & 0xFF) / 255.0F;
    float red = (color >> 16 & 0xFF) / 255.0F;
    float green = (color >> 8 & 0xFF) / 255.0F;
    float blue = (color & 0xFF) / 255.0F;
    matrices.method_22903();
    matrices.method_22905(0.5F, 0.5F, 0.5F);
    matrices.method_22904(x, y, 0.0D);
    class_289 tessellator = class_289.method_1348();
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();
    class_287 bufferBuilder = tessellator.method_60827(class_293.class_5596.field_27382, class_290.field_1576);
    bufferBuilder.method_22912(0.0F, 0.0F, 0.0F).method_22915(red, green, blue, alpha);
    bufferBuilder.method_22912(0.0F, height, 0.0F).method_22915(red, green, blue, alpha);
    bufferBuilder.method_22912(width, height, 0.0F).method_22915(red, green, blue, alpha);
    bufferBuilder.method_22912(width, 0.0F, 0.0F).method_22915(red, green, blue, alpha);
    class_286.method_43433(bufferBuilder.method_60800());
    RenderSystem.disableBlend();
    matrices.method_22909();
  }
  
  public static void renderRoundedQuadInternal(Matrix4f matrix, float cr, float cg, float cb, float ca, double fromX, double fromY, double toX, double toY, double corner1, double corner2, double corner3, double corner4, double samples) {
    class_287 bufferBuilder = class_289.method_1348().method_60827(class_293.class_5596.field_27381, class_290.field_1576);
    double[][] map = { { toX - corner4, toY - corner4, corner4 }, { toX - corner2, fromY + corner2, corner2 }, { fromX + corner1, fromY + corner1, corner1 }, { fromX + corner3, toY - corner3, corner3 } };
    for (int i = 0; i < 4; i++) {
      double[] current = map[i];
      double rad = current[2];
      double r;
      for (r = i * 90.0D; r < 90.0D + i * 90.0D; r += 90.0D / samples) {
        float f1 = (float)Math.toRadians(r);
        float f2 = (float)(Math.sin(f1) * rad);
        float f3 = (float)(Math.cos(f1) * rad);
        bufferBuilder.method_22918(matrix, (float)current[0] + f2, (float)current[1] + f3, 0.0F).method_22915(cr, cg, cb, ca);
      } 
      float rad1 = (float)Math.toRadians(90.0D + i * 90.0D);
      float sin = (float)(Math.sin(rad1) * rad);
      float cos = (float)(Math.cos(rad1) * rad);
      bufferBuilder.method_22918(matrix, (float)current[0] + sin, (float)current[1] + cos, 0.0F).method_22915(cr, cg, cb, ca);
    } 
    class_286.method_43433(bufferBuilder.method_60800());
  }
  
  public static void renderFilledBox(class_4587 matrices, float f, float f2, float f3, float f4, float f5, float f6, Color color) {
    RenderSystem.enableBlend();
    RenderSystem.disableDepthTest();
    RenderSystem.setShaderColor(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
    RenderSystem.setShader(class_757::method_34539);
    class_289 tessellator = class_289.method_1348();
    class_287 bufferBuilder = tessellator.method_60827(class_293.class_5596.field_27380, class_290.field_1592);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f2, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f3);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f6);
    bufferBuilder.method_22918(matrices.method_23760().method_23761(), f4, f5, f6);
    class_286.method_43433(bufferBuilder.method_60800());
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.enableDepthTest();
    RenderSystem.disableBlend();
  }
  
  public static void renderLine(class_4587 matrices, Color color, class_243 start, class_243 end) {
    matrices.method_22903();
    Matrix4f s = matrices.method_23760().method_23761();
    if (ClickGUI.antiAliasing.getValue()) {
      GL11.glEnable(32925);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
    } 
    GL11.glDepthFunc(519);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.defaultBlendFunc();
    RenderSystem.enableBlend();
    genericAABBRender(class_293.class_5596.field_29344, class_290.field_1576, class_757::method_34540, s, start, end
        
        .method_1020(start), color, (buffer, x, y, z, x1, y1, z1, red, green, blue, alpha, matrix) -> {
          buffer.method_22918(matrix, x, y, z).method_22915(red, green, blue, alpha);
          buffer.method_22918(matrix, x1, y1, z1).method_22915(red, green, blue, alpha);
        });
    GL11.glDepthFunc(515);
    RenderSystem.disableBlend();
    if (ClickGUI.antiAliasing.getValue()) {
      GL11.glDisable(2848);
      GL11.glDisable(32925);
    } 
    matrices.method_22909();
  }
  
  private static void genericAABBRender(class_293.class_5596 mode, class_293 format, Supplier<class_5944> shader, Matrix4f stack, class_243 start, class_243 dimensions, Color color, RenderAction action) {
    float red = color.getRed() / 255.0F;
    float green = color.getGreen() / 255.0F;
    float blue = color.getBlue() / 255.0F;
    float alpha = color.getAlpha() / 255.0F;
    class_243 end = start.method_1019(dimensions);
    float x1 = (float)start.field_1352;
    float y1 = (float)start.field_1351;
    float z1 = (float)start.field_1350;
    float x2 = (float)end.field_1352;
    float y2 = (float)end.field_1351;
    float z2 = (float)end.field_1350;
    useBuffer(mode, format, shader, bufferBuilder -> action.run(bufferBuilder, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, stack));
  }
  
  private static void useBuffer(class_293.class_5596 mode, class_293 format, Supplier<class_5944> shader, Consumer<class_287> runner) {
    class_289 t = class_289.method_1348();
    class_287 bb = t.method_60827(mode, format);
    runner.accept(bb);
    setup();
    RenderSystem.setShader(shader);
    class_286.method_43433(bb.method_60800());
    cleanup();
  }
  
  static interface RenderAction {
    void run(class_287 param1class_287, float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6, float param1Float7, float param1Float8, float param1Float9, float param1Float10, Matrix4f param1Matrix4f);
  }
}
