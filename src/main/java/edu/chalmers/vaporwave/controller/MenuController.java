package edu.chalmers.vaporwave.controller;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.menu.*;
import edu.chalmers.vaporwave.util.Utils;
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

    private Player localPlayer;
    private Player remotePlayer;
    private List<Player> players;

    public MenuController(Group root) {

        this.newGameEvent = new NewGameEvent();
        this.players = new ArrayList<>();

        this.localPlayer = new Player(1, "PlayerOne");
        this.newGameEvent.setLocalPlayer(this.localPlayer);
        this.localPlayer.setDirectionControls(new String[] {"LEFT", "UP", "RIGHT", "DOWN"});
        this.localPlayer.setBombControl("SPACE");
        this.localPlayer.setMineControl("M");
        this.players.add(this.localPlayer);

        this.remotePlayer = new Player(2, "PlayerTwo");
        this.newGameEvent.setRemotePlayer(this.remotePlayer);
        this.remotePlayer.setDirectionControls(new String[] {"A", "W", "D", "S"});
        this.remotePlayer.setBombControl("SHIFT");
        this.remotePlayer.setMineControl("CAPS");
        this.players.add(this.remotePlayer);

        updatePlayerGamePads(this.players);

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

    public void updatePlayerGamePads(List<Player> players) {
        ListenerController.getInstance().updateGamePads();

        List<Controller> gamePads = ListenerController.getInstance().getGamePads();
        for (int i = 0; i < players.size(); i++) {
            if (gamePads.size() > i) {
                players.get(i).setGamePad(gamePads.get(i));
            }
        }
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        localPlayerInput(this.localPlayer);

        remotePlayerInput(this.remotePlayer);

    }

    private void changeSelected(Direction direction, int playerID) {
        menuMap.get(activeMenu).changeSelected(direction, playerID);
        updateViews(playerID);
    }

    private void localPlayerInput(Player player) {
        List<String> allPressed = ListenerController.getInstance().getAllPressed(player);
        String[] directionControls = player.getDirectionControls();

        if (!allPressed.isEmpty()) {
            String key = allPressed.get(0);
            for (int i = 0; i < directionControls.length; i++) {
                if (key.equals(directionControls[i])
                        || key.equals("LS_UP") || key.equals("LS_LEFT")|| key.equals("LS_DOWN")|| key.equals("LS_RIGHT")
                        || key.equals("DPAD_UP") || key.equals("DPAD_LEFT")|| key.equals("DPAD_DOWN")|| key.equals("DPAD_RIGHT")) {
                    changeSelected(Utils.getDirectionFromString(key), 0);
                    break;
                }
            }
        }

        List<String> allReleased = ListenerController.getInstance().getAllReleased(player);

        if (!allReleased.isEmpty()) {
            String key = allReleased.get(0);

            switch (key) {
                case "ENTER":
                case "SPACE":
                case "BTN_A":
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
                default:
            }
        }
    }

    private void remotePlayerInput(Player player) {
        List<String> allPressed = ListenerController.getInstance().getAllPressed(player);
        String[] directionControls = player.getDirectionControls();

        if (!allPressed.isEmpty()) {
            String key = allPressed.get(0);

            for (int i = 0; i < directionControls.length; i++) {
                if ((key.equals(directionControls[i])
                        && (Utils.getDirectionFromString(directionControls[i]) == Direction.LEFT
                        || Utils.getDirectionFromString(directionControls[i]) == Direction.RIGHT))
                        || key.equals("LS_LEFT") || key.equals("LS_RIGHT") || key.equals("DPAD_LEFT") || key.equals("DPAD_RIGHT")) {

                    changeSelected(Utils.getDirectionFromString(key), player.getPlayerId() - 1);
                    break;
                }
            }
        }

        List<String> allReleased = ListenerController.getInstance().getAllReleased(player);

        if (!allReleased.isEmpty()) {
            String key = allReleased.get(0);

            if (key.equals(player.getBombControl()) || key.equals("BTN_A")) {
                menuMap.get(activeMenu).performMenuAction(newGameEvent, 1);

                if (menuMap.get(activeMenu) instanceof CharacterSelect && menuViewMap.get(activeMenu) instanceof CharacterSelectView) {
                    ((CharacterSelectView) menuViewMap.get(activeMenu)).setSelectedCharacters(
                            ((CharacterSelect)menuMap.get(activeMenu)).getSelectedCharacters()
                    );
                }

                updateViews(1);
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
        if (activeMenu == MenuState.START_MENU) {
            updatePlayerGamePads(this.players);
        }
        this.activeMenu = activeMenu;
    }

    private boolean isNewGameEventReady() {
        return this.newGameEvent.getLocalPlayer().getCharacter() != null &&
                this.newGameEvent.getRemotePlayer().getCharacter() != null;
    }

}
