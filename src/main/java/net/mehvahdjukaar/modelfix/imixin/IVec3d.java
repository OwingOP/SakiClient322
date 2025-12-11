package net.mehvahdjukaar.modelfix.imixin;

import net.minecraft.class_2382;
import org.joml.Vector3d;

public interface IVec3d {
  void set(double paramDouble1, double paramDouble2, double paramDouble3);
  
  default void set(class_2382 vec) {
    set(vec.method_10263(), vec.method_10264(), vec.method_10260());
  }
  
  default void set(Vector3d vec) {
    set(vec.x, vec.y, vec.z);
  }
  
  void setXZ(double paramDouble1, double paramDouble2);
  
  void setY(double paramDouble);
}
