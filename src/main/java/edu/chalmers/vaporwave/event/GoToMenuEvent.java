package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.menu.MenuState;

/**
 * This event is posted to the eventbus by MenuController every time it switches menu
 */
public class GoToMenuEvent {

    private MenuState activeMenu;

    public GoToMenuEvent(MenuState activeMenu){
        this.activeMenu = activeMenu;
    }

    public MenuState getActiveMenu(){
        return this.activeMenu;
    }
}
