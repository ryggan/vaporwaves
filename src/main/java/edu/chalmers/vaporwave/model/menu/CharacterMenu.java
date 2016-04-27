package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.event.NewGameEvent;

public class CharacterMenu extends AbstractMenu {

    public CharacterMenu(NewGameEvent newGameEvent) {
        super(newGameEvent, new int[]{0, 2, 0});


    }


    public MenuAction getMenuAction() {
        if (getSelectedSuper() == 0) {
            return MenuAction.PREVIOUS;
        } else if (getSelectedSuper() == 2) {
            return MenuAction.NEXT;
        }
        return MenuAction.NO_ACTION;
    }



}
