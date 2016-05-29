package edu.chalmers.vaporwave.model;

import java.util.Comparator;

/**
 * A custom comparator for sorting lists with Players in them, by different values
 * depending on which game type that was played, or if just by ID, and so on.
 */
public enum PlayerComparator implements Comparator<Player> {

    // The different sorting settings
    ID_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.compare(player1.getPlayerID(), player2.getPlayerID());
        }},

    SCORE_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.compare(player1.getScore(), player2.getScore());
        }},

    CHARACTER_KILL_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.compare(player1.getKills(), player2.getKills());
        }},

    ENEMY_KILL_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.compare(player1.getCreeps(), player2.getCreeps());
        }};

    // A way of reversing the sorted order, if needed
    public static Comparator<Player> decending(final Comparator<Player> other) {
        return new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                return -1 * other.compare(player1, player2);
            }
        };
    }

    // This is the method that needs to be called when creating the custom comparator
    public static Comparator<Player> getComparator(final PlayerComparator... multipleOptions) {
        return new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                for (PlayerComparator option : multipleOptions) {
                    int result = option.compare(player1, player2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }
}