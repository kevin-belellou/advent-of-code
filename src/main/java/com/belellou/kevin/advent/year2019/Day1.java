package com.belellou.kevin.advent.year2019;

import java.io.BufferedReader;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day1 extends AbstractDaySolver<Integer, Integer> {

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines()
                     .mapToInt(Integer::parseInt)
                     .map(value -> value / 3 - 2)
                     .sum();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 3_390_830;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        int totalFuel = 0;

        for (Integer i : reader.lines()
                               .map(Integer::valueOf)
                               .toList()) {
            int fuel = i / 3 - 2;
            do {
                totalFuel += fuel;
                fuel = fuel / 3 - 2;
            } while (fuel > 0);
        }

        return totalFuel;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 5_083_370;
    }
}
