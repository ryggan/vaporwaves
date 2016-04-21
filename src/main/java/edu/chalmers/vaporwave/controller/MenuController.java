package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.model.menu.StartMenu;
import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class MenuController {

    private MenuView menuView;
    private MenuState menuState;

    public MenuController(Group root) {
        menuView = new MenuView(root);
        menuState=StartMenu.getInstance();
        menuView.setMenuState(menuState);

        GameEventBus.getInstance().post(new NewGameEvent());
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        ArrayList<String> input = ListenerController.getInstance().getInput();

            if (input.contains("UP")) {
                menuState.changeSelected("UP");
                menuView.update("UP");
            } else if (input.contains("DOWN")) {
                menuState.changeSelected("DOWN");
                menuView.update("DOWN");
            }


    }

    public void newGame() {

    }

}
