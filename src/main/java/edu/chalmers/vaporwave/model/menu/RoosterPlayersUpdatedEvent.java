package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.model.Player;

import java.util.Set;

/**
 * This event is posted by RoosterMenu to update gamepads for chosen players.
 */
public class RoosterPlayersUpdatedEvent {

    private Set<Player> players;
    private boolean updateGamePads;

    public RoosterPlayersUpdatedEvent(Set<Player> players, boolean updateGamePads) {
        this.players = players;
        this.updateGamePads = updateGamePads;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public boolean isUpdateGamePads() {
        return this.updateGamePads;
    }
}
