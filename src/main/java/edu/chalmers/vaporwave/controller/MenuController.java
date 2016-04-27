package edu.chalmers.vaporwave.controller;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.menu.AbstractMenu;
import edu.chalmers.vaporwave.model.menu.StartMenu;
import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class MenuController {

    private MenuView menuView;
    private NewGameEvent newGameEvent;
    private List<AbstractMenu> menuList;
    private int activeMenu;

    public MenuController(Group root) {
        menuView = new MenuView(root);
        this.menuView.updateView(0);
        this.newGameEvent = new NewGameEvent();
        this.activeMenu = 0;
        this.menuList = new ArrayList<>();
        menuList.add(new StartMenu(this.newGameEvent));
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {
        if (!ListenerController.getInstance().getPressed().isEmpty()) {
            switch (ListenerController.getInstance().getPressed().get(0)) {
                case "UP":
                    menuList.get(activeMenu).changeSelected(Direction.UP);
                    break;
                case "DOWN":
                    menuList.get(activeMenu).changeSelected(Direction.DOWN);
                    break;
                case "LEFT":
                    menuList.get(activeMenu).changeSelected(Direction.LEFT);
                    break;
                case "RIGHT":
                    menuList.get(activeMenu).changeSelected(Direction.RIGHT);
                    break;
                case "ENTER":
                case "SPACE":
                    switch (menuList.get(activeMenu).getMenuAction()) {
                        case EXIT_GAME:
                            GameEventBus.getInstance().post(new ExitGameEvent());
                            break;
                        case START_GAME:
                            GameEventBus.getInstance().post(newGameEvent);
                            break;
                        case NEXT:
                            this.activeMenu += 1;
                            break;
                        case PREVIOUS:
                            this.activeMenu -= 1;
                            break;
                    }

                    break;
            }
            if (activeMenu > menuList.size()) {


            } else {
                this.menuView.updateView(this.menuList.get(activeMenu).getSelectedSuper());
            }
        }
    }


}
