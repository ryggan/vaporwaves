package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;

/**
 * Created by Lina on 2016-06-22.
 */
public class ControlsMenu extends AbstractMenu {
    public ControlsMenu() {
        super(new int[]{0}, 0);
    }

    @Override
    public MenuState getMenuAction() {

        if (this.getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            return MenuState.OPTIONS_MENU;

        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {


    }

    @Override
    public void initMenu(NewGameEvent newGameEvent) {


    }
}
