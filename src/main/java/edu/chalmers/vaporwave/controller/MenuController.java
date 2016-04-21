package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

import java.util.ArrayList;

public class MenuController {

    private MenuView menuView;

    public MenuController(Group root) {
        menuView = new MenuView(root);
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall, ArrayList<String> input) {

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            if (key.equals("UP") || key.equals("DOWN")) {
                menuView.changeSelected(key);
            }
        }

    }
}
