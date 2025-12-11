package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.class_4604;
import net.minecraft.class_761;
import net.minecraft.class_769;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_761.class})
public interface WorldRendererAccessor {
  @Accessor("chunks")
  class_769 getChunks();
  
  @Accessor
  class_4604 getFrustum();
  
  @Accessor
  void setFrustum(class_4604 paramclass_4604);
}
