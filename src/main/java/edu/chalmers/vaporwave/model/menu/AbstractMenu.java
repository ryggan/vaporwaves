package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.util.ArrayCloner;

public abstract class AbstractMenu {

    private int[] menuItems;
    private int[] selectedItems;
    private int currentSelected;
    private int[] remotePlayersSelected;
    private NewGameEvent newGameEvent;

    public AbstractMenu(int[] menuItems) {
        this(menuItems, null);
    }

    public AbstractMenu(int[] menuItems, NewGameEvent newGameEvent) {
        this.selectedItems = new int[menuItems.length];
        this.currentSelected = 0;
        this.menuItems = ArrayCloner.intArrayCloner(menuItems);
        this.newGameEvent = newGameEvent;
        this.remotePlayersSelected = new int[]{0, 0, 0, 0};
    }

    public void changeSelected(Direction direction, int playerID) {
        if(playerID == 0) {
            switch (direction) {
                case UP:
                    if (currentSelected > 0) {
                        currentSelected -= 1;
                    } else {
                        currentSelected = menuItems.length - 1;
                    }
                    break;
                case DOWN:
                    if (currentSelected != menuItems.length - 1) {
                        currentSelected += 1;
                    } else {
                        currentSelected = 0;
                    }
                    break;
                case LEFT:
                    if (selectedItems[currentSelected] > 0) {
                        selectedItems[currentSelected] -= 1;
                    } else {
                        selectedItems[currentSelected] = menuItems[currentSelected];
                    }
                    break;
                case RIGHT:
                    if (selectedItems[currentSelected] < menuItems[currentSelected]) {
                        selectedItems[currentSelected] += 1;
                    } else {
                        selectedItems[currentSelected] = 0;
                    }
                    break;
            }
        } else {

            // todo: Only for character select, needs to be refactored if other player should be able to choose maps etc
            switch (direction) {
                case LEFT:
                    if (this.remotePlayersSelected[playerID] == 0) {
                        this.remotePlayersSelected[playerID] = 4;
                    } else {
                        this.remotePlayersSelected[playerID] -= 1;
                    }
                    break;
                case RIGHT:
                    if (this.remotePlayersSelected[playerID] == 4) {
                        this.remotePlayersSelected[playerID] = 0;
                    } else {
                        this.remotePlayersSelected[playerID] += 1;
                    }
                    break;
            }
        }
    }



    public int getSelectedSuper() {
        return this.currentSelected;
    }

    public int[] getSelectedSub() {
        return intArrayCloner(this.selectedItems);
    }

    public abstract MenuState getMenuAction();

    public NewGameEvent getNewGameEvent() {
        return this.newGameEvent;
    }

    public int[] getMenuItems() {
        return intArrayCloner(this.menuItems);
    }

    public int[] intArrayCloner(int[] intArray) {
        int[] temporary = new int[intArray.length];
        for(int i = 0; i < intArray.length; i++) {
            temporary[i] = intArray[i];
        }
        return temporary;
    }

}
