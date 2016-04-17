package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class ArenaController {

    private ArenaView av;

    public ArenaController(Group root) {
        av = new ArenaView(root);
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

    }
}
