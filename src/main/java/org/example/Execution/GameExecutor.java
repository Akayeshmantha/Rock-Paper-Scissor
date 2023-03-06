package org.example.Execution;

import org.example.enums.Result;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface GameExecutor {
    ConcurrentMap<Result, AtomicInteger> executeRockPaperScissors();
}
