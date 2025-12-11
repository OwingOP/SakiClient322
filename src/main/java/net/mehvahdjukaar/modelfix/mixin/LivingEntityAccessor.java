package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.class_1282;
import net.minecraft.class_1309;
import net.minecraft.class_2338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1309.class})
public interface LivingEntityAccessor {
  @Accessor
  boolean getJumping();
  
  @Accessor("lastDamageSource")
  class_1282 getLastDamageSource();
  
  @Accessor("lastDamageSource")
  void setLastDamageSource(class_1282 paramclass_1282);
  
  @Accessor("lastDamageTime")
  long getLastDamageTime();
  
  @Accessor("lastDamageTime")
  void setLastDamageTime(long paramLong);
  
  @Accessor("lastBlockPos")
  class_2338 getLastBlockPos();
  
  @Accessor("lastBlockPos")
  void setLastBlockPos(class_2338 paramclass_2338);
  
  @Accessor("attacking")
  void setAttacking(class_1309 paramclass_1309);
}
