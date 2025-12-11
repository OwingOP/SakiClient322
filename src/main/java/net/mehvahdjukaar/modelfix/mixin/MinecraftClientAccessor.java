package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.class_310;
import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_310.class})
public interface MinecraftClientAccessor {
  @Accessor
  class_312 getMouse();
  
  @Invoker
  void invokeDoItemUse();
  
  @Invoker
  boolean invokeDoAttack();
  
  @Accessor("itemUseCooldown")
  void setItemUseCooldown(int paramInt);
}
