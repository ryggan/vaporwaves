package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.menu.StartMenu;
import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

import java.util.ArrayList;

public class MenuController {

    private MenuView menuView;
    private StartMenu startMenu=StartMenu.getInstance();

    public MenuController(Group root) {
        menuView = new MenuView(root);

        GameEventBus.getInstance().post(new NewGameEvent());
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        ArrayList<String> input = ListenerController.getInstance().getInput();

            if (input.contains("UP")) {
                startMenu.changeSelected("UP");
            } else if (input.contains("DOWN")) {
                startMenu.changeSelected("DOWN");
            }


    }

}
