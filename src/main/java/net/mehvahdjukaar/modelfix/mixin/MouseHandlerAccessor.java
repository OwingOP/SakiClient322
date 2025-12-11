package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_312.class})
public interface MouseHandlerAccessor {
  @Invoker("onMouseButton")
  void press(long paramLong, int paramInt1, int paramInt2, int paramInt3);
}
