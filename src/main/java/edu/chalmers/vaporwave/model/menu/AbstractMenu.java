package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;
import edu.chalmers.vaporwave.event.NewGameEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMenu {

    private int[] menuItems;
    private int selectedSuperItem;
    private int selectedSubItem;
    private NewGameEvent newGameEvent;

    public AbstractMenu(NewGameEvent newGameEvent, int[] menuItems) {
        this.selectedSuperItem = 0;
        this.selectedSubItem = 0;
        this.menuItems = menuItems;
        this.newGameEvent = newGameEvent;
    }

    public void changeSelected(Direction direction) {
        switch (direction) {
            case UP:
                if (selectedSuperItem > 0) {
                    selectedSuperItem -= 1;
                } else {
                    selectedSuperItem = menuItems.length - 1;
                }
                break;
            case DOWN:
                if (selectedSuperItem != menuItems.length - 1) {
                    selectedSuperItem += 1;
                } else {
                    selectedSuperItem = 0;
                }
            case LEFT:
                if (selectedSubItem > 0) {
                    selectedSubItem -= 1;
                } else {
                    selectedSubItem = menuItems[selectedSuperItem];
                }
                break;
            case RIGHT:
                if (selectedSubItem != menuItems[selectedSuperItem]) {
                    selectedSubItem += 1;
                } else {
                    selectedSuperItem = 0;
                }
        }
    }

    public int getSelectedSuper() {
        return this.selectedSuperItem;
    }

    public int getSelectedSub() {
        return this.selectedSubItem;
    }

    public abstract MenuAction getMenuAction();

    public NewGameEvent getNewGameEvent() {
        return this.newGameEvent;
    }

    public int[] getMenuItems() {
        return this.menuItems;
    }

}
