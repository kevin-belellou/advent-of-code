package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Gatherers;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day2 extends AbstractDaySolver<Integer, Integer> {

    public static final String WHITESPACE_REGEX = "\\s+";

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines()
                     .map(line -> line.split(WHITESPACE_REGEX))
                     .map(line -> Arrays.stream(line)
                                        .map(Integer::parseInt)
                                        .sorted()
                                        .toList())
                     .gather(Gatherers.fold(() -> 0,
                                            (sum, numbers) -> sum + (numbers.getLast() - numbers.getFirst())))
                     .findFirst()
                     .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 51_139;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return reader.lines()
                     .map(line -> line.split(WHITESPACE_REGEX))
                     .map(line -> Arrays.stream(line)
                                        .map(Integer::parseInt)
                                        .sorted(Comparator.reverseOrder())
                                        .toList())
                     .map(numbers -> {
                         for (int i = 0; i < numbers.size(); i++) {
                             for (int j = i + 1; j < numbers.size(); j++) {
                                 if (numbers.get(i) % numbers.get(j) == 0) {
                                     return numbers.get(i) / numbers.get(j);
                                 }
                             }
                         }
                         throw new RuntimeException("No division found");
                     })
                     .reduce(Integer::sum)
                     .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 272;
    }
}
