package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.MenuState;

import java.util.Set;

public class ExitToMenuEvent {

    private MenuState destinationMenu;
    private Set<Player> players;

    public ExitToMenuEvent(MenuState destinationMenu) {
        this.destinationMenu = destinationMenu;
    }

    public ExitToMenuEvent(MenuState destinationMenu, Set<Player> players) {
        this.destinationMenu = destinationMenu;
        this.players=players;
    }

    public MenuState getDestinationMenu() {
        return this.destinationMenu;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

}
