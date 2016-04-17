package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class ArenaController {

    private ArenaView av;

    public ArenaController(Group root) {

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
