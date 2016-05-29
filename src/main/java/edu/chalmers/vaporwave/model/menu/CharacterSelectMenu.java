package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.model.CPUPlayer;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.ClonerUtility;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Debug;
import edu.chalmers.vaporwave.util.Utils;

/**
 * In this menu, all human players chooses which character they would want to play with.
 * After every active player has chosen, if there are any CPU players, their characters are
 * randomized between the remaining unclaimed characters.
 */
public class CharacterSelectMenu extends AbstractMenu {

    private int[] selectedCharacters;
    private static final String[] characterNames = { "MEI", "ALYSSA", "ZYPHER", "CHARLOTTE" };
    SoundID[] soundIDs = {SoundID.MENU_MEI, SoundID.MENU_ALYSSA,SoundID.MENU_ZYPHER,SoundID.MENU_CHARLOTTE};

    public CharacterSelectMenu() {
        super(new int[]{0, 3, 0}, 1);

        // Set selected players to -1 for all characters
        selectedCharacters = new int[] {-1, -1, -1, -1};
    }

    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_BACKWARD_CLICK);
            return MenuState.ROOSTER;
        } else if (getSelectedSuper() == 2) {
            return MenuState.START_GAME;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        if (Debug.PRINT_LOG) {
            System.out.println("performMenuAction in CharacterSelectMenu. Player ID : " + playerID);
        }

        if (this.getSelectedSuper() == 1 && playerID == 0 && this.selectedCharacters[getSelectedSub()[1]] == -1) {
            unselectCharacterForPlayer(playerID);
            this.selectedCharacters[getSelectedSub()[1]] = 0;
            newGameEvent.getPrimaryPlayer().setCharacter(new GameCharacter(characterNames[this.getSelectedSub()[1]], 0));
            Container.playSound(soundIDs[this.getSelectedSub()[1]]);

        } else if (playerID >= 1 && this.selectedCharacters[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)] == -1) {
            unselectCharacterForPlayer(playerID);
            this.selectedCharacters[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)] = playerID;

            for (Player player : newGameEvent.getPlayers()) {
                if (player.getPlayerID() == playerID) {
                    player.setCharacter(new GameCharacter(characterNames[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)], playerID));
                    Container.playSound(soundIDs[Utils.calculateRemoteSelected(this.getRemoteSelected(), playerID, Constants.MAX_NUMBER_OF_PLAYERS)]);
                }
            }
        }
    }

    // When initializing this screen, makes sure that if a player has dropped out, its
    // chosen character is released to be claimed by other players
    @Override
    public void initMenu(NewGameEvent newGameEvent) {
        for (int i = 0; i < this.selectedCharacters.length; i++) {
            if (this.selectedCharacters[i] != -1) {
                boolean playerStillHere = false;
                for (Player player : newGameEvent.getPlayers()) {
                    if (!(player instanceof CPUPlayer) && player.getPlayerID() == this.selectedCharacters[i]) {
                        playerStillHere = true;
                    }
                }
                if (!playerStillHere) {
                    this.selectedCharacters[i] = -1;
                }
            }
        }
    }

    // Special selection in this menu, as this is the only menu where other players than primary
    // can input and make choices.
    @Override
    public void changeSelected(Direction direction, Player player) {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {

            moveDirection(direction, player);
            if (player.getPlayerID() == 0 && getSelectedSuper() == 1) {
                while (this.selectedCharacters[getSelectedSub()[1]] > 0) {

                    moveDirection(direction, player);
                }
            } else {
                while (this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(),
                        player.getPlayerID(), Constants.MAX_NUMBER_OF_PLAYERS)] != -1
                        && this.selectedCharacters[Utils.calculateRemoteSelected(getRemoteSelected(),
                        player.getPlayerID(), Constants.MAX_NUMBER_OF_PLAYERS)] != player.getPlayerID()) {

                    moveDirection(direction, player);
                }
            }

        } else {
            super.changeSelected(direction, player);
        }
    }

    private void moveDirection(Direction direction, Player player) {
        switch (direction) {
            case LEFT:
                menuMoveLeft(player.getPlayerID());
                break;

            case RIGHT:
                menuMoveRight(player.getPlayerID());
                break;

            default:
        }
    }

    // Unselects a character for a given player
    private void unselectCharacterForPlayer(int playerID) {
        for (int i = 0; i < selectedCharacters.length; i++) {
            if (selectedCharacters[i] == playerID) {
                selectedCharacters[i] = -1;
            }
        }
    }

    public int[] getSelectedCharacters() {
        return ClonerUtility.intArrayCloner(this.selectedCharacters);
    }
}