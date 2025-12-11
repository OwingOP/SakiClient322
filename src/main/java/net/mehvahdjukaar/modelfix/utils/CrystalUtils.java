package net.mehvahdjukaar.modelfix.utils;

import java.util.List;
import net.mehvahdjukaar.modelfix.saki;
import net.minecraft.class_1297;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2680;

public final class CrystalUtils {
  public static boolean canPlaceCrystalClient(class_2338 block) {
    class_2680 blockState = saki.mc.field_1687.method_8320(block);
    if (!blockState.method_27852(class_2246.field_10540) && !blockState.method_27852(class_2246.field_9987))
      return false; 
    return canPlaceCrystalClientAssumeObsidian(block);
  }
  
  public static boolean canPlaceCrystalClientAssumeObsidian(class_2338 block) {
    class_2338 blockPos2 = block.method_10084();
    if (!saki.mc.field_1687.method_22347(blockPos2))
      return false; 
    double d = blockPos2.method_10263();
    double e = blockPos2.method_10264();
    double f = blockPos2.method_10260();
    List<class_1297> list = saki.mc.field_1687.method_8335(null, new class_238(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
    return list.isEmpty();
  }
  
  public static boolean canPlaceCrystalServer(class_2338 pos) {
    class_2680 blockState = saki.mc.field_1687.method_8320(pos);
    if (!blockState.method_27852(class_2246.field_10540) || !blockState.method_27852(class_2246.field_9987))
      return false; 
    class_2338 blockPos = pos.method_10084();
    if (!saki.mc.field_1687.method_22347(blockPos))
      return false; 
    double d = blockPos.method_10263();
    double e = blockPos.method_10264();
    double f = blockPos.method_10260();
    List<class_1297> list = saki.mc.field_1687.method_8335(null, new class_238(d, e, f, d + 1.0D, e + 2.0D, f + 1.0D));
    return list.isEmpty();
  }
}
