package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

public class StartMenu extends AbstractMenu {
    public StartMenu(NewGameEvent newGameEvent) {
        super(new int[]{1, 0}, newGameEvent);
    }

    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            return MenuState.CHARACTER_SELECT;
        } else if (this.getSelectedSuper() == 1) {
            return MenuState.EXIT_PROGRAM;
        }
        return MenuState.NO_ACTION;

    }
}
