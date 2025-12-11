package net.mehvahdjukaar.modelfix.mixin;

import net.mehvahdjukaar.modelfix.addons.addon.render.NoBounce;
import net.mehvahdjukaar.modelfix.saki;
import net.mehvahdjukaar.modelfix.utils.CrystalUtils;
import net.mehvahdjukaar.modelfix.utils.RenderUtils;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1774;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1838;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1774.class})
public class EndCrystalItemMixin {
  @Unique
  private class_243 getPlayerLookVec(class_1657 p) {
    return RenderUtils.getPlayerLookVec(p);
  }
  
  @Unique
  private class_243 getClientLookVec() {
    assert saki.mc.field_1724 != null;
    return getPlayerLookVec((class_1657)saki.mc.field_1724);
  }
  
  @Unique
  private boolean isBlock(class_2248 b, class_2338 p) {
    return (getBlockState(p).method_26204() == b);
  }
  
  @Unique
  private class_2680 getBlockState(class_2338 p) {
    return saki.mc.field_1687.method_8320(p);
  }
  
  @Unique
  private boolean canPlaceCrystalServer(class_2338 blockPos) {
    class_2680 blockState = saki.mc.field_1687.method_8320(blockPos);
    if (!blockState.method_27852(class_2246.field_10540) && !blockState.method_27852(class_2246.field_9987))
      return false; 
    return CrystalUtils.canPlaceCrystalClientAssumeObsidian(blockPos);
  }
  
  @Inject(method = {"useOnBlock"}, at = {@At("HEAD")})
  private void onUse(class_1838 context, CallbackInfoReturnable<class_1269> cir) {
    NoBounce noBounce = (NoBounce)saki.INSTANCE.getModuleManager().getModule(NoBounce.class);
    if (noBounce.isEnabled() &&
            saki.INSTANCE != null && saki.mc.field_1724 != null) {
      class_1799 mainHandStack = saki.mc.field_1724.method_6047();
      if (mainHandStack.method_31574(class_1802.field_8301)) {
        class_243 e = saki.mc.field_1724.method_33571();
        class_3965 blockHit = saki.mc.field_1687.method_17742(new class_3959(e, e.method_1019(getClientLookVec().method_1021(4.5D)), class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)saki.mc.field_1724));
        if (isBlock(class_2246.field_10540, blockHit.method_17777()) || isBlock(class_2246.field_9987, blockHit.method_17777())) {
          class_239 hitResult = saki.mc.field_1765;
          if (hitResult instanceof class_3965) {
            class_3965 blockHit2 = (class_3965)hitResult;
            class_2338 pos = blockHit2.method_17777();
            if (canPlaceCrystalServer(pos))
              context.method_8041().method_7934(-1); 
          } 
        } 
      } 
    } 
  }
}
