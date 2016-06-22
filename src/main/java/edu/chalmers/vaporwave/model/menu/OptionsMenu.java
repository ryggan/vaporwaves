package edu.chalmers.vaporwave.model.menu;

/**
 * Created by Lina on 2016-06-22.
 */
public class OptionsMenu extends AbstractMenu{


    public OptionsMenu() {
        super(new int[]{0, 0}, 0);
    }

    @Override
    public MenuState getMenuAction() {
        return null;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {

    }

    @Override
    public void initMenu(NewGameEvent newGameEvent) {

    }
}
