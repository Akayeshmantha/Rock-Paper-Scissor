package org.example.Player;

import org.example.enums.Move;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RandomPlayer extends Player implements Runnable {
    private Move move;
//    private static final Object lock = new Object();

    private static final Lock lock = new ReentrantLock(true);
    private static final Condition isSet = lock.newCondition();

    public RandomPlayer(String playerName) {
        super(playerName);
    }

    @Override
    public Move getMove() throws InterruptedException {
        try{
            lock.lock();
            isSet.await(5, TimeUnit.MILLISECONDS);
            return this.move;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
//        just moved out from synchronization due to performance gain in lock api
//         still this will work if uncommented
//        synchronized (lock) {
//            lock.wait();
//            return this.move;
//        }
        return Move.PAPER;
    }

    @Override
    public void run() {
        try{
            lock.lock();
            move = Move.RandomChoice();
            isSet.signal();
        } finally {
            lock.unlock();
        }
//        synchronized (lock) {
//                move = Move.RandomChoice();
//                lock.notifyAll();
//        }
    }

}
