package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.ClonerUtility;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the template for every menu screen model; most things are the same but every
 * menu screen should have different actions and so on, which is implemented in its
 * subclasses.
 */
public abstract class AbstractMenu {

    private List<boolean[]> menuItems;
//    private int[] menuItems;
    private int[] selectedItems;
    private int currentSelected;
    private int[] remoteSelected;

    public AbstractMenu(int[] menuItems) {
        this(menuItems, 0);
    }

    public AbstractMenu(int[] menuItems, int currentSelected) {
        this.selectedItems = new int[menuItems.length];
        this.currentSelected = currentSelected;
//        this.menuItems = ClonerUtility.intArrayCloner(menuItems);
        this.remoteSelected = new int[Constants.MAX_NUMBER_OF_PLAYERS];

        this.menuItems = new ArrayList<>();
        for (int i = 0; i < menuItems.length; i++) {
            boolean[] menuRow = new boolean[menuItems[i] + 1];
            for (int j = 0; j < menuRow.length; j++) {
                menuRow[j] = true;
            }
            this.menuItems.add(menuRow);
        }
    }

    // When a player presses a directional button, the following happens.
    // First, simply check where to go, then move menu cursor in that way.
    // Only primary player should be able to move up and down, but when moving left and right,
    // it gets a bit messier, because of Character Select, where every player chooses his/her
    // character individually.
    public void changeSelected(Direction direction, Player player) {
        switch (direction) {
            case UP:
                menuMoveUp();
                break;
            case DOWN:
                menuMoveDown();
                break;
            case LEFT:
                menuMoveLeft(player.getPlayerID());
                break;
            case RIGHT:
                menuMoveRight(player.getPlayerID());
                break;
            default:
        }
    }

    protected void menuMoveUp() {
        if (currentSelected > 0) {
            currentSelected -= 1;
        } else {
            currentSelected = menuItems.size() - 1;
        }
    }

    protected void menuMoveDown() {
        if (currentSelected != menuItems.size() - 1) {
            currentSelected += 1;
        } else {
            currentSelected = 0;
        }
    }

    protected void menuMoveRight(int playerID) {
        if (playerID == 0) {
            if (selectedItems[currentSelected] < menuItems.get(currentSelected).length - 1) {
                selectedItems[currentSelected] += 1;
            } else {
                if (menuItems.get(currentSelected).length - 1 > 0) {
                    selectedItems[currentSelected] = 0;
                } else {
                    menuMoveDown();
                }
            }
        } else {
            remoteSelected[playerID] += 1;
        }
    }

    protected void menuMoveLeft(int playerID) {
        if (playerID == 0) {
            if (selectedItems[currentSelected] > 0) {
                selectedItems[currentSelected] -= 1;
            } else {
                if (menuItems.get(currentSelected).length - 1 > 0) {
                    selectedItems[currentSelected] = menuItems.get(currentSelected).length - 1;
                } else {
                    menuMoveUp();
                }
            }
        } else {
            remoteSelected[playerID] -= 1;
        }
    }

    // Gets and sets. Super selected means vertical selection, sub selected, means horizontal selection,
    // and remote selected is what all the secondary players has selected.
    public int getSelectedSuper() {
        return this.currentSelected;
    }

    public void setSuperSelected(int superSelected) {
        this.currentSelected = Math.max(Math.min(superSelected, 0), this.menuItems.size() - 1);
    }

    public int[] getSelectedSub() {
        return ClonerUtility.intArrayCloner(this.selectedItems);
    }

    public int[] getRemoteSelected() {
        return ClonerUtility.intArrayCloner(this.remoteSelected);
    }

    public abstract MenuState getMenuAction();

    public List<boolean[]> getMenuItems() {
        List<boolean[]> newList = new ArrayList<>();
        for (boolean[] row : this.menuItems) {
            boolean[] newRow = new boolean[row.length];
            for (int i = 0; i < row.length; i++) {
                newRow[i] = row[i];
            }
            newList.add(newRow);
        }
        return newList;
    }

    public abstract void performMenuAction(NewGameEvent newGameEvent, int playerID);

    public abstract void initMenu(NewGameEvent newGameEvent);
}
