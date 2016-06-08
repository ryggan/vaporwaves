package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.FileID;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.util.MapFileReader;

/**
 * The Rooster is a menu where the primary player decides how many other players that
 * should be playing, and if they should be human or CPU.
 */
public class MapSelectMenu extends AbstractMenu {

    public MapSelectMenu() {
        super(new int[]{0, Container.getAllMaps().size(), 0}, 1);

        System.out.println("Total maps: "+Container.getAllMaps().size());

//        ArenaMap arenaMap = new ArenaMap("default",
//                (new MapFileReader(Container.getMap(FileID.VAPORMAP_DEFAULT))).getMapObjects());
    }

    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_BACKWARD_CLICK);
            return MenuState.START_MENU;
        } else if (this.getSelectedSuper() == 2) {
            return MenuState.START_GAME;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
//        int subSelect = this.getSelectedSub()[1];
//        if (subSelect > 0) {
//            Container.playSound(SoundID.MENU_FORWARD_CLICK);
//            do {
//                this.selectedPlayers[subSelect] += 1;
//            } while (playerIsChosen(subSelect, this.selectedPlayers[subSelect]));
//
//            if (this.selectedPlayers[subSelect] > 5) {
//                this.selectedPlayers[subSelect] = 0;
//            }
//            updatePlayers(newGameEvent);
//        }
    }

    // This method is unused in this screen, but may get functionality in the future
    @Override
    public void initMenu(NewGameEvent newGameEvent) { }
}
