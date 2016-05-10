package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

public class StartMenu extends AbstractMenu {
    public StartMenu(NewGameEvent newGameEvent) {
        super(new int[]{1, 0}, newGameEvent);
    }


    public MenuAction getMenuAction() {
        if (this.getSelectedSuper() == 0) {
//            return MenuAction.NEXT;
            return MenuAction.START_GAME;
        } else if (this.getSelectedSuper() == 1) {
            return MenuAction.EXIT_PROGRAM;
        }

        return MenuAction.NO_ACTION;
    }
}
