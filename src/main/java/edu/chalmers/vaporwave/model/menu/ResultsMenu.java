package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

public class ResultsMenu extends AbstractMenu {

    public ResultsMenu(NewGameEvent newGameEvent) {
        super( new int[]{1, 0});
    }

    //exit

    @Override
    public MenuState getMenuAction(){
            if (this.getSelectedSuper() == 0) {
                return MenuState.START_MENU;
            }

            return MenuState.NO_ACTION;

    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {

    }

}
