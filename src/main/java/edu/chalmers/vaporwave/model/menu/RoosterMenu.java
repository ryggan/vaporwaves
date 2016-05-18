package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

public class RoosterMenu extends AbstractMenu {

    public RoosterMenu(NewGameEvent newGameEvent) {
        super(new int[]{0, 0}, newGameEvent, 0);
    }

    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            return MenuState.START_MENU;
        } else if (getSelectedSuper() == 1) {
            return MenuState.CHARACTER_SELECT;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        System.out.println("Performing menu event in rooster");
    }
}
