package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.menu.StartMenu;
import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class MenuController {

    private MenuView menuView;
    private StartMenu startMenu=StartMenu.getInstance();

    public MenuController(Group root) {
        menuView = new MenuView(root);
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        ArrayList<String> input = ListenerController.getInstance().getInput();

            if (input.contains("UP")) {
                startMenu.changeSelected("UP");
            } else if (input.contains("DOWN")) {
                startMenu.changeSelected("DOWN");
            }


    }

    public void newGame() {

    }

}
