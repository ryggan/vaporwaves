package edu.chalmers.vaporwave.model;

import java.util.Comparator;

/**
 * A custom comparator for sorting lists with Players in them, by different values
 * depending on which game type that was played, or if just by ID, and so on.
 */
public class ArenaMapComparator implements Comparator<ArenaMap> {
    public int compare(ArenaMap arenaMap1, ArenaMap arenaMap2) {
        return arenaMap1.getName().compareTo(arenaMap2.getName());
    }
}