package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.PauseMenuView;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class PauseMenuController {

    // todo: Is this needed?
    //private List<MenuButtonSprite> pauseMenuButtons;
   // private List<MenuButtonSprite> optionsMenuButtons;

    //chaning name to pauseMenuView
    private PauseMenuView pauseMenuView;

    //should be menubuttonview instead of labels when we've got sprites
    private List<Label> pauseMenuLabels;

    public PauseMenuController(Group root) {
        pauseMenuLabels = new ArrayList<>();
        // todo: Is this needed?
        //pauseMenuLabels.add(new Label("Resume"));
        //should be options
        pauseMenuLabels.add(new Label("[Game is paused!]"));
        //pauseMenuLabels.add(new Label("Quit"));

        pauseMenuView = new PauseMenuView(root, pauseMenuLabels);
    }

    public PauseMenuView getPauseMenuView() {
        return this.pauseMenuView;
    }
}
