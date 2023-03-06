package org.example.enums;

public enum Result {
    PLAYER1_WIN,
    PLAYER2_WIN,
    DRAW;

    public static Result getRoundResult(final Move firstMove, final Move secondMove) {
        Result result = PLAYER2_WIN;
        if(firstMove == Move.ROCK && secondMove == Move.PAPER) {
            result = PLAYER2_WIN;
        } else if(firstMove == Move.ROCK && secondMove == Move.SCISSORS) {
            result = PLAYER1_WIN;
        } else if(firstMove == Move.ROCK && secondMove == Move.ROCK) {
            result = DRAW;
        } else if(firstMove == Move.PAPER && secondMove == Move.PAPER) {
            result = DRAW;
        } else if(firstMove == Move.PAPER && secondMove == Move.SCISSORS) {
            result = PLAYER2_WIN;
        } else if(firstMove == Move.PAPER && secondMove == Move.ROCK) {
            result = PLAYER1_WIN;
        }else if(firstMove == Move.SCISSORS && secondMove == Move.PAPER) {
            result = PLAYER1_WIN;
        } else if(firstMove == Move.SCISSORS && secondMove == Move.SCISSORS) {
            result = DRAW;
        } else if(firstMove == Move.SCISSORS && secondMove == Move.ROCK) {
            result = PLAYER2_WIN;
        }
        return result;
    }
}
