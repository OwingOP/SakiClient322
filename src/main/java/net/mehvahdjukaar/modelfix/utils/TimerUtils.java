package net.mehvahdjukaar.modelfix.utils;

public final class TimerUtils {
  private long lastMS;
  
  public TimerUtils() {
    reset();
  }
  
  public long getCurrentMS() {
    return System.nanoTime() / 1000000L;
  }
  
  public boolean hasReached(double milliseconds) {
    return ((getCurrentMS() - this.lastMS) >= milliseconds);
  }
  
  public void reset() {
    this.lastMS = getCurrentMS();
  }
  
  public boolean delay(float milliSec) {
    return ((float)(getTime() - this.lastMS) >= milliSec);
  }
  
  public long getTime() {
    return System.nanoTime() / 1000000L;
  }
}
