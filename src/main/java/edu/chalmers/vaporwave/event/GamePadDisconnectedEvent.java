package edu.chalmers.vaporwave.event;

import net.java.games.input.Controller;

public class GamePadDisconnectedEvent {

    private Controller gamePad;

    public GamePadDisconnectedEvent(Controller gamePad) {
        this.gamePad = gamePad;
    }

    public Controller getGamePad() {
        return this.gamePad;
    }
}
