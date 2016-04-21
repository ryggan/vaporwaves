package edu.chalmers.vaporwave.event;

import java.util.EventObject;

/**
 * Created by FEngelbrektsson on 19/04/16.
 */
public interface IEventListener {
    /**
     * Various eventlisteners implements this interface, and its method.
     * @param event
     * @return boolean for testing purposes
     */
    void eventFired(IEvent event);
}
