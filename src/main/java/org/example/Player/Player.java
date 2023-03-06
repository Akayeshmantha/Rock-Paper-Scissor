package org.example.Player;

import org.example.enums.Move;

public class Player{
    private String playerName;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public Move getMove() throws InterruptedException{
        return Move.PAPER;
    }

    public String getPlayerName() {
        return playerName;
    }
}
