package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.model.Player;

import java.util.HashMap;
import java.util.Set;

public class ResultsMenu extends AbstractMenu {

    Set<Player> players;

    public ResultsMenu(NewGameEvent newGameEvent) {
        super( new int[]{1, 0});
    }

    public ResultsMenu(Set<Player> players) {
        super( new int[]{1, 0});
        this.players=players;
    }

    //exit

    @Override
    public MenuState getMenuAction(){
            if (this.getSelectedSuper() == 0) {
                return MenuState.START_MENU;
            }

            return MenuState.NO_ACTION;

    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {

    }

}
