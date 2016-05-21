package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.menu.MenuState;

public class ExitToMenuEvent {

    private MenuState destinationMenu;

    public ExitToMenuEvent(MenuState destinationMenu) {
        this.destinationMenu = destinationMenu;
    }

    public MenuState getDestinationMenu() {
        return this.destinationMenu;
    }

}
