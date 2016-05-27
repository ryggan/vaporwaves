package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.model.Player;

import java.util.Set;

public class RoosterPlayersUpdatedEvent {

    private Set<Player> players;
    private boolean updateListener;

    public RoosterPlayersUpdatedEvent(Set<Player> players, boolean updateListener) {
        this.players = players;
        this.updateListener = updateListener;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public boolean isUpdateListener() {
        return this.updateListener;
    }
}
