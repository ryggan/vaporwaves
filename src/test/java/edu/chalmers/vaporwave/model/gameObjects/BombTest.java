package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.event.EventBus;
import edu.chalmers.vaporwave.event.IEvent;
import edu.chalmers.vaporwave.event.IEventListener;
import edu.chalmers.vaporwave.model.gameObjects.Bomb;
import edu.chalmers.*;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 19/04/16.
 */
public class BombTest implements IEventListener {

    @Test
    public void testSubscrive() {
        /**
         * Put a pin in this fucking test
         */
        EventBus.BUS.subscribe(this);
        Bomb bomb = new Bomb(10, 3);

        eventFired(bomb);


    }

    public void eventFired(IEvent event) {
        assertTrue(bomb.fireEvent() == true);
    }

}