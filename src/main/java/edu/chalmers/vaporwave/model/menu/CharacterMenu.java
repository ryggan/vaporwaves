package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;
import edu.chalmers.vaporwave.view.MenuButtonView;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterMenu extends AbstractMenu {


    public CharacterMenu(NewGameEvent newGameEvent) {
        super(new int[]{0, 3, 0}, newGameEvent);



    }


    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            return MenuState.START_MENU;
        } else if (getSelectedSuper() == 2) {
            return MenuState.START_GAME;
        }
        return MenuState.NO_ACTION;
    }



}
