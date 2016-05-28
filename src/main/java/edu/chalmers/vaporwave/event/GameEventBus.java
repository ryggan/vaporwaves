package edu.chalmers.vaporwave.event;

import com.google.common.eventbus.*;

/**
 * This is a simple singleton class, to encapsule the google eventbus we want to use.
 */
public class GameEventBus extends EventBus {
    private static GameEventBus eventBus;

    private GameEventBus() { }

    public static synchronized EventBus getInstance() {
        if (eventBus == null) {
            eventBus = new GameEventBus();
        }
        return eventBus;
    }
}
