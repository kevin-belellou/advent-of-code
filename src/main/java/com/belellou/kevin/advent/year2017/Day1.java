package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day1 extends AbstractDaySolver<Integer, Integer> {

    private static Integer getSum(String line, int windowSize) {
        return Stream.of(line.split(""))
                     .map(Integer::valueOf)
                     .gather(Gatherers.windowSliding(windowSize))
                     .filter(window -> window.getFirst().equals(window.getLast()))
                     .map(List::getFirst)
                     .reduce(Integer::sum)
                     .orElse(0);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        line = line + line.charAt(0);

        return getSum(line, 2);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_031;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        int midPoint = line.length() / 2;

        line = line + line.substring(0, midPoint);

        return getSum(line, midPoint + 1);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_080;
    }
}
