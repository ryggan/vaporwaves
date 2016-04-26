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
    private Group root;

    public MenuController(Group root) {
        menuView = new MenuView(root);
        this.root = root;
        menuState=StartMenu.getInstance();
        menuView.setMenuState(menuState);

    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        if (ListenerController.getInstance().getPressed().contains("UP")) {
            menuState.changeSelected("UP");
            menuView.update("UP");
        } else if (ListenerController.getInstance().getPressed().contains("DOWN")) {
            menuState.changeSelected("DOWN");
            menuView.update("DOWN");
        } else if (ListenerController.getInstance().getPressed().contains("SPACE")) {
            removeMenu();
            GameEventBus.getInstance().post(new NewGameEvent());
        }

    }

    public void removeMenu() {
        this.root.getChildren().clear();

    }

    public void newGame() {

    }
}
