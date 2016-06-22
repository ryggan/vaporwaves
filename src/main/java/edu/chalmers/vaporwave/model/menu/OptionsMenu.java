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
            return MenuState.ROOSTER;
        }else if (this.getSelectedSuper() == 1) {
            if(Container.isSoundMuted()){
                Container.setSoundMuted(false);
                Container.playSound(SoundID.MENU_BGM_1);
                System.out.println("unmuted");
            } else {
                Container.stopSound(SoundID.MENU_BGM_1);
                Container.setSoundMuted(true);
                System.out.println("muted");
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
