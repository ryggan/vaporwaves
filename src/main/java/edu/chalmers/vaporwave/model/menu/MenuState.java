package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.view.GameMenuButton;
import javafx.scene.control.MenuButton;

public abstract class MenuState {

    GameMenuButton[] buttonList;
    GameMenuButton selectedButton;
    GameMenuButton lastSelectedButton;

    public MenuState(int arrayLength){
        buttonList=new GameMenuButton[arrayLength];
    }

    public GameMenuButton[] getButtonList(){
        return buttonList;
    }

    public void changeSelected(String key){
        lastSelectedButton=selectedButton;

        if(key=="DOWN") {
            if (getMenuButtonIndex(selectedButton) == buttonList.length - 1) {
                selectedButton = buttonList[0];
                System.out.println(getMenuButtonIndex(selectedButton));
            } else {
                selectedButton=buttonList[getMenuButtonIndex(selectedButton)+1];
                System.out.println(getMenuButtonIndex(selectedButton));
            }
        }else if(key=="UP"){
            if(getMenuButtonIndex(selectedButton)==0){
                selectedButton = buttonList[buttonList.length-1];
                System.out.println(getMenuButtonIndex(selectedButton));
            } else {
                selectedButton=buttonList[getMenuButtonIndex(selectedButton)-1];
                System.out.println(getMenuButtonIndex(selectedButton));
            }
        } else {
            //let it go
            //maybe show up correct keys
        }
    }

    public void setSelectedButton(GameMenuButton selectedButton){
        this.selectedButton=selectedButton;
    }

    public GameMenuButton getSelectedButton(){
        return selectedButton;
    }
    public GameMenuButton getLastSelectedButton(){
        return lastSelectedButton;
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
        System.out.println(index);
        return index;


    }

}
