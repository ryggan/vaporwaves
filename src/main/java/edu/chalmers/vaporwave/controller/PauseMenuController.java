package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.MenuButtonView;
import edu.chalmers.vaporwave.view.PauseMenuView;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FEngelbrektsson on 10/05/16.
 */
public class PauseMenuController {

    private List<MenuButtonView> pauseMenuButtons;
    private List<MenuButtonView> optionsMenuButtons;

    //chaning name to pauseMenuView
    private PauseMenuView pauseMenuView;

    //should be menubuttonview instead of labels when we've got sprites
    private List<Label> pauseMenuLabels;

    public PauseMenuController(Group root) {
        pauseMenuLabels = new ArrayList<>();
        pauseMenuLabels.add(new Label("Resume"));
        //should be options
        pauseMenuLabels.add(new Label("[Game is paused!]"));
        pauseMenuLabels.add(new Label("Quit"));

        pauseMenuView = new PauseMenuView(root, pauseMenuLabels);
    }

    public PauseMenuView getPauseMenuView() {
        return this.pauseMenuView;
    }
}
