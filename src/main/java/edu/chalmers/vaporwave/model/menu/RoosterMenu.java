package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.util.Constants;

public class RoosterMenu extends AbstractMenu {

    private int[] selectedPlayers;

    public RoosterMenu(NewGameEvent newGameEvent) {
        super(new int[]{0, 3, 0}, newGameEvent, 0);
        this.selectedPlayers = new int[Constants.MAX_NUMBER_OF_PLAYERS];
        this.selectedPlayers[0] = 1;
    }

    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            return MenuState.START_MENU;
        } else if (getSelectedSuper() == 2) {
            return MenuState.CHARACTER_SELECT;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        int subSelect = this.getSelectedSub()[1];
        do {
            this.selectedPlayers[subSelect] += 1;
        } while(playerIsChosen(subSelect, this.selectedPlayers[subSelect]));

        if (this.selectedPlayers[subSelect] > 5) {
            this.selectedPlayers[subSelect] = 0;
        }
    }

    private boolean playerIsChosen(int current, int player) {
        for (int i = 0; i < this.selectedPlayers.length; i++) {
            if (current != i && this.selectedPlayers[i] == player) {
                return true;
            }
        }
        return false;
    }

    public int[] getSelectedPlayers() {
        return this.selectedPlayers;
    }
}
