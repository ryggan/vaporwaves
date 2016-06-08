package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.CPUPlayer;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.menu.*;
import edu.chalmers.vaporwave.util.Debug;
import edu.chalmers.vaporwave.util.Utils;
import edu.chalmers.vaporwave.view.*;
import javafx.scene.Group;
import net.java.games.input.Controller;

import java.util.*;

/**
 * This controller handles the menus, i.e everything outside the actual game.
 * It utilizes abstract model and view objects in a State Pattern and changes
 * them when moving between menus
 */
public class MenuController implements ContentController {

    private NewGameEvent newGameEvent;
    private Map<MenuState, AbstractMenu> menuMap;
    private Map<MenuState, AbstractMenuView> menuViewMap;
    private MenuState activeMenu;

    private boolean pressedDown;

    public MenuController(Group root) throws Exception {

        GameEventBus.getInstance().register(this);

        this.newGameEvent = new NewGameEvent();
        this.pressedDown = false;

        // Menu background music
        Container.playSound(SoundID.MENU_BGM_1);

        // Setting up primary player, that will be navigate menus
        Player player;
        player = new Player(0, "P1");
        this.newGameEvent.addPlayer(player);
        player.setDirectionControls(new String[] {Utils.getPlayerControls().get(0)[0],
                Utils.getPlayerControls().get(0)[1],
                Utils.getPlayerControls().get(0)[2],
                Utils.getPlayerControls().get(0)[3]});
        player.setBombControl(Utils.getPlayerControls().get(0)[4]);
        this.newGameEvent.addPlayer(player);

        updatePlayerGamePads(this.newGameEvent.getPlayers(), true);

        // Setting up menu models
        this.activeMenu = MenuState.START_MENU;
        this.menuMap = new HashMap<>();
        this.menuMap.put(MenuState.START_MENU, new StartMenu());
        this.menuMap.put(MenuState.ROOSTER, new RoosterMenu(player));
        this.menuMap.put(MenuState.CHARACTER_SELECT, new CharacterSelectMenu());
        this.menuMap.put(MenuState.MAP_SELECT, new MapSelectMenu());
        this.menuMap.put(MenuState.RESULTS_MENU, new ResultsMenu(this.newGameEvent.getPlayers()));

        // Setting up menu views
        this.menuViewMap = new HashMap<>();
        this.menuViewMap.put(MenuState.START_MENU, new StartMenuView(root));
        this.menuViewMap.put(MenuState.ROOSTER, new RoosterMenuView(root));
        this.menuViewMap.put(MenuState.CHARACTER_SELECT, new CharacterSelectView(root));
        this.menuViewMap.put(MenuState.MAP_SELECT, new MapSelectMenuView(root,
                ((MapSelectMenu) this.menuMap.get(MenuState.MAP_SELECT)).getArenaMaps()));
        this.menuViewMap.put(MenuState.RESULTS_MENU, new ResultsMenuView(root));

        // Initiating start screen view
        updateViews(null);
    }

    public void updatePlayerGamePads(Set<Player> players, boolean updateGamePads) {
        if (updateGamePads) {
            InputController.getInstance().updateGamePads();
        }

        List<Controller> gamePads = InputController.getInstance().getGamePads();

        for (Player player : players) {
            if (gamePads.size() > player.getPlayerID()) {
                player.setGamePad(gamePads.get(player.getPlayerID()));
            }
        }
    }

    // The timerUpdate of MenuController is a simple run-down of all input and eventual
    // actions as a consequence
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) throws Exception {

        localPlayerInput(this.newGameEvent.getPrimaryPlayer());
        localPlayerPressed(this.newGameEvent.getPrimaryPlayer());
        localPlayerReleased(this.newGameEvent.getPrimaryPlayer());

        for (Player player : this.newGameEvent.getPlayers()) {
            if (player.getPlayerID() != 0) {
                remotePlayerPressed(player);
                remotePlayerReleased(player);
            }
        }
    }

    private void changeSelected(Direction direction, Player player) {
        this.menuMap.get(this.activeMenu).changeSelected(direction, player);
        updateViews(player);
    }

    // Local (/primary) player input (constantly pushed down buttons), pressed (the exact moment a button
    // is pressed down), and released (the exact moment a button is released again).
    private void localPlayerInput(Player player) {
        List<String> allInput = InputController.getInstance().getAllInput(player);
        this.pressedDown = (allInput.contains("ENTER") || allInput.contains("SPACE") || allInput.contains("BTN_A"));
    }

    private void localPlayerPressed(Player player) {
        List<String> allPressed = InputController.getInstance().getAllPressed(player);
        String[] directionControls = player.getDirectionControls();

        if (!allPressed.isEmpty()) {
            String key = allPressed.get(0);
            for (int i = 0; i < directionControls.length; i++) {
                if (key.equals(directionControls[i])
                        || key.equals("LS_UP") || key.equals("LS_LEFT")|| key.equals("LS_DOWN")|| key.equals("LS_RIGHT")
                        || key.equals("DPAD_UP") || key.equals("DPAD_LEFT")|| key.equals("DPAD_DOWN")|| key.equals("DPAD_RIGHT")) {
                    changeSelected(Utils.getDirectionFromString(key), player);
                    break;
                }
            }
            if (allPressed.contains("ENTER") || allPressed.contains("SPACE") || allPressed.contains("BTN_A")) {
                updateViews(this.newGameEvent.getPrimaryPlayer());
            }
        }
    }

    private void localPlayerReleased(Player player) {
        List<String> allReleased = InputController.getInstance().getAllReleased(player);

        if (!allReleased.isEmpty() && player.getPlayerID() == 0) {
            String key = allReleased.get(0);
            AbstractMenu menu = this.menuMap.get(this.activeMenu);

            switch (key) {
                case "ENTER":
                case "SPACE":
                case "BTN_A":
                    switch (menu.getMenuAction()) {
                        case EXIT_PROGRAM:
                            menuActionExitProgram();
                            break;

                        case START_GAME:
                            menuActionStartGame();
                            break;

                        case NO_ACTION:
                            menuActionNoAction(menu);
                            break;

                        default:
                            menuActionDefault(menu);
                            break;
                    }
                    updateViews(this.newGameEvent.getPrimaryPlayer());

                    break;
                default:
            }
        }
    }

    // Different menu actions, that primary player could inflict when navigating menus
    private void menuActionExitProgram() {
        Container.stopSound(SoundID.MENU_BGM_1);
        Container.getSound(SoundID.MENU_EXIT).getSound().setOnEndOfMedia(new EndGameThread());
        Container.playSound(SoundID.MENU_EXIT);
    }

    private void menuActionStartGame() {
        for (Player p : this.newGameEvent.getPlayers()) {
            if (p instanceof CPUPlayer) {
                p.setCharacter(null);
            }
        }
        for (Player p : this.newGameEvent.getPlayers()) {
            if(p instanceof CPUPlayer){
                p.setCharacter(getAvailableGameCharacters().get(0));
            }
        }
        if (isNewGameEventReady()) {
            GameEventBus.getInstance().post(this.newGameEvent);
            Container.stopSound(SoundID.MENU_BGM_1);
            Container.playSound(SoundID.START_GAME);
            Container.playSound(SoundID.MENU_SUCCESS);
        }else{
            Container.playSound(SoundID.EXPLOSION);
        }
    }

    private void menuActionNoAction(AbstractMenu menu) {
        menu.performMenuAction(this.newGameEvent, 0);
    }

    private void menuActionDefault(AbstractMenu menu) {
        this.setActiveMenu(menu.getMenuAction());
        if (this.menuMap.get(this.activeMenu) instanceof CharacterSelectMenu
                    && this.menuViewMap.get(this.activeMenu) instanceof CharacterSelectView) {
            Container.playSound(SoundID.SELECT_CHARACTER);
        }
    }

    // Getting available characters to fill CPU-Players with
    private List<GameCharacter> getAvailableGameCharacters() {
        List<String> allCharacters = Utils.getCharacterNames();

        for (Player player : this.newGameEvent.getPlayers()) {
            if (player.getCharacter() != null && allCharacters.contains(player.getCharacter().getName())) {
                allCharacters.remove(player.getCharacter().getName());
            }
        }

        List<GameCharacter> availableCharacters = new ArrayList<>();
        for (String name : allCharacters) {
            availableCharacters.add(new GameCharacter(name, -1));
        }
        Collections.shuffle(availableCharacters);

        System.out.println("Available characters: ");
        System.out.println("   "+availableCharacters);

        return availableCharacters;
    }

    // Remote (/secondary) player input (constantly pushed down buttons), pressed (the exact moment a button
    // is pressed down), and released (the exact moment a button is released again).
    private void remotePlayerPressed(Player player) {
        List<String> allPressed = InputController.getInstance().getAllPressed(player);
        String[] directionControls = player.getDirectionControls();

        if (!allPressed.isEmpty()) {
            String key = allPressed.get(0);

            for (int i = 0; i < directionControls.length; i++) {
                if ((key.equals(directionControls[i])
                        && (Utils.getDirectionFromString(directionControls[i]) == Direction.LEFT
                        || Utils.getDirectionFromString(directionControls[i]) == Direction.RIGHT))
                        || key.equals("LS_LEFT") || key.equals("LS_RIGHT") || key.equals("DPAD_LEFT") || key.equals("DPAD_RIGHT")) {

                    changeSelected(Utils.getDirectionFromString(key), player);
                    break;
                }
            }
        }
    }

    private void remotePlayerReleased(Player player) {
        List<String> allReleased = InputController.getInstance().getAllReleased(player);

        if (!allReleased.isEmpty()) {
            String key = allReleased.get(0);

            if (key.equals(player.getBombControl()) || key.equals("BTN_A")) {
                this.menuMap.get(this.activeMenu).performMenuAction(this.newGameEvent, player.getPlayerID());

                updateViews(player);
            }
        }
    }

    // Update correct view when changing menu content
    public void updateViews(Player player) {

         // When in rooster menu, push player info into view
         if (this.menuMap.get(this.activeMenu) instanceof RoosterMenu
                    && this.menuViewMap.get(this.activeMenu) instanceof RoosterMenuView) {

             ((RoosterMenuView) this.menuViewMap.get(this.activeMenu)).setSelectedPlayers(
                     ((RoosterMenu) this.menuMap.get(this.activeMenu)).getSelectedPlayers());

         // When in character select menu, push character and player info to view
         } else if (this.menuMap.get(this.activeMenu) instanceof CharacterSelectMenu
                 && this.menuViewMap.get(this.activeMenu) instanceof CharacterSelectView) {

             ((CharacterSelectView) this.menuViewMap.get(this.activeMenu)).setSelectedCharacters(
                     ((CharacterSelectMenu)this.menuMap.get(this.activeMenu)).getSelectedCharacters());

             ((CharacterSelectView) this.menuViewMap.get(this.activeMenu)).setPlayers(this.newGameEvent.getPlayers());
         }

        // The actual updating of the view
        this.menuViewMap.get(this.activeMenu).updateView(
                this.menuMap.get(this.activeMenu).getSelectedSuper(),
                this.menuMap.get(this.activeMenu).getSelectedSub(),
                this.menuMap.get(this.activeMenu).getRemoteSelected(),
                player,
                this.pressedDown
                );
    }

    // Changes active menu
    public void setActiveMenu(MenuState activeMenu){
        Container.playSound(SoundID.MENU_BGM_1);

        if (activeMenu == MenuState.ROOSTER) {
            updatePlayerGamePads(this.newGameEvent.getPlayers(), true);
        }
        this.activeMenu = activeMenu;

        this.menuMap.get(activeMenu).initMenu(this.newGameEvent);
        updateViews(this.newGameEvent.getPrimaryPlayer());
    }

    private boolean isNewGameEventReady() {
        for (Player player : this.newGameEvent.getPlayers()) {
            if (player.getCharacter() == null) {
                return false;
            }
        }
        return true;
    }

    // Posted from InputController when a gamepad disconnects, to update players with this
    @Subscribe
    public void gamePadDisconnected(GamePadDisconnectedEvent disconnectedEvent) {
        Controller gamePad = disconnectedEvent.getGamePad();
        for (Player player : this.newGameEvent.getPlayers()) {
            if (player.getGamePad() == gamePad) {
                if (Debug.PRINT_LOG) {
                    System.out.println("GamePad '" + gamePad.getName() + "' removed from player " + player.getName());
                }
                player.setGamePad(null);
            }
        }
    }

    // Posted from GameController when a game is over
    @Subscribe
    public void exitToMenu(ExitToMenuEvent exitToMenuEvent) {
        ((ResultsMenu) this.menuMap.get(MenuState.RESULTS_MENU)).setPlayers(exitToMenuEvent.getPlayers());

        ((ResultsMenuView) this.menuViewMap.get(MenuState.RESULTS_MENU)).setPlayers(exitToMenuEvent.getPlayers());
        ((ResultsMenuView) this.menuViewMap.get(MenuState.RESULTS_MENU)).setGameType(exitToMenuEvent.getGameType());

        GameEventBus.getInstance().post(new GoToMenuEvent(exitToMenuEvent.getDestinationMenu()));
    }

    // Posted from RoosterMenu when updating gamepads is nescessary
    @Subscribe
    public void roosterPlayersUpdated(RoosterPlayersUpdatedEvent event) {
        updatePlayerGamePads(this.newGameEvent.getPlayers(), event.isUpdateGamePads());
    }

    // A thread to wait for exit-sound to play
    private static class EndGameThread implements Runnable {
        @Override
        public void run() {
            GameEventBus.getInstance().post(new ExitGameEvent());
        }
    }
}
