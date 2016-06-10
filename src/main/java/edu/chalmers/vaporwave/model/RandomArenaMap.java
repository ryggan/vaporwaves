package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.RandomMapGenerator;

public class RandomArenaMap extends ArenaMap {

    RandomMapGenerator mapGenerator;

    public RandomArenaMap() {
        randomize();
    }

    public void randomize() {
        this.mapGenerator = new RandomMapGenerator();
        setNewMap("Random", this.mapGenerator.generateMap());
    }
}
