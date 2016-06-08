package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.FileID;
import edu.chalmers.vaporwave.assetcontainer.SoundID;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaMapComparator;
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
        super(new int[]{0, Container.getAllMaps().size() - 1, 0}, 1);

        this.arenaMaps = new ArrayList<>();
        for (Map.Entry<FileID, File> entry : Container.getAllMaps().entrySet()) {
            this.arenaMaps.add(new ArenaMap(entry.getKey().toString(), (new MapFileReader(entry.getValue())).getMapObjects()));
        }
        this.arenaMaps.sort(new ArenaMapComparator());

        this.selectedMap = this.arenaMaps.get(0);
    }

    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            Container.playSound(SoundID.MENU_BACKWARD_CLICK);
            return MenuState.CHARACTER_SELECT;
        } else if (this.getSelectedSuper() == 2) {
            return MenuState.START_GAME;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    protected void menuMoveRight(int playerID) {
        super.menuMoveRight(playerID);
        this.selectedMap = this.arenaMaps.get(getSelectedSub()[1]);
    }

    @Override
    protected void menuMoveLeft(int playerID) {
        super.menuMoveLeft(playerID);
        this.selectedMap = this.arenaMaps.get(getSelectedSub()[1]);
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        if (getSelectedSuper() == 2) {
            newGameEvent.setArenaMap(this.selectedMap);
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
