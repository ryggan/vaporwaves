package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.view.GameMenuButton;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import sun.management.GarbageCollectionNotifInfoCompositeData;

/**
 * Created by bob on 2016-04-15.
 */
public class StartMenu extends MenuState {

    GameMenuButton startGameButton;
    GameMenuButton optionsButton;

    private static StartMenu instance=new StartMenu();

    private StartMenu(){
        super(2);
        startGameButton=new GameMenuButton(new ImageView("images/startGameButton.png"), new ImageView("images/startGameButtonSelected.png"), new ImageView());
        optionsButton= new GameMenuButton(new ImageView(), new ImageView(), new ImageView());
        buttonList[0]=startGameButton;
        buttonList[1]=optionsButton;
        selectedButton=startGameButton;
    }

    public static StartMenu getInstance(){
        return instance;
    }


}
