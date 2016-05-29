package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.PauseMenuView;
import javafx.scene.Group;

/**
 * A controller that initially was intended to hold a pause menu with optiones etc.
 * Sadly, this was removed due to lack of time, but we have kept the controller, if we should
 * want to implement the full pause menu further on.
 */
public class PauseMenuController {

    private PauseMenuView pauseMenuView;

    public PauseMenuController(Group root) {
        pauseMenuView = new PauseMenuView(root);
    }

    public PauseMenuView getPauseMenuView() {
        return this.pauseMenuView;
    }
}
