package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

public class GameController {

    private ArenaView av;

    public GameController(Group root) {

        // Initiates view

        av = new ArenaView(root);
    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        // Calls view to update graphics
        av.updateView(timeSinceStart, timeSinceLastCall);

        // aaaall the game logicz
    }
}
