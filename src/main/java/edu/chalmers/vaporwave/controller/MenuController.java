package edu.chalmers.vaporwave.controller;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.menu.Menu;
import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

public class MenuController {

    private MenuView menuView;
    private Menu menuState;

    public MenuController(Group root) {
        menuView = new MenuView(root);
        this.menuState = new Menu(1);
        this.menuView.updateView(0);
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {
        if (!ListenerController.getInstance().getPressed().isEmpty()) {
            switch (ListenerController.getInstance().getPressed().get(0)) {
                case "UP":
                    menuState.changeSelected(Direction.UP);
                    break;
                case "DOWN":
                    menuState.changeSelected(Direction.DOWN);
                    break;
                case "ENTER":
                case "SPACE": // todo: Make this more beautiful!
                    if (this.menuState.getSelected() == 0) {
                        newGame();
                    } else if (this.menuState.getSelected() == 1) {
                        System.exit(0);
                    }
                    break;
            }
            this.menuView.updateView(this.menuState.getSelected());

        }
    }

    public void removeMenu() {
//        this.root.getChildren().clear();

    }

    public void selectMenu() {

    }

    public void newGame() {
        GameEventBus.getInstance().post(new NewGameEvent());
    }
}
