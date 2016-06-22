package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.SoundID;

/**
 * Created by Lina on 2016-06-22.
 */
public class OptionsMenu extends AbstractMenu{


    public OptionsMenu() {
        super(new int[]{0, 0, 0}, 0);
    }

    @Override
    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);

        }else if (this.getSelectedSuper() == 1) {
            if(Container.isSoundMuted()){
                Container.setSoundMuted(false);
                Container.playSound(SoundID.MENU_BGM_1);
            } else {
                Container.setSoundMuted(true);
                Container.stopSound(SoundID.MENU_BGM_1);
            }
        } else if (this.getSelectedSuper() == 2) {
            Container.playSound(SoundID.MENU_BACKWARD_CLICK);
            return MenuState.START_MENU;
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
