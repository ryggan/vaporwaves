package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.menu.MenuState;

public class GoToMenuEvent {

    private MenuState activeMenu;

    public GoToMenuEvent() {

    }

    public GoToMenuEvent(MenuState activeMenu){
        this.activeMenu = activeMenu;
    }

    public MenuState getActiveMenu(){
        return activeMenu;
    }
}
