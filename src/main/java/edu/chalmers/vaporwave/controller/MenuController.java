package edu.chalmers.vaporwave.controller;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.menu.*;
import edu.chalmers.vaporwave.view.*;
import javafx.scene.Group;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuController {

    private NewGameEvent newGameEvent;
    private Map<MenuState, AbstractMenu> menuMap;
    private Map<MenuState, AbstractMenuView> menuViewMap;
    private MenuState activeMenu;

    public MenuController(Group root) {

        this.newGameEvent = new NewGameEvent();

        Player localPlayer = new Player(1, "PlayerOne");
        localPlayer.setCharacter(new GameCharacter("ALYSSA", new Point(0,0)));
        this.newGameEvent.setLocalPlayer(localPlayer);

        Player remotePlayer = new Player(2, "PlayerTwo");
        remotePlayer.setCharacter(new GameCharacter("CHARLOTTE", new Point(20,0)));
        this.newGameEvent.setRemotePlayer(remotePlayer);

        this.activeMenu = MenuState.START_MENU;
        this.menuMap = new HashMap<>();
        this.menuMap.put(MenuState.START_MENU, new StartMenu(this.newGameEvent));
        this.menuMap.put(MenuState.CHARACTER_SELECT, new CharacterMenu(this.newGameEvent));
        this.menuMap.put(MenuState.RESULTS_MENU, new ResultsMenu(this.newGameEvent));

        this.menuViewMap = new HashMap<>();
        this.menuViewMap.put(MenuState.START_MENU, new StartMenuView(root));
        this.menuViewMap.put(MenuState.CHARACTER_SELECT, new CharacterSelectView(root));
        this.menuViewMap.put(MenuState.RESULTS_MENU, new ResultsMenuView(root));

        this.menuViewMap.get(activeMenu).updateView(
                this.menuMap.get(activeMenu).getSelectedSuper(),
                this.menuMap.get(activeMenu).getSelectedSub()
        );
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {


        if (!ListenerController.getInstance().getPressed().isEmpty()) {
            switch (ListenerController.getInstance().getPressed().get(0)) {
                case "UP":
                    menuMap.get(activeMenu).changeSelected(Direction.UP);
                    updateViews();
                    break;
                case "DOWN":
                    menuMap.get(activeMenu).changeSelected(Direction.DOWN);
                    updateViews();
                    break;
                case "LEFT":
                    menuMap.get(activeMenu).changeSelected(Direction.LEFT);
                    updateViews();
                    break;
                case "RIGHT":
                    menuMap.get(activeMenu).changeSelected(Direction.RIGHT);
                    updateViews();
                    break;
            }
        }

        if (!ListenerController.getInstance().getReleased().isEmpty()) {

            System.out.println("active Menu : " + activeMenu);

            switch (ListenerController.getInstance().getReleased().get(0)) {
                case "ENTER":
                case "SPACE":
                    switch (menuMap.get(activeMenu).getMenuAction()) {
                        case EXIT_PROGRAM:
                            GameEventBus.getInstance().post(new ExitGameEvent());
                            break;
                        case START_GAME:
                            for (Map.Entry<MenuState, AbstractMenuView> menu : this.menuViewMap.entrySet()) {
                                menu.getValue().clearView();
                            }
                            GameEventBus.getInstance().post(newGameEvent);
                            break;
                        case NO_ACTION:
                            break;
                        default:
                            System.out.println("Do menu action :D");
                            this.setActiveMenu(menuMap.get(activeMenu).getMenuAction());
                            updateViews();
                            break;
                    }

                    break;
            }
        }
    }

    public void updateViews() {
        this.menuViewMap.get(activeMenu).updateView(
                this.menuMap.get(activeMenu).getSelectedSuper(),
                this.menuMap.get(activeMenu).getSelectedSub()
        );
    }

    public void setActiveMenu(MenuState activeMenu){
        this.activeMenu=activeMenu;
    }



}
