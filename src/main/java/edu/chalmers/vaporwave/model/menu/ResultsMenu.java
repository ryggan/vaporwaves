package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

/**
 * Created by Lina on 5/10/2016.
 */
public class ResultsMenu extends AbstractMenu {

    public ResultsMenu(NewGameEvent newGameEvent) {
        super( new int[]{0, 3, 0});
    }



    @Override
    public MenuAction getMenuAction() {
        return null;
    }
}
