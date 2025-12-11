package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.mehvahdjukaar.modelfix.event.Listener;

public interface ItemUseListener extends Listener {
  void onItemUse(ItemUseEvent paramItemUseEvent);
  
  public static class ItemUseEvent extends CancellableEvent<ItemUseListener> {
    public void fire(ArrayList<ItemUseListener> listeners) {
      listeners.forEach(e -> e.onItemUse(this));
    }
    
    public Class<ItemUseListener> getListenerType() {
      return ItemUseListener.class;
    }
  }
}
