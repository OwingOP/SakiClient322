package net.mehvahdjukaar.modelfix.event;

import java.util.ArrayList;

public abstract class Event<T extends Listener> {
  public abstract void fire(ArrayList<T> paramArrayList);
  
  public abstract Class<T> getListenerType();
}
