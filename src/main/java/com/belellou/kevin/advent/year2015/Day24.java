package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day24 extends AbstractDaySolver<Integer> {

    public Day24() {
        super(Day24.class);
    }

    private static void solve(List<Integer> packages, int index, int weightPerGroup, int weightLeft,
//                              AtomicLong currentSize,
                              AtomicLong minSize,
//                              AtomicLong currentQuantum,
                              AtomicLong minQuantum,
                              List<Long> list) {
        for (int i = index - 1; i >= 0; i--) {
            int currentPackage = packages.get(i);

            if (currentPackage > weightLeft) {
                continue;
            } else if (currentPackage == weightLeft) {
//                long finalSize = currentSize.get() + 1;
//                long finalQuantum = currentQuantum.get() * currentPackage;
                list.add((long) currentPackage);
                long finalSize = list.size();
                long finalQuantum = list.stream().reduce((aLong, aLong2) -> aLong * aLong2).orElseThrow();

                if (finalSize < minSize.get()) {
                    minSize.set(finalSize);
                    minQuantum.set(finalQuantum);
                } else if (finalSize == minSize.get()) {
                    if (finalQuantum < minQuantum.get()) {
                        minQuantum.set(finalQuantum);
                    }
                }

                list.removeLast();
//                return;
            } else {
//                currentSize.incrementAndGet();
//                currentQuantum.set(currentQuantum.get() * currentPackage);

                list.add((long) currentPackage);
                solve(packages, i, weightPerGroup, weightLeft - currentPackage,
//                      currentSize,
                      minSize,
//                      currentQuantum,
                      minQuantum, list);
                list.removeLast();

//                currentSize.decrementAndGet();
//                currentQuantum.set(currentQuantum.get() / currentPackage);
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Integer> packages = reader.lines().map(Integer::parseInt).toList();

        int totalWeight = packages.stream().reduce(Integer::sum).orElseThrow();
        int weightPerGroup = totalWeight / 3;

//        AtomicLong currentSize = new AtomicLong(0);
        AtomicLong minSize = new AtomicLong(Integer.MAX_VALUE);
//        AtomicLong currentQuantum = new AtomicLong(1);
        AtomicLong minQuantum = new AtomicLong(Integer.MAX_VALUE);
        List<Long> list = new ArrayList<>();

        solve(packages, packages.size(), weightPerGroup, weightPerGroup,
//              currentSize,
              minSize,
//              currentQuantum,
              minQuantum, list);

        return minQuantum.intValue();
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 1;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }
}
