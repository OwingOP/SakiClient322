package net.mehvahdjukaar.modelfix.event;

class PrioritizedListener<L extends Listener> {
  private final L listener;
  
  private final int priority;
  
  public PrioritizedListener(L listener) {
    this(listener, 0);
  }
  
  public PrioritizedListener(L listener, int priority) {
    this.listener = listener;
    this.priority = priority;
  }
  
  public int getPriority() {
    return this.priority;
  }
  
  public L getListener() {
    return this.listener;
  }
}
