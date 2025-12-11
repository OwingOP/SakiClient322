package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.mehvahdjukaar.modelfix.event.Listener;

public interface BlockBreakingListener extends Listener {
  void onBlockBreaking(BlockBreakingEvent paramBlockBreakingEvent);
  
  public static class BlockBreakingEvent extends CancellableEvent<BlockBreakingListener> {
    public void fire(ArrayList<BlockBreakingListener> listeners) {
      listeners.forEach(e -> e.onBlockBreaking(this));
    }
    
    public Class<BlockBreakingListener> getListenerType() {
      return BlockBreakingListener.class;
    }
  }
}
