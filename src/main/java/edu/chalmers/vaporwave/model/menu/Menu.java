package edu.chalmers.vaporwave.model.menu;

import com.sun.javafx.scene.traversal.Direction;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private int selectedItem;
    private int menuItems;

    public Menu(int menuItems){
        this.selectedItem = 0;
        this.menuItems = menuItems;
    }

    public void changeSelected(Direction direction) {
        switch (direction) {
            case UP:
                if (selectedItem > 0) {
                    selectedItem -= 1;
                } else {
                    selectedItem = menuItems;
                }
                break;
            case DOWN:
                if (selectedItem != menuItems) {
                    selectedItem += 1;
                } else {
                    selectedItem = 0;
                }
        }
    }

    public int getSelected() {
        return this.selectedItem;
    }

}
