package org.example.enums;

import java.util.Random;

public enum Move {
    ROCK,
    PAPER,
    SCISSORS;

    /**
     * Choose random choice for player
     * @return random choice
     */
    public static Move RandomChoice(){
        final Move[] s = Move.values();
        final Random selection = new Random();
        return s[selection.nextInt(s.length)];
    }
}