package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day1 extends AbstractDaySolver<Integer, Integer> {

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines().mapToInt(Integer::parseInt).sum();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 574;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Integer> changes = reader.lines().map(Integer::parseInt).toList();
        HashSet<Integer> frequencies = new HashSet<>();

        int currentFrequency = 0;
        int changesIndex = 0;

        while (frequencies.add(currentFrequency)) {
            currentFrequency += changes.get(changesIndex);
            changesIndex = (changesIndex + 1) % changes.size();
        }

        return currentFrequency;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 452;
    }
}
