package net.mehvahdjukaar.modelfix.mixin;

import net.minecraft.class_4604;
import org.joml.FrustumIntersection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_4604.class})
public interface FrustumAccessor {
  @Accessor
  FrustumIntersection getFrustumIntersection();
  
  @Accessor
  void setFrustumIntersection(FrustumIntersection paramFrustumIntersection);
  
  @Accessor("x")
  double getX();
  
  @Accessor("x")
  void setX(double paramDouble);
  
  @Accessor("y")
  double getY();
  
  @Accessor("y")
  void setY(double paramDouble);
  
  @Accessor("z")
  double getZ();
  
  @Accessor("z")
  void setZ(double paramDouble);
}
