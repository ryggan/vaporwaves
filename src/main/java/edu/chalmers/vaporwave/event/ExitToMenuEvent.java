package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.GameType;

import java.util.Set;

/**
 * This event is posted to the eventbus by GameController when the exiting to menu
 */
public class ExitToMenuEvent {

    private MenuState destinationMenu;
    private Set<Player> players;
    private GameType gameType;

    public ExitToMenuEvent(MenuState destinationMenu, Set<Player> players, GameType gameType) {
        this.destinationMenu = destinationMenu;
        this.players = players;
        this.gameType = gameType;
    }

    public MenuState getDestinationMenu() {
        return this.destinationMenu;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public GameType getGameType() {
        return this.gameType;
    }

}
