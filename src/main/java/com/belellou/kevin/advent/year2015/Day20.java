package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day20 extends AbstractDaySolver<Integer> {

    public Day20() {
        super(Day20.class);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        int searchedPresent = Integer.parseInt(reader.lines().findFirst().orElseThrow());

        return IntStream.range(1, Integer.MAX_VALUE)
                        .dropWhile(value -> {
                            AtomicInteger presents = new AtomicInteger();

                            IntStream.rangeClosed(1, (int) Math.sqrt(value))
                                     .forEach(divider -> {
                                         if (value % divider == 0) {
                                             presents.addAndGet(divider * 10);

                                             if (divider != value / divider) {
                                                 presents.addAndGet((value / divider) * 10);
                                             }
                                         }
                                     });

                            return presents.get() < searchedPresent;
                        })
                        .findFirst()
                        .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 831_600;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        int searchedPresent = Integer.parseInt(reader.lines().findFirst().orElseThrow());

        return IntStream.range(1, Integer.MAX_VALUE)
                        .dropWhile(value -> {
                            AtomicInteger presents = new AtomicInteger();

                            IntStream.rangeClosed(1, (int) Math.sqrt(value))
                                     .limit(50)
                                     .forEach(divider -> {
                                         if (value % divider == 0) {
                                             presents.addAndGet(divider * 11);

                                             if (divider != value / divider) {
                                                 presents.addAndGet((value / divider) * 11);
                                             }
                                         }
                                     });

                            return presents.get() < searchedPresent;
                        })
                        .findFirst()
                        .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 884_520;
    }
}
