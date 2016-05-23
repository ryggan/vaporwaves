package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

public class RoosterMenu extends AbstractMenu {

    private int[] selectedPlayers;
    private List<Player> allPlayers;

    public RoosterMenu() {
        super(new int[]{0, 3, 0}, 0);
        this.selectedPlayers = new int[Constants.MAX_NUMBER_OF_PLAYERS];
        this.selectedPlayers[0] = 1;

        allPlayers = new ArrayList<>();

        Player player;
        for (int i = 0; i < Constants.MAX_NUMBER_OF_PLAYERS; i++) {
            player = new Player(i, "P" + (i+1));
            player.setDirectionControls(new String[] {Utils.getPlayerControls().get(i)[0],
                    Utils.getPlayerControls().get(i)[1],
                    Utils.getPlayerControls().get(i)[2],
                    Utils.getPlayerControls().get(i)[3]});
            player.setBombControl(Utils.getPlayerControls().get(i)[4]);
            player.setMineControl(Utils.getPlayerControls().get(i)[5]);
            this.allPlayers.add(player);
        }
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
        if (subSelect > 0) {
            do {
                this.selectedPlayers[subSelect] += 1;
            } while (playerIsChosen(subSelect, this.selectedPlayers[subSelect]));

            if (this.selectedPlayers[subSelect] > 5) {
                this.selectedPlayers[subSelect] = 0;
            }

            updatePlayers(subSelect, newGameEvent);

        }
    }

    private void updatePlayers(int subSelect, NewGameEvent newGameEvent) {
        newGameEvent.getPlayers().clear();
        newGameEvent.addPlayer(this.allPlayers.get(0));
        for (int i = 0; i < this.selectedPlayers.length; i++) {
            newGameEvent.addPlayer(this.allPlayers.get(this.selectedPlayers[subSelect] - 1));
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
        int[] returnValue = new int[this.selectedPlayers.length];
        for (int i = 0; i < this.selectedPlayers.length; i++) {
            returnValue[i] = this.selectedPlayers[i];
        }
        return returnValue;
    }
}
