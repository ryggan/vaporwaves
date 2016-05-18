package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import com.sun.tools.internal.jxc.ap.Const;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Debug;
import edu.chalmers.vaporwave.util.Utils;

public class CharacterSelect extends AbstractMenu {

    private int[] selectedCharacters;
    private static final String[] characterNames = { "MEI", "ALYSSA", "ZYPHER", "CHARLOTTE" };

    public CharacterSelect(NewGameEvent newGameEvent) {
        super(new int[]{0, 3, 0}, newGameEvent, 1);

        // Set selected characters to -1 for all players
        selectedCharacters = new int[] {-1, -1, -1, -1};
    }


    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            return MenuState.START_MENU;
        } else if (getSelectedSuper() == 2) {
            return MenuState.START_GAME;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        if (Debug.PRINT_LOG) {
            System.out.println("performMenuAction in CharacterSelect. Player ID : " + playerID);
        }

        if (this.getSelectedSuper() == 1 && playerID == 0 && this.selectedCharacters[getSelectedSub()[1]] == -1) {
            unselectCharacterForPlayer(playerID);
            this.selectedCharacters[getSelectedSub()[1]] = 0;
            newGameEvent.getLocalPlayer().setCharacter(new GameCharacter(characterNames[this.getSelectedSub()[1]], 0));
        } else if (playerID >= 1 && this.selectedCharacters[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)] == -1) {
            unselectCharacterForPlayer(playerID);
            this.selectedCharacters[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)] = playerID;

            for (Player player : newGameEvent.getPlayers()) {
                if (player.getPlayerID() == playerID) {
                    player.setCharacter(new GameCharacter(characterNames[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)], playerID));
                }
            }
        }
    }

    public void changeSelected(Direction direction, Player player) {
        switch (direction) {
            // todo: Break out into helper method
            case LEFT:
                menuMoveLeft(player.getPlayerID());
                if (player.getPlayerID() == 0 && getSelectedSuper() == 1) {
                    while (this.selectedCharacters[getSelectedSub()[1]] > 0) {
                        menuMoveLeft(player.getPlayerID());
                    }
                } else {
                    while (this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), player.getPlayerID(), Constants.MAX_NUMBER_OF_PLAYERS)] != -1 &&
                            this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), player.getPlayerID(), Constants.MAX_NUMBER_OF_PLAYERS)] != player.getPlayerID()) {
                        menuMoveLeft(player.getPlayerID());
                    }
                }

                break;
            case RIGHT:

                menuMoveRight(player.getPlayerID());
                if (player.getPlayerID() == 0 && getSelectedSuper() == 1) {
                    while (this.selectedCharacters[getSelectedSub()[1]] > 0) {
                        menuMoveRight(player.getPlayerID());
                    }
                } else {
                    while (this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), player.getPlayerID(), Constants.MAX_NUMBER_OF_PLAYERS)] != -1 &&
                            this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), player.getPlayerID(), Constants.MAX_NUMBER_OF_PLAYERS)] != player.getPlayerID()) {
                        menuMoveRight(player.getPlayerID());
                    }
                }

                break;
            default:
                super.changeSelected(direction, player);
                break;
        }
    }

    private void unselectCharacterForPlayer(int playerID) {
        for (int i = 0; i < selectedCharacters.length; i++) {
            if (selectedCharacters[i] == playerID) {
                selectedCharacters[i] = -1;
            }
        }
    }

    public int[] getSelectedCharacters() {
        return ArrayCloner.intArrayCloner(this.selectedCharacters);
    }
}