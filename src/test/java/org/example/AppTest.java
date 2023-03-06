package org.example;

import junit.framework.TestCase;
import org.example.Execution.GameExecutor;
import org.example.Execution.GameExecutorImpl;
import org.example.Player.FixedPlayer;
import org.example.Player.RandomPlayer;
import org.example.enums.Move;
import org.example.enums.Result;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    private static final String RANDOM_NAME = "RANDOM";
    private static final String FIXED_NAME = "FIXED";
    static volatile ConcurrentMap<Result, AtomicInteger> resultMap ;
    private FixedPlayer fixedPlayer;
    private RandomPlayer randomPlayer;
    private GameExecutor gameExecutor;
    private int cycles = 100;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    public  void testUser_ReturnsPlayerName() {
        fixedPlayer = new FixedPlayer(FIXED_NAME);
        randomPlayer = new RandomPlayer(RANDOM_NAME);
        assertEquals(randomPlayer.getPlayerName(), RANDOM_NAME);
        assertEquals(fixedPlayer.getPlayerName(), FIXED_NAME);
    }
    public void testFixedPlayer_MoveNonNull() throws InterruptedException {
        fixedPlayer = new FixedPlayer(FIXED_NAME);
        assertNotNull(fixedPlayer.getMove());
        assertEquals(Move.PAPER, fixedPlayer.getMove());
    }
    public void testRandomUser_ReturnsNonNullMove() throws InterruptedException {
        randomPlayer = new RandomPlayer(RANDOM_NAME);
        Thread thread = new Thread(randomPlayer);
        thread.start();
        System.out.println(randomPlayer.getMove());
        assertNotNull(randomPlayer.getMove());
    }

    public void testGameExecutor_ResultShouldNotBeNull() {
        fixedPlayer = new FixedPlayer(FIXED_NAME);
        randomPlayer = new RandomPlayer(RANDOM_NAME);
        gameExecutor = new GameExecutorImpl(randomPlayer, fixedPlayer, 100);
        resultMap = gameExecutor.executeRockPaperScissors();
        assertNotNull(resultMap);
    }

    public void testGameExecutor_ResultShouldContainResultEnums() {
        fixedPlayer = new FixedPlayer(FIXED_NAME);
        randomPlayer = new RandomPlayer(RANDOM_NAME);
        gameExecutor = new GameExecutorImpl(randomPlayer, fixedPlayer, 100);
        resultMap = gameExecutor.executeRockPaperScissors();
        assertTrue(resultMap.containsKey(Result.DRAW));
        assertTrue(resultMap.containsKey(Result.PLAYER2_WIN));
        assertTrue(resultMap.containsKey(Result.PLAYER1_WIN));
    }

    // since we care about 100 cycles of the game
    public void testGameExecutor_ShouldReturn100Results() {
        fixedPlayer = new FixedPlayer(FIXED_NAME);
        randomPlayer = new RandomPlayer(RANDOM_NAME);
        gameExecutor = new GameExecutorImpl(randomPlayer, fixedPlayer, 100);
        resultMap = gameExecutor.executeRockPaperScissors();
        assertEquals(100, resultMap.get(Result.PLAYER1_WIN).get() +
                resultMap.get(Result.PLAYER2_WIN).get() + resultMap.get(Result.DRAW).get() );
    }
}
