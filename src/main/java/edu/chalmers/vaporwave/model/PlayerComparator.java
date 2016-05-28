package edu.chalmers.vaporwave.model;

import java.util.Comparator;

public enum PlayerComparator implements Comparator<Player> {

    ID_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.valueOf(player1.getPlayerID()).compareTo(player2.getPlayerID());
        }},

    SCORE_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.valueOf(player1.getScore()).compareTo(player2.getScore());
        }},

    CHARACTER_KILL_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.valueOf(player1.getKills()).compareTo(player2.getKills());
        }},

    ENEMY_KILL_SORT {
        public int compare(Player player1, Player player2) {
            return Integer.valueOf(player1.getCreeps()).compareTo(player2.getCreeps());
        }};

    public static Comparator<Player> decending(final Comparator<Player> other) {
        return new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                return -1 * other.compare(player1, player2);
            }
        };
    }

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