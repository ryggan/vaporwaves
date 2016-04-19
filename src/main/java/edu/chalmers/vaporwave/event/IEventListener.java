package edu.chalmers.vaporwave.event;

/**
 * Created by FEngelbrektsson on 19/04/16.
 */
public interface IEventListener {
    /**
     * Various eventlisteners implements this interface, and its method.
     * @param event
     */
    void eventFired(IEvent event);
}
