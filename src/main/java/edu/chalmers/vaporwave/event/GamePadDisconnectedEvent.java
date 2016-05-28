package edu.chalmers.vaporwave.event;

import net.java.games.input.Controller;

/**
 * This event is posted to the eventbus by InputController when gamepads are being
 * updated and one of them is found to be missing
 */
public class GamePadDisconnectedEvent {

    private Controller gamePad;

    public GamePadDisconnectedEvent(Controller gamePad) {
        this.gamePad = gamePad;
    }

    public Controller getGamePad() {
        return this.gamePad;
    }
}
