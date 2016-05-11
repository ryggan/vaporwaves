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


    public MenuAction getMenuAction() {
        if (getSelectedSuper() == 0) {

        } else if (getSelectedSuper() == 2) {
            return MenuAction.START_GAME;
        }
        return MenuAction.NO_ACTION;
    }



}
