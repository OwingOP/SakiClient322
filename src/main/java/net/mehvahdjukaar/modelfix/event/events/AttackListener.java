package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.CancellableEvent;
import net.mehvahdjukaar.modelfix.event.Listener;

public interface AttackListener extends Listener {
  void onAttack(AttackEvent paramAttackEvent);
  
  public static class AttackEvent extends CancellableEvent<AttackListener> {
    public void fire(ArrayList<AttackListener> listeners) {
      listeners.forEach(e -> e.onAttack(this));
    }
    
    public Class<AttackListener> getListenerType() {
      return AttackListener.class;
    }
  }
}
