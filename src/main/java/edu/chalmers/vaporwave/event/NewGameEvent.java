package edu.chalmers.vaporwave.event;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.PowerUp;

/**
 * Created by andreascarlsson on 2016-04-21.
 */
public class NewGameEvent {
    private ArenaMap arenaMap;
    private GameCharacter gameCharacter;
//    private enabledPowerUps = List<>

    public int getId() {
        return 15;
    }

}
