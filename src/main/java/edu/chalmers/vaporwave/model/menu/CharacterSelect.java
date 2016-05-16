package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ArrayCloner;
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
        if (this.getSelectedSuper() == 1 && playerID == 0 && this.selectedCharacters[getSelectedSub()[1]] == -1) {
            unselectCharacterForPlayer(playerID);
            this.selectedCharacters[getSelectedSub()[1]] = 0;
            newGameEvent.getLocalPlayer().setCharacter(new GameCharacter(characterNames[this.getSelectedSub()[1]], 0));
        } else if (playerID >= 1 && this.selectedCharacters[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, 4)] == -1) {
            unselectCharacterForPlayer(playerID);
            this.selectedCharacters[Utils.calculateRemoteSelected(this.getRemoteSelected(), 1, 4)] = playerID;
            newGameEvent.getRemotePlayer().setCharacter(new GameCharacter(characterNames[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, 4)], 0));
        }
    }

    public void changeSelected(Direction direction, int playerID) {
        switch (direction) {
            case LEFT:
                menuMoveLeft(playerID);
                if (playerID == 0 && getSelectedSuper() == 1) {
                    while (this.selectedCharacters[getSelectedSub()[1]] > 0) {
                        menuMoveLeft(playerID);
                    }
                } else {
                    while (this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), playerID, 4)] != -1 &&
                            this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), playerID, 4)] != playerID) {
                        menuMoveLeft(playerID);
                    }
                }

                break;
            case RIGHT:

                menuMoveRight(playerID);
                if (playerID == 0 && getSelectedSuper() == 1) {
                    while (this.selectedCharacters[getSelectedSub()[1]] > 0) {
                        menuMoveRight(playerID);
                    }
                } else {
                    while (this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), playerID, 4)] != -1 &&
                            this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(), playerID, 4)] != playerID) {
                        menuMoveRight(playerID);
                    }
                }

                break;
            default:
                super.changeSelected(direction, playerID);
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