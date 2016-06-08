package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.model.CPUPlayer;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The Rooster is a menu where the primary player decides how many other players that
 * should be playing, and if they should be human or CPU.
 */
public class RoosterMenu extends AbstractMenu {

    private int[] selectedPlayers;
    private List<Player> allPlayers;

    public RoosterMenu(Player localPlayer) {
        super(new int[]{0, 3, 0}, 1);
        this.selectedPlayers = new int[Constants.MAX_NUMBER_OF_PLAYERS];
        this.selectedPlayers[0] = 1;

        allPlayers = new ArrayList<>();

        this.allPlayers.add(localPlayer);

        Player player;
        for (int i = 1; i < Constants.MAX_NUMBER_OF_PLAYERS; i++) {
            player = new Player(i, "P" + (i+1));
            player.setDirectionControls(new String[] {Utils.getPlayerControls().get(i)[0],
                    Utils.getPlayerControls().get(i)[1],
                    Utils.getPlayerControls().get(i)[2],
                    Utils.getPlayerControls().get(i)[3]});
            player.setBombControl(Utils.getPlayerControls().get(i)[4]);

            this.allPlayers.add(player);
        }
    }

    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_BACKWARD_CLICK);
            return MenuState.START_MENU;
        } else if (getSelectedSuper() == 2) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            return MenuState.CHARACTER_SELECT;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        int subSelect = this.getSelectedSub()[1];
        if (subSelect > 0) {
            System.out.println("subselect > 0 !!");
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            do {
                this.selectedPlayers[subSelect] += 1;
                System.out.println("Correcting selected player! "+this.selectedPlayers[subSelect]);
            } while (playerIsChosen(subSelect, this.selectedPlayers[subSelect]));

            if (this.selectedPlayers[subSelect] > 5) {
                this.selectedPlayers[subSelect] = 0;
            }
            updatePlayers(newGameEvent);
        }
    }

    // This is called every time a new choice in the rooster is made, to make sure
    // that the correct collection of players is carried on to the next menu
    private void updatePlayers(NewGameEvent newGameEvent) {
        newGameEvent.getPlayers().clear();
        newGameEvent.addPlayer(this.allPlayers.get(0));

        for (int i = 1; i < this.selectedPlayers.length; i++) {

            if (selectedPlayers[i] > 0 && selectedPlayers[i] < 5) {
                newGameEvent.addPlayer(this.allPlayers.get(selectedPlayers[i] - 1));

            }
        }
        for (int i = 1; i < this.selectedPlayers.length; i++) {
            if(selectedPlayers[i] == 5) {
                int id = 1;
                while (!playerIDAvailable(newGameEvent.getPlayers(), id)) {
                    id++;
                }
                newGameEvent.addPlayer(new CPUPlayer(id, "CPU " + id));
            }
        }

        GameEventBus.getInstance().post(new RoosterPlayersUpdatedEvent(newGameEvent.getPlayers(), false));
    }

    // Checks in a set of players whether a specific ID is present
    private boolean playerIDAvailable(Set<Player> playerSet, int ID) {
        for (Player player : playerSet) {
            if (player.getPlayerID() == ID) {
                return false;
            }
        }
        return true;
    }

    // Checks if a player is chosen at a given place in the rooster
    private boolean playerIsChosen(int current, int player) {
        for (int i = 0; i < this.selectedPlayers.length; i++) {
            if (current != i && this.selectedPlayers[i] == player && player != 5) {
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

    @Override
    public void initMenu(NewGameEvent newGameEvent) {

    }
}
