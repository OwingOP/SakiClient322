package net.mehvahdjukaar.modelfix.utils;

import java.util.Random;

public final class MathUtils {
  public static Random random = new Random(System.currentTimeMillis());
  
  public static double roundToDecimal(double n, double point) {
    return point * Math.round(n / point);
  }
  
  public static int randomInt(int start, int bound) {
    return random.nextInt(start, bound);
  }
  
  public static double smoothStepLerp(double delta, double start, double end) {
    delta = Math.max(0.0D, Math.min(1.0D, delta));
    double t = delta * delta * (3.0D - 2.0D * delta);
    double value = start + (end - start) * t;
    return value;
  }
  
  public static double goodLerp(float delta, double start, double end) {
    int step = (int)Math.ceil(Math.abs(end - start) * delta);
    if (start < end)
      return Math.min(start + step, end); 
    return Math.max(start - step, end);
  }
  
  public static float randomFloat(float v, float v1) {
    return 0.0F;
  }
}
