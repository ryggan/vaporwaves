package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;

/**
 * Simple menu handling, based on its parent.
 */
public class StartMenu extends AbstractMenu {
    public StartMenu() {
        super(new int[]{0, 0, 0}, 0);

    }

    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            return MenuState.ROOSTER;
        }else if (this.getSelectedSuper() == 1) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            return MenuState.OPTIONS_MENU;
        } else if (this.getSelectedSuper() == 2) {
            return MenuState.EXIT_PROGRAM;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        System.out.println("Performing menu event in characterSelect");
    }

    // This method is unused in this screen, but may get functionality in the future
    @Override
    public void initMenu(NewGameEvent newGameEvent) { }
}
