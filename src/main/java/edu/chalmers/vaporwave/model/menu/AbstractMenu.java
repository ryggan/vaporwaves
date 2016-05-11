package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.NewGameEvent;

public abstract class AbstractMenu {

    private int[] menuItems;
    private int[] selectedItems;
    private int currentSelected;
    private NewGameEvent newGameEvent;

    public AbstractMenu(int[] menuItems) {
        this(menuItems, null);
    }

    public AbstractMenu(int[] menuItems, NewGameEvent newGameEvent) {
        this.selectedItems = new int[menuItems.length];
        this.currentSelected = 0;
        this.menuItems = menuItems;
        this.newGameEvent = newGameEvent;
    }

    public void changeSelected(Direction direction) {
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
    }



    public int getSelectedSuper() {
        return this.currentSelected;
    }

    public int[] getSelectedSub() {
        return this.selectedItems;
    }

    public abstract MenuState getMenuAction();

    public NewGameEvent getNewGameEvent() {
        return this.newGameEvent;
    }

    public int[] getMenuItems() {
        return this.menuItems;
    }

}
