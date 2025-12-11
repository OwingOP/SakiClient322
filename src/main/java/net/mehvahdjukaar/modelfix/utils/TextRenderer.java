package net.mehvahdjukaar.modelfix.utils;

import net.mehvahdjukaar.modelfix.addons.addon.client.ClickGUI;
import net.mehvahdjukaar.modelfix.font.Fonts;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_332;
import net.minecraft.class_4587;

public final class TextRenderer {
  public static void drawString(CharSequence string, class_332 context, int x, int y, int color) {
    boolean custom = ClickGUI.customFont.getValue();
    if (custom) {
      Fonts.QUICKSAND.drawString(context.method_51448(), string, x, (y - 8), color);
    } else {
      drawMinecraftText(string, context, x, y, color);
    } 
  }
  
  public static int getWidth(CharSequence string) {
    boolean custom = ClickGUI.customFont.getValue();
    if (custom)
      return Fonts.QUICKSAND.getStringWidth(string); 
    return saki.mc.field_1772.method_1727(string.toString()) * 2;
  }
  
  public static void drawCenteredString(CharSequence string, class_332 context, int x, int y, int color) {
    boolean custom = ClickGUI.customFont.getValue();
    if (custom) {
      Fonts.QUICKSAND.drawString(context.method_51448(), string, (x - Fonts.QUICKSAND.getStringWidth(string) / 2), (y - 8), color);
    } else {
      drawCenteredMinecraftText(string, context, x, y, color);
    } 
  }
  
  public static void drawLargeString(CharSequence string, class_332 context, int x, int y, int color) {
    boolean custom = ClickGUI.customFont.getValue();
    if (custom) {
      class_4587 matrices = context.method_51448();
      matrices.method_22903();
      matrices.method_22905(1.4F, 1.4F, 1.4F);
      Fonts.QUICKSAND.drawString(context.method_51448(), string, x, (y - 8), color);
      matrices.method_22905(1.0F, 1.0F, 1.0F);
      matrices.method_22909();
    } else {
      drawLargerMinecraftText(string, context, x, y, color);
    } 
  }
  
  public static void drawMinecraftText(CharSequence string, class_332 context, int x, int y, int color) {
    class_4587 matrices = context.method_51448();
    matrices.method_22903();
    matrices.method_22905(2.0F, 2.0F, 2.0F);
    context.method_51433(saki.mc.field_1772, string.toString(), x / 2, y / 2, color, false);
    matrices.method_22905(1.0F, 1.0F, 1.0F);
    matrices.method_22909();
  }
  
  public static void drawLargerMinecraftText(CharSequence string, class_332 context, int x, int y, int color) {
    class_4587 matrices = context.method_51448();
    matrices.method_22903();
    matrices.method_22905(3.0F, 3.0F, 3.0F);
    context.method_51433(saki.mc.field_1772, (String)string, x / 3, y / 3, color, false);
    matrices.method_22905(1.0F, 1.0F, 1.0F);
    matrices.method_22909();
  }
  
  public static void drawCenteredMinecraftText(CharSequence string, class_332 context, int x, int y, int color) {
    class_4587 matrices = context.method_51448();
    matrices.method_22903();
    matrices.method_22905(2.0F, 2.0F, 2.0F);
    context.method_51433(saki.mc.field_1772, (String)string, x / 2 - saki.mc.field_1772.method_1727((String)string) / 2, y / 2, color, false);
    matrices.method_22905(1.0F, 1.0F, 1.0F);
    matrices.method_22909();
  }
}
