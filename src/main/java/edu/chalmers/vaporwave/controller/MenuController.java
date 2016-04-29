package edu.chalmers.vaporwave.controller;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.ExitGameEvent;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.CPUPlayer;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.model.menu.AbstractMenu;
import edu.chalmers.vaporwave.model.menu.CharacterMenu;
import edu.chalmers.vaporwave.model.menu.MenuCategory;
import edu.chalmers.vaporwave.model.menu.StartMenu;
import edu.chalmers.vaporwave.view.AbstractMenuView;
import edu.chalmers.vaporwave.view.CharacterSelectView;
import edu.chalmers.vaporwave.view.StartMenuView;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class MenuController {

    private NewGameEvent newGameEvent;
    private List<AbstractMenu> menuList;
    private List<AbstractMenuView> menuViewList;
    private int activeMenu;

    public MenuController(Group root) {

        this.newGameEvent = new NewGameEvent();

        Player thisPlayer = new Player(1, "PlayerOne");
        thisPlayer.setCharacter(new GameCharacter("ALYSSA"));
        this.newGameEvent.setLocalPlayer(thisPlayer);

        Player testCPUPlayer = new CPUPlayer(2, "CPUPlayerOne");
        testCPUPlayer.setCharacter(new GameCharacter("CHARLOTTE"));

        this.activeMenu = 0;
        this.menuList = new ArrayList<>();
        this.menuList.add(new StartMenu(this.newGameEvent));
        this.menuList.add(new CharacterMenu(this.newGameEvent));

        this.menuViewList = new ArrayList<>();
        this.menuViewList.add(new StartMenuView(root));
        this.menuViewList.add(new CharacterSelectView(root));

        this.menuViewList.get(activeMenu).updateView(
                this.menuList.get(activeMenu).getSelectedSuper(),
                this.menuList.get(activeMenu).getSelectedSub()
        );
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {
        if (!ListenerController.getInstance().getPressed().isEmpty()) {
            switch (ListenerController.getInstance().getPressed().get(0)) {
                case "UP":
                    menuList.get(activeMenu).changeSelected(Direction.UP);
                    updateViews();
                    break;
                case "DOWN":
                    menuList.get(activeMenu).changeSelected(Direction.DOWN);
                    updateViews();
                    break;
                case "LEFT":
                    menuList.get(activeMenu).changeSelected(Direction.LEFT);
                    updateViews();
                    break;
                case "RIGHT":
                    menuList.get(activeMenu).changeSelected(Direction.RIGHT);
                    updateViews();
                    break;
                case "ENTER":
                case "SPACE":
                    switch (menuList.get(activeMenu).getMenuAction()) {
                        case EXIT_GAME:
                            GameEventBus.getInstance().post(new ExitGameEvent());
                            break;
                        case START_GAME:
                            for (AbstractMenuView menu : this.menuViewList) {
                                menu.clearView();
                            }
                            GameEventBus.getInstance().post(newGameEvent);
                            break;
                        case NEXT:
                            this.activeMenu += 1;
                            updateViews();
                            break;
                        case PREVIOUS:
                            this.activeMenu -= 1;
                            updateViews();
                            break;
                    }

                    break;
            }
        }
    }

    public void updateViews() {
        this.menuViewList.get(activeMenu).updateView(
                this.menuList.get(activeMenu).getSelectedSuper(),
                this.menuList.get(activeMenu).getSelectedSub()
        );
    }



}
