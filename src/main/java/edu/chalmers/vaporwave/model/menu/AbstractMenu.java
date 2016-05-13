package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.NewGameEvent;

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
        this.menuItems = menuItems;
        this.newGameEvent = newGameEvent;
        this.remoteSelected = new int[4];
    }

    public void changeSelected(Direction direction, int playerID) {
        switch (direction) {
            case UP:
                menuMoveUp();
                break;
            case DOWN:
                menuMoveDown();
                break;
            case LEFT:
                menuMoveLeft(playerID);
                break;
            case RIGHT:
                menuMoveRight(playerID);
                break;
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
        return this.selectedItems;
    }

    public int[] getRemoteSelected() {
        return this.remoteSelected;
    }

    public abstract MenuState getMenuAction();

    public NewGameEvent getNewGameEvent() {
        return this.newGameEvent;
    }

    public int[] getMenuItems() {
        return this.menuItems;
    }

    public abstract void performMenuAction(NewGameEvent newGameEvent, int playerID);
}
