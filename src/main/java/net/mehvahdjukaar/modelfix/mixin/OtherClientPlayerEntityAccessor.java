package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.class_243;
import net.minecraft.class_745;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_745.class})
public interface OtherClientPlayerEntityAccessor {
  @Accessor("velocityLerpDivisor")
  int getVelocityLerpDivisor();
  
  @Accessor("velocityLerpDivisor")
  void setVelocityLerpDivisor(int paramInt);
  
  @Accessor("clientVelocity")
  class_243 getClientVelocity();
  
  @Accessor("clientVelocity")
  void setClientVelocity(class_243 paramclass_243);
}
