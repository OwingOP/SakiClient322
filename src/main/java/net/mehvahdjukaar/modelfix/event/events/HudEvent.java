package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.minecraft.client.gui.DrawContext;

public class HudEvent extends Event<HudListener> {
    public DrawContext context;

    public float delta;

    public HudEvent(DrawContext context, float delta) {
        this.context = context;
        this.delta = delta;
    }

    public void fire(ArrayList<HudListener> listeners) {
        listeners.forEach(e -> e.onRenderHud(this));
    }

    public Class<HudListener> getListenerType() {
        return HudListener.class;
    }
}
