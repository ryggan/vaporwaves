package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.view.GameMenuButton;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import sun.management.GarbageCollectionNotifInfoCompositeData;

/**
 * Created by bob on 2016-04-15.
 */
public class StartMenu implements MenuState {

    GameMenuButton startGameButton;
    GameMenuButton optionsButton;
    GameMenuButton[] buttonList=new GameMenuButton[2];

    GameMenuButton selectedButton;

    private StartMenu instance=new StartMenu();

    private StartMenu(){
        startGameButton=new GameMenuButton(new ImageView(), new ImageView(), new ImageView());
        optionsButton= new GameMenuButton(new ImageView(), new ImageView(), new ImageView());
        buttonList[0]=startGameButton;
        buttonList[1]=optionsButton;
        selectedButton=buttonList[0];
    }

    public StartMenu getInstance(){
        return instance;
    }

    private GameMenuButton[] getButtonList(){
        return buttonList;
    }

    private void changeSelected(String key){

        if(key=="DOWN") {
            if (getMenuButtonIndex(selectedButton) == buttonList.length - 1) {
                selectedButton = buttonList[0];
            } else {
                selectedButton=buttonList[getMenuButtonIndex(selectedButton)+1];
            }
        }else if(key=="UP"){
            if(getMenuButtonIndex(selectedButton)==0){
                selectedButton = buttonList[buttonList.length-1];
            } else {
                selectedButton=buttonList[getMenuButtonIndex(selectedButton)-1];
            }
        } else {
            //let it go
            //maybe show up correct keys
        }
    }

    private void setSelectedButton(GameMenuButton selectedButton){
        this.selectedButton=selectedButton;
    }

    private GameMenuButton getSelectedButton(){
        return selectedButton;
    }

    //TODO
    private int getMenuButtonIndex(GameMenuButton button) throws IllegalArgumentException {
        boolean found = false;
        int index=-1;
            for (int i = 0; i < buttonList.length; i++) {
                if (buttonList[i].equals(button)) {
                    found = true;
                    index=i;
                }
            }
        return index;

    }
}
