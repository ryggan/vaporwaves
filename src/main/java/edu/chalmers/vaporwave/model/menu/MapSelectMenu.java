package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.FileID;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaMapComparator;
import edu.chalmers.vaporwave.model.RandomArenaMap;
import edu.chalmers.vaporwave.util.MapFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Rooster is a menu where the primary player decides how many other players that
 * should be playing, and if they should be human or CPU.
 */
public class MapSelectMenu extends AbstractMenu {

    private List<ArenaMap> arenaMaps;

    private ArenaMap selectedMap;

    public MapSelectMenu() {
        super(new int[]{0, Container.getAllMaps().size(), 1}, 1);

        this.arenaMaps = new ArrayList<>();
        this.arenaMaps.add(new RandomArenaMap());
        for (Map.Entry<FileID, File> entry : Container.getAllMaps().entrySet()) {
            this.arenaMaps.add(new ArenaMap(entry.getKey().toString(), (new MapFileReader(entry.getValue())).getMapObjects()));
        }
        this.arenaMaps.sort(new ArenaMapComparator());

        selectMap();

        setMenuItem(false, 2, 1);
    }

    public MenuState getMenuAction() {
        if (getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_BACKWARD_CLICK);
            return MenuState.CHARACTER_SELECT;

        } else if (getSelectedSuper() == 2) {
            if (getSubSelected() == 0) {
                return MenuState.START_GAME;

            } else if (getSubSelected() == 1) {
                Container.playSound(SoundID.MENU_FORWARD_CLICK);
            }
        }
        return MenuState.NO_ACTION;
    }

    @Override
    protected void menuMoveRight(int playerID) {
        super.menuMoveRight(playerID);
        selectMap();
    }

    @Override
    protected void menuMoveLeft(int playerID) {
        super.menuMoveLeft(playerID);
        selectMap();
    }

    private void selectMap() {
        this.selectedMap = this.arenaMaps.get(getSelectedSub()[1]);
        if (this.selectedMap instanceof RandomArenaMap) {
            ((RandomArenaMap) this.selectedMap).randomize();
            setMenuItem(false, 2, 1);
        } else {
            // todo: don't forget to put this in when implementing theme
//            setMenuItem(true, 2, 1);
        }
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        if (getSelectedSuper() == 1) {
            Container.playSound(SoundID.MENU_FORWARD_CLICK);
            setSuperSelected(2);
            setSubSelected(2, 0);

        } else if (getSelectedSuper() == 2) {
            if (getSubSelected() == 0) {
                selectMap();
                newGameEvent.setArenaMap(this.selectedMap);

            } else if (getSubSelected() == 1) {
                // todo: implement level changing theme
            }
        }
    }

    // This method is unused in this screen, but may get functionality in the future
    @Override
    public void initMenu(NewGameEvent newGameEvent) { }

    public List<ArenaMap> getArenaMaps() {
        List<ArenaMap> newList = new ArrayList<>();
        newList.addAll(this.arenaMaps);
        return newList;
    }
}
