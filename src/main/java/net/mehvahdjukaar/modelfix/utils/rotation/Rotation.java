package net.mehvahdjukaar.modelfix.utils.rotation;

public final class Rotation extends Record {
  private final double yaw;
  
  private final double pitch;
  
  public Rotation(double yaw, double pitch) {
    this.yaw = yaw;
    this.pitch = pitch;
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lnet/mehvahdjukaar/modelfix/utils/rotation/Rotation;)Ljava/lang/String;
    //   6: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #4	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	7	0	this	Lnet/mehvahdjukaar/modelfix/utils/rotation/Rotation;
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lnet/mehvahdjukaar/modelfix/utils/rotation/Rotation;)I
    //   6: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #4	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	7	0	this	Lnet/mehvahdjukaar/modelfix/utils/rotation/Rotation;
  }
  
  public final boolean equals(Object o) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lnet/mehvahdjukaar/modelfix/utils/rotation/Rotation;Ljava/lang/Object;)Z
    //   7: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #4	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	8	0	this	Lnet/mehvahdjukaar/modelfix/utils/rotation/Rotation;
    //   0	8	1	o	Ljava/lang/Object;
  }
  
  public double yaw() {
    return this.yaw;
  }
  
  public double pitch() {
    return this.pitch;
  }
}
