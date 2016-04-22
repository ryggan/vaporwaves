package edu.chalmers.vaporwave.event;

import com.google.common.eventbus.*;

/**
 * Created by andreascarlsson on 2016-04-21.
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
