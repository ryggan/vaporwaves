package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.ArrayCloner;
import edu.chalmers.vaporwave.util.Constants;

public abstract class AbstractMenu {

    private int[] menuItems;
    private int[] selectedItems;
    private int currentSelected;
    private int[] remoteSelected;
    private NewGameEvent newGameEvent;

    public AbstractMenu(int[] menuItems) {
        this(menuItems, null, 0);
    }

    public AbstractMenu(int[] menuItems, NewGameEvent newGameEvent, int currentSelected) {
        this.selectedItems = new int[menuItems.length];
        this.currentSelected = currentSelected;
        this.menuItems = ArrayCloner.intArrayCloner(menuItems);
        this.newGameEvent = newGameEvent;
        this.remoteSelected = new int[Constants.MAX_NUMBER_OF_PLAYERS];
    }

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
            currentSelected = menuItems.length - 1;
        }
    }

    protected void menuMoveDown() {
        if (currentSelected != menuItems.length - 1) {
            currentSelected += 1;
        } else {
            currentSelected = 0;
        }
    }

    protected void menuMoveRight(int playerID) {
        if (playerID == 0) {
            if (selectedItems[currentSelected] < menuItems[currentSelected]) {
                selectedItems[currentSelected] += 1;
            } else {
                if (menuItems[currentSelected] > 0) {
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
                if (menuItems[currentSelected] > 0) {
                    selectedItems[currentSelected] = menuItems[currentSelected];
                } else {
                    menuMoveUp();
                }
            }
        } else {
            remoteSelected[playerID] -= 1;
        }
    }


    public int getSelectedSuper() {
        return this.currentSelected;
    }

    public int[] getSelectedSub() {
        return ArrayCloner.intArrayCloner(this.selectedItems);
    }

    public int[] getRemoteSelected() {
        return ArrayCloner.intArrayCloner(this.remoteSelected);
    }

    public abstract MenuState getMenuAction();

    public NewGameEvent getNewGameEvent() {
        return this.newGameEvent;
    }

    public int[] getMenuItems() {
        return ArrayCloner.intArrayCloner(this.menuItems);
    }

//    public int[] intArrayCloner(int[] intArray) {
//        int[] temporary = new int[intArray.length];
//        for(int i = 0; i < intArray.length; i++) {
//            temporary[i] = intArray[i];
//        }
//        return temporary;
//    }

    public abstract void performMenuAction(NewGameEvent newGameEvent, int playerID);
}
