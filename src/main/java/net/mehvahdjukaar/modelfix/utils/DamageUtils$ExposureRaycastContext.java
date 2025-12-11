package net.mehvahdjukaar.modelfix.utils;

import net.minecraft.class_243;

public final class ExposureRaycastContext extends Record {
  private final class_243 start;
  
  private final class_243 end;
  
  public ExposureRaycastContext(class_243 start, class_243 end) {
    this.start = start;
    this.end = end;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lnet/mehvahdjukaar/modelfix/utils/DamageUtils$ExposureRaycastContext;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #324	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	7	0	this	Lnet/mehvahdjukaar/modelfix/utils/DamageUtils$ExposureRaycastContext;
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lnet/mehvahdjukaar/modelfix/utils/DamageUtils$ExposureRaycastContext;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #324	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	7	0	this	Lnet/mehvahdjukaar/modelfix/utils/DamageUtils$ExposureRaycastContext;
  }
  
  public final boolean equals(Object o) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lnet/mehvahdjukaar/modelfix/utils/DamageUtils$ExposureRaycastContext;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #324	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	8	0	this	Lnet/mehvahdjukaar/modelfix/utils/DamageUtils$ExposureRaycastContext;
    //   0	8	1	o	Ljava/lang/Object;
  }
  
  public class_243 start() {
    return this.start;
  }
  
  public class_243 end() {
    return this.end;
  }
}
