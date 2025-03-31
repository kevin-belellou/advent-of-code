package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.function.Function;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day4 extends AbstractDaySolver<Integer, Integer> {

    private static int countValidPassphrases(BufferedReader reader, Function<String[], Boolean> validator) {
        return Math.toIntExact(reader.lines()
                                     .map(line -> line.split("\\s+"))
                                     .map(validator)
                                     .filter(isValid -> isValid)
                                     .count());
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return countValidPassphrases(reader, line -> line.length == Arrays.stream(line)
                                                                          .distinct()
                                                                          .count()
        );
    }

    @Override
    public Integer getFirstStarSolution() {
        return 455;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return countValidPassphrases(reader, line -> {
            if (line.length != Arrays.stream(line).distinct().count()) {
                return false;
            }

            return Arrays.stream(line)
                         .map(word -> Arrays.stream(word.split(""))
                                            .sorted()
                                            .toList())
                         .distinct()
                         .count() == line.length;
        });
    }

    @Override
    public Integer getSecondStarSolution() {
        return 186;
    }
}
