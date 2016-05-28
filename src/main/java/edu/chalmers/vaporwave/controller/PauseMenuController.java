package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.PauseMenuView;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * A controller that initially was intended to hold a pause menu with optiones etc.
 * Sadly, this was removed due to lack of time, but we have kept the controller, if we should
 * want to implement the full pause menu further on.
 */
public class PauseMenuController {

    private PauseMenuView pauseMenuView;
    private List<Label> pauseMenuLabels;

    public PauseMenuController(Group root) {
        pauseMenuLabels = new ArrayList<>();
        pauseMenuLabels.add(new Label("[Game is paused!]"));

        pauseMenuView = new PauseMenuView(root, pauseMenuLabels);
    }

    public PauseMenuView getPauseMenuView() {
        return this.pauseMenuView;
    }
}
