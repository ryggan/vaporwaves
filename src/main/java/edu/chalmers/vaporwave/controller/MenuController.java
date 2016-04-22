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

    private boolean upKeyPressed = false;
    private boolean downKeyPressed = false;

    public MenuController(Group root) {
        menuView = new MenuView(root);
        menuState=StartMenu.getInstance();
        menuView.setMenuState(menuState);

        GameEventBus.getInstance().post(new NewGameEvent());
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        List<String> input = ListenerController.getInstance().getInput();


            if (input.contains("UP") && !upKeyPressed) {
                upKeyPressed = true;
                menuState.changeSelected("UP");
                menuView.update("UP");
            } else if (input.contains("DOWN") && !downKeyPressed) {
                downKeyPressed = true;
                menuState.changeSelected("DOWN");
                menuView.update("DOWN");
            }

            if (!input.contains("UP")) {
                upKeyPressed = false;
            }

            if (!input.contains("DOWN")) {
                downKeyPressed = false;
            }



    }

    public void newGame() {

    }

}
