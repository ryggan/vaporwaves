package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

public class ResultsMenu extends AbstractMenu {

    public ResultsMenu(NewGameEvent newGameEvent) {
        super( new int[]{0, 3, 0});
    }



    @Override
    public MenuState getMenuAction() {
        return null;
    }
}
