package org.example;

import org.example.Execution.GameExecutor;
import org.example.Execution.GameExecutorImpl;
import org.example.Player.FixedPlayer;
import org.example.Player.RandomPlayer;
import org.example.enums.Result;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Executable mvn archetype
 * @Devloped by AKA Perera
 * ROCK PAPER SCISSOR Concurrent solution
 */
public class App {
    static volatile ConcurrentMap<Result, AtomicInteger> resultMap ;
    public static void main(String[] args) {
        final int cycles = 100;
        final RandomPlayer randomPlayer1 = new RandomPlayer("Player A");
        final FixedPlayer fixedPlayer = new FixedPlayer("Player B");
        final GameExecutor executorImpl = new GameExecutorImpl(randomPlayer1, fixedPlayer, cycles);
        resultMap = executorImpl.executeRockPaperScissors();

        System.out.printf("%s wins %3d of 100 games\n" +
                "%s wins %3d of 100 games\n" +
                "Tie %3d of games\n\n", randomPlayer1.getPlayerName(),
                resultMap.get(Result.PLAYER1_WIN).get(), fixedPlayer.getPlayerName(),
                resultMap.get(Result.PLAYER2_WIN).get(), resultMap.get(Result.DRAW).get());
    }
}
