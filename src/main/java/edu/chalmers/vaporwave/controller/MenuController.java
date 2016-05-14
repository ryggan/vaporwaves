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
import net.java.games.input.Controller;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuController {

    private NewGameEvent newGameEvent;
    private Map<MenuState, AbstractMenu> menuMap;
    private Map<MenuState, AbstractMenuView> menuViewMap;
    private MenuState activeMenu;

    public MenuController(Group root) {

        this.newGameEvent = new NewGameEvent();

        List<Controller> gamePads = ListenerController.getInstance().getGamePads();

        Player localPlayer = new Player(1, "PlayerOne");
        this.newGameEvent.setLocalPlayer(localPlayer);
        if (gamePads.size() > 0) {
            localPlayer.setGamePad(gamePads.get(0));
        }

        Player remotePlayer = new Player(2, "PlayerTwo");
        this.newGameEvent.setRemotePlayer(remotePlayer);
        if (gamePads.size() > 1) {
            remotePlayer.setGamePad(gamePads.get(1));
        }

        this.activeMenu = MenuState.START_MENU;
        this.menuMap = new HashMap<>();
        this.menuMap.put(MenuState.START_MENU, new StartMenu(this.newGameEvent));
        this.menuMap.put(MenuState.CHARACTER_SELECT, new CharacterSelect(this.newGameEvent));
        this.menuMap.put(MenuState.RESULTS_MENU, new ResultsMenu(this.newGameEvent));

        this.menuViewMap = new HashMap<>();
        this.menuViewMap.put(MenuState.START_MENU, new StartMenuView(root));
        this.menuViewMap.put(MenuState.CHARACTER_SELECT, new CharacterSelectView(root));
        this.menuViewMap.put(MenuState.RESULTS_MENU, new ResultsMenuView(root));

        this.menuViewMap.get(activeMenu).updateView(
                this.menuMap.get(activeMenu).getSelectedSuper(),
                this.menuMap.get(activeMenu).getSelectedSub(),
                null,
                -1
        );
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {


        if (!ListenerController.getInstance().getPressed().isEmpty()) {
            switch (ListenerController.getInstance().getPressed().get(0)) {
                case "UP":
                    menuMap.get(activeMenu).changeSelected(Direction.UP, 0);
                    updateViews(0);
                    break;
                case "DOWN":
                    menuMap.get(activeMenu).changeSelected(Direction.DOWN, 0);
                    updateViews(0);
                    break;
                case "LEFT":
                    menuMap.get(activeMenu).changeSelected(Direction.LEFT, 0);
                    updateViews(0);
                    break;
                case "RIGHT":
                    menuMap.get(activeMenu).changeSelected(Direction.RIGHT, 0);
                    updateViews(0);
                    break;
                case "A":
                    menuMap.get(activeMenu).changeSelected(Direction.LEFT, 1);
                    updateViews(1);
                    break;
                case "D":
                    menuMap.get(activeMenu).changeSelected(Direction.RIGHT, 1);
                    updateViews(1);
                    break;
                default:
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
                            updateViews(0);
                            break;
                        case START_GAME:
                            if (isNewGameEventReady()) {
//                                for (Map.Entry<MenuState, AbstractMenuView> menu : this.menuViewMap.entrySet()) {
//                                    menu.getValue().clearView();
//                                    updateViews(-1);
//                                }
                                GameEventBus.getInstance().post(newGameEvent);
                            }
                            break;
                        case NO_ACTION:
                            menuMap.get(activeMenu).performMenuAction(newGameEvent, 0);

                            if (menuMap.get(activeMenu) instanceof CharacterSelect && menuViewMap.get(activeMenu) instanceof CharacterSelectView) {
                                ((CharacterSelectView) menuViewMap.get(activeMenu)).setSelectedCharacters(
                                        ((CharacterSelect)menuMap.get(activeMenu)).getSelectedCharacters()
                                );
                            }

                            updateViews(0);
                            break;
                        default:
                            this.setActiveMenu(menuMap.get(activeMenu).getMenuAction());
                            updateViews(0);
                            break;
                    }

                    break;
                case "SHIFT":
                    menuMap.get(activeMenu).performMenuAction(newGameEvent, 1);

                    if (menuMap.get(activeMenu) instanceof CharacterSelect && menuViewMap.get(activeMenu) instanceof CharacterSelectView) {
                        ((CharacterSelectView) menuViewMap.get(activeMenu)).setSelectedCharacters(
                                ((CharacterSelect)menuMap.get(activeMenu)).getSelectedCharacters()
                        );
                    }

                    updateViews(1);
                    break;
                default:
            }
        }
    }

    public void updateViews(int playerID) {
        this.menuViewMap.get(activeMenu).updateView(
                this.menuMap.get(activeMenu).getSelectedSuper(),
                this.menuMap.get(activeMenu).getSelectedSub(),
                this.menuMap.get(activeMenu).getRemoteSelected(),
                playerID
        );
    }


    public void setActiveMenu(MenuState activeMenu){
        this.activeMenu = activeMenu;
    }

    private boolean isNewGameEventReady() {
        return this.newGameEvent.getLocalPlayer().getCharacter() != null &&
                this.newGameEvent.getRemotePlayer().getCharacter() != null;
    }

}
