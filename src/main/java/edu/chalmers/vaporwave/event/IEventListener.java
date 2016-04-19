package edu.chalmers.vaporwave.event;

/**
 * Created by FEngelbrektsson on 19/04/16.
 */
public interface IEventListener {
    /**
     * Various eventlisteners implements this interface, and its method.
     * @param event
     * @return boolean for testing purposes
     */
    Boolean eventFired(IEvent event);
}
