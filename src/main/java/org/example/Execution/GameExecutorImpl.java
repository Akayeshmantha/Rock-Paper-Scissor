package org.example.Execution;

import org.example.Player.FixedPlayer;
import org.example.Player.RandomPlayer;
import org.example.enums.Move;
import org.example.enums.Result;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class GameExecutorImpl implements GameExecutor {
    final RandomPlayer randomPlayer;
    final FixedPlayer fixedPlayer;
    final int noOfThreads = Runtime.getRuntime().availableProcessors() - 1;
    final int cycles;
    final ExecutorService executorService =  Executors.newFixedThreadPool(noOfThreads);
    static volatile ConcurrentMap<Result, AtomicInteger> resultMap;

    public GameExecutorImpl(final RandomPlayer randomPlayer, final FixedPlayer fixedPlayer, final int cycles) {
        this.randomPlayer = randomPlayer;
        this.fixedPlayer = fixedPlayer;
        this.cycles = cycles;
        resultMap = new ConcurrentHashMap<>();
    }

    @Override
    public ConcurrentMap<Result, AtomicInteger> executeRockPaperScissors() {
        IntStream.range(0, this.cycles).parallel().forEach(value -> {
            executorService.execute(randomPlayer);
            Move move1;
            Move move2;

            try {
                move1 = randomPlayer.getMove();
                move2 = fixedPlayer.getMove();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            final Result results = Result.getRoundResult(move1, move2);
            // known issue in concurrent hashmap retry should work in catch
            try {
                Objects.requireNonNull(resultMap.putIfAbsent(results, new AtomicInteger(0))).incrementAndGet();
            } catch (NullPointerException np) {
                Objects.requireNonNull(resultMap.putIfAbsent(results, new AtomicInteger(0))).incrementAndGet();
            }
        });
        executorService.shutdown();
        return resultMap;
    }
}
