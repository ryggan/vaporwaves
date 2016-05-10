package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.MenuButtonView;
import edu.chalmers.vaporwave.view.PauseMenu;
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
    private PauseMenu pauseMenuView;

    //should be menubuttonview instead of labels when we've got sprites
    private List<Label> pauseMenuLabels;

    public PauseMenuController(Group root) {
        pauseMenuLabels = new ArrayList<>();
        pauseMenuLabels.add(new Label("Resume"));
        pauseMenuLabels.add(new Label("Options"));
        pauseMenuLabels.add(new Label("Quit"));

        pauseMenuView = new PauseMenu(root, pauseMenuLabels);
    }

    public PauseMenu getPauseMenuView() {
        return this.pauseMenuView;
    }
}
