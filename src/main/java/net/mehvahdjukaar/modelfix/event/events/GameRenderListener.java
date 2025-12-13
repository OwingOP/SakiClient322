package net.mehvahdjukaar.modelfix.event.events;

import java.util.ArrayList;
import net.mehvahdjukaar.modelfix.event.Event;
import net.mehvahdjukaar.modelfix.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

public interface GameRenderListener extends Listener {
    void onGameRender(GameRenderEvent paramGameRenderEvent);

    public static class GameRenderEvent extends Event<GameRenderListener> {
        public MatrixStack matrices;

        public float delta;

        public GameRenderEvent(MatrixStack matrices, float delta) {
            this.matrices = matrices;
            this.delta = delta;
        }

        public void fire(ArrayList<GameRenderListener> listeners) {
            listeners.forEach(e -> e.onGameRender(this));
        }

        public Class<GameRenderListener> getListenerType() {
            return GameRenderListener.class;
        }
    }
}
