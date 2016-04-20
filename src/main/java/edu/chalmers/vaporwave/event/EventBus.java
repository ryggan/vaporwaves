package edu.chalmers.vaporwave.event;

import java.util.ArrayList;

/**
 * Created by FEngelbrektsson on 19/04/16.
 */
public enum EventBus {
    BUS;

    private final ArrayList<IEventListener> busSubscribers = new ArrayList<IEventListener>();

    /**
     * Subscribes a listener to the bus.
     * @param eventListener
     */
    public void subscribe(IEventListener eventListener) {
        busSubscribers.add(eventListener);
    }

    /**
     * Unsubscribes a listener from the bus.
     * @param eventListener
     */
    public void unsubscribe(IEventListener eventListener) {
        busSubscribers.remove(eventListener);
    }

    /**
     *
     * @return an ArrayList with IEventListeners that are subscribed to the bus.
     */
    public ArrayList<IEventListener> getSubscriberList() {
        return busSubscribers;
    }

    /**
     * Removes all IEventListeners from the bus.
     */

    public void clearBus() {
        busSubscribers.clear();
    }

    /**
     * To be continued...
     * @param event
     */

    public void postEvent(IEvent event) {
        for (IEventListener listener : busSubscribers) {
            listener.eventFired(event);
        }
    }

}
