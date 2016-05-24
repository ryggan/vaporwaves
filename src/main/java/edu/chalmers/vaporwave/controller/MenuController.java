package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.event.*;
import edu.chalmers.vaporwave.model.game.CPUPlayer;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.menu.NewGameEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.*;
import edu.chalmers.vaporwave.util.Debug;
import edu.chalmers.vaporwave.util.SoundPlayer;
import edu.chalmers.vaporwave.util.Utils;
import edu.chalmers.vaporwave.view.*;
import javafx.scene.Group;
import javafx.scene.media.MediaPlayer;
import net.java.games.input.Controller;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuController {

    private NewGameEvent newGameEvent;
    private Map<MenuState, AbstractMenu> menuMap;
    private Map<MenuState, AbstractMenuView> menuViewMap;
    private MenuState activeMenu;
    private ResultsMenuView resultsMenuView;

    private SoundPlayer menuMusic;

    private boolean pressedDown;

    public MenuController(Group root) {

        GameEventBus.getInstance().register(this);

        this.newGameEvent = new NewGameEvent();

        this.menuMusic = Container.getSound(SoundID.MENU_BGM_1);
        menuMusic.playSound();
        menuMusic.loopSound(true);

        Player player;
        player = new Player(0, "P1");
        this.newGameEvent.setLocalPlayer(player);
        player.setDirectionControls(new String[] {Utils.getPlayerControls().get(0)[0],
                Utils.getPlayerControls().get(0)[1],
                Utils.getPlayerControls().get(0)[2],
                Utils.getPlayerControls().get(0)[3]});
        player.setBombControl(Utils.getPlayerControls().get(0)[4]);
        this.newGameEvent.addPlayer(player);

        updatePlayerGamePads(newGameEvent.getPlayers());


        this.activeMenu = MenuState.START_MENU;
        this.menuMap = new HashMap<>();
        this.menuMap.put(MenuState.START_MENU, new StartMenu());
        this.menuMap.put(MenuState.ROOSTER, new RoosterMenu());
        this.menuMap.put(MenuState.CHARACTER_SELECT, new CharacterSelectMenu());
        this.menuMap.put(MenuState.RESULTS_MENU, new ResultsMenu(this.newGameEvent.getPlayers()));
        
        resultsMenuView=new ResultsMenuView(root);


        this.menuViewMap = new HashMap<>();
        this.menuViewMap.put(MenuState.START_MENU, new StartMenuView(root));
        this.menuViewMap.put(MenuState.ROOSTER, new RoosterMenuView(root));
        this.menuViewMap.put(MenuState.CHARACTER_SELECT, new CharacterSelectView(root));
        this.menuViewMap.put(MenuState.RESULTS_MENU, resultsMenuView);

        this.pressedDown = false;

        this.menuViewMap.get(activeMenu).updateView(
                this.menuMap.get(activeMenu).getSelectedSuper(),
                this.menuMap.get(activeMenu).getSelectedSub(),
                null,
                newGameEvent.getPrimaryPlayer(),
                false
        );
    }

    public void updatePlayerGamePads(Set<Player> players) {
        ListenerController.getInstance().updateGamePads();

        List<Controller> gamePads = ListenerController.getInstance().getGamePads();


        for (Player player : players) {
            if (gamePads.size() > player.getPlayerID()) {
                player.setGamePad(gamePads.get(player.getPlayerID()));
            }
        }
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        localPlayerInput(this.newGameEvent.getPrimaryPlayer());

        for (Player player : newGameEvent.getPlayers()) {
            if (player.getPlayerID() != 0) {
                remotePlayerInput(player);
            }
        }
    }

    private void changeSelected(Direction direction, Player player) {
        menuMap.get(activeMenu).changeSelected(direction, player);
        updateViews(player);
    }

    private void localPlayerInput(Player player) {
        List<String> allInput = ListenerController.getInstance().getAllInput(player);
        this.pressedDown = (allInput.contains("ENTER") || allInput.contains("SPACE") || allInput.contains("BTN_A"));

        List<String> allPressed = ListenerController.getInstance().getAllPressed(player);
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

        List<String> allReleased = ListenerController.getInstance().getAllReleased(player);

        if (!allReleased.isEmpty() && player.getPlayerID() == 0) {
            String key = allReleased.get(0);

            switch (key) {
                case "ENTER":
                case "SPACE":
                case "BTN_A":
                    switch (menuMap.get(activeMenu).getMenuAction()) {
                        case EXIT_PROGRAM:
                            menuMusic.stopSound();
                            Container.getSound(SoundID.MENU_EXIT).getSound().play();
                            Container.getSound(SoundID.MENU_EXIT).getSound().setOnEndOfMedia(new Runnable() {
                                @Override
                                public void run() {
                                    GameEventBus.getInstance().post(new ExitGameEvent());
                                }
                            });

                            break;
                        case START_GAME:
                            for (Player p : this.newGameEvent.getPlayers()) {
                                if(p.getClass().equals(CPUPlayer.class)){
                                   // menuMusic.loopSound(false);
                                    p.setCharacter(getAvailableGameCharacters().get(0));
                                }
                            }
                            if (isNewGameEventReady()) {
                                GameEventBus.getInstance().post(this.newGameEvent);
                                menuMusic.stopSound();
                            }else{
                                Container.playSound(SoundID.EXPLOSION);
                            }
                            break;
                        case NO_ACTION:
                            menuMap.get(activeMenu).performMenuAction(newGameEvent, 0);

                            if (menuMap.get(activeMenu) instanceof CharacterSelectMenu && menuViewMap.get(activeMenu) instanceof CharacterSelectView) {
                                ((CharacterSelectView) menuViewMap.get(activeMenu)).setSelectedCharacters(
                                        ((CharacterSelectMenu)menuMap.get(activeMenu)).getSelectedCharacters()
                                );
                            } else if (menuMap.get(activeMenu) instanceof RoosterMenu && menuViewMap.get(activeMenu) instanceof RoosterMenuView) {
                                ((RoosterMenuView) menuViewMap.get(activeMenu)).setSelectedPlayers(
                                        ((RoosterMenu)menuMap.get(activeMenu)).getSelectedPlayers()
                                );

                            }
                            break;

                        default:
                            this.setActiveMenu(menuMap.get(activeMenu).getMenuAction());
                            if (menuMap.get(activeMenu) instanceof RoosterMenu && menuViewMap.get(activeMenu) instanceof RoosterMenuView) {
                               ((RoosterMenuView) menuViewMap.get(activeMenu)).setSelectedPlayers(
                                        ((RoosterMenu)menuMap.get(activeMenu)).getSelectedPlayers()
                                );
                            } else if (menuMap.get(activeMenu) instanceof CharacterSelectMenu && menuViewMap.get(activeMenu) instanceof CharacterSelectView) {
                                ((CharacterSelectView) menuViewMap.get(activeMenu)).setPlayers(this.newGameEvent.getPlayers());
                            }

                            break;
                    }
                    updateViews(this.newGameEvent.getPrimaryPlayer());

                    break;
                default:
            }
        }
    }



    private List<GameCharacter> getAvailableGameCharacters() {
        Set<GameCharacter> allCharacters = new HashSet<>();
        allCharacters.add(new GameCharacter("ALYSSA", -1));
        allCharacters.add(new GameCharacter("MEI", -1));
        allCharacters.add(new GameCharacter("CHARLOTTE", -1));
        allCharacters.add(new GameCharacter("ZYPHER", -1));

        for (Player player : this.newGameEvent.getPlayers()) {
            if (allCharacters.contains(player.getCharacter())) {
                allCharacters.remove(player.getCharacter());
            }
        }

        List<GameCharacter> availableCharacters = new ArrayList<>();
        availableCharacters.addAll(allCharacters);
        Collections.shuffle(availableCharacters);

        return availableCharacters;
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

                    changeSelected(Utils.getDirectionFromString(key), player);
                    break;
                }
            }
        }

        List<String> allReleased = ListenerController.getInstance().getAllReleased(player);

        if (!allReleased.isEmpty()) {
            String key = allReleased.get(0);

            if (key.equals(player.getBombControl()) || key.equals("BTN_A")) {
                menuMap.get(activeMenu).performMenuAction(newGameEvent, player.getPlayerID());

                if (menuMap.get(activeMenu) instanceof CharacterSelectMenu && menuViewMap.get(activeMenu) instanceof CharacterSelectView) {
                    ((CharacterSelectView) menuViewMap.get(activeMenu)).setSelectedCharacters(
                            ((CharacterSelectMenu)menuMap.get(activeMenu)).getSelectedCharacters()
                    );
                }

                updateViews(player);
            }
        }
    }

    public void updateViews(Player player) {
            this.menuViewMap.get(activeMenu).updateView(
                    this.menuMap.get(activeMenu).getSelectedSuper(),
                    this.menuMap.get(activeMenu).getSelectedSub(),
                    this.menuMap.get(activeMenu).getRemoteSelected(),
                    player,
                    this.pressedDown
            );
    }

    public void setActiveMenu(MenuState activeMenu){
        if (activeMenu == MenuState.ROOSTER) {
            updatePlayerGamePads(this.newGameEvent.getPlayers());
        }
        this.activeMenu = activeMenu;
    }

    private boolean isNewGameEventReady() {
        for (Player player : this.newGameEvent.getPlayers()) {
            if (player.getCharacter() == null) {
                return false;
            }
        }
        return true;
    }

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

    @Subscribe
    public void exitToMenu(ExitToMenuEvent exitToMenuEvent) {

        resultsMenuView.setPlayers(exitToMenuEvent.getPlayers());
        resultsMenuView.setGameType(exitToMenuEvent.getGameType());

        GameEventBus.getInstance().post(new GoToMenuEvent(exitToMenuEvent.getDestinationMenu()));
    }
}
