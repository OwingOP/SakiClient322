package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.minecraft.client.util.Window;

public interface ResolutionListener extends Listener {
    void onResolution(ResolutionEvent paramResolutionEvent);

    public static class ResolutionEvent extends Event<ResolutionListener> {
        public Window window;

        public ResolutionEvent(Window window) {
            this.window = window;
        }

        public void fire(ArrayList<ResolutionListener> listeners) {
            listeners.forEach(l -> l.onResolution(this));
        }

        public Class<ResolutionListener> getListenerType() {
            return ResolutionListener.class;
        }
    }
}
