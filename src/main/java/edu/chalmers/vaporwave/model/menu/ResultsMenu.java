package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.model.Player;

import java.util.HashSet;
import java.util.Set;

public class ResultsMenu extends AbstractMenu {

    private Set<Player> players;

    public ResultsMenu(NewGameEvent newGameEvent) {
        super( new int[]{1, 0});
    }

    public ResultsMenu(Set<Player> players) {
        super(new int[]{1, 0});

        setPlayers(players);
    }

    public void setPlayers(Set<Player> players) {
        this.players = new HashSet<>();
        this.players.addAll(players);
    }

    @Override
    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            for (Player player : this.players) {
                player.resetPlayerGameStats();
            }

            return MenuState.START_MENU;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {

    }

    @Override
    public void initMenu(NewGameEvent newGameEvent) {

    }

}
