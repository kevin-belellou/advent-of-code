package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day15 extends AbstractDaySolver<Integer, Integer> {

    private static final int FACTOR_A = 16_807;
    private static final int FACTOR_B = 48_271;
    private static final int DIVISOR = 2_147_483_647; // Integer.MAX_VALUE

    private static final int SAMPLE_SIZE_1 = 40_000_000;
    private static final int SAMPLE_SIZE_2 = 5_000_000;

    public static final int CRITERIA_A = 4;
    public static final int CRITERIA_B = 8;

    private static final Pattern SEED_PATTERN = Pattern.compile("(\\d+)");

    private static Pair<Integer, Integer> getSeeds(BufferedReader reader) {
        return reader.lines()
                     .map(SEED_PATTERN::matcher)
                     .filter(Matcher::find)
                     .map(matcher -> matcher.group(1))
                     .map(Integer::valueOf)
                     .gather(Gatherers.windowFixed(2))
                     .map(window -> Pair.of(window.getFirst(), window.getLast()))
                     .findFirst()
                     .orElseThrow();
    }

    private static Stream<Pair<Integer, Integer>> generatePairs(Pair<Integer, Integer> seeds) {
        // Using a list because values must be final or effectively final for the stream to work.
        List<Pair<Integer, Integer>> values = new ArrayList<>(List.of(seeds));

        int matches = 0;

        return IntStream.range(0, SAMPLE_SIZE_1)
                        .mapToObj(_ -> {
                            values.set(0, nextValues(values.getFirst()));

                            return values.getFirst();
                        });
    }

    private static Pair<Integer, Integer> nextValues(Pair<Integer, Integer> lastValues) {
        int nextA = Math.toIntExact((lastValues.getLeft().longValue() * FACTOR_A) % DIVISOR);
        int nextB = Math.toIntExact((lastValues.getRight().longValue() * FACTOR_B) % DIVISOR);

        return Pair.of(nextA, nextB);
    }

    private static Predicate<Pair<Integer, Integer>> last16BitsMatches() {
        return pair -> (pair.getLeft() & 0xFFFF) == (pair.getRight() & 0xFFFF);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Pair<Integer, Integer> seeds = getSeeds(reader);

        return Math.toIntExact(generatePairs(seeds).filter(last16BitsMatches())
                                                   .count());
    }

    @Override
    public Integer getFirstStarSolution() {
        return 577;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        Pair<Integer, Integer> values = getSeeds(reader);

        List<Integer> leftValues = new ArrayList<>();
        List<Integer> rightValues = new ArrayList<>();

        while (leftValues.size() < SAMPLE_SIZE_2 || rightValues.size() < SAMPLE_SIZE_2) {
            values = nextValues(values);

            if (values.getLeft() % CRITERIA_A == 0) {
                leftValues.add(values.getLeft());
            }
            if (values.getRight() % CRITERIA_B == 0) {
                rightValues.add(values.getRight());
            }
        }

        return Math.toIntExact(IntStream.range(0, SAMPLE_SIZE_2)
                                        .mapToObj(index -> Pair.of(leftValues.get(index), rightValues.get(index)))
                                        .filter(last16BitsMatches())
                                        .count());
    }

    @Override
    public Integer getSecondStarSolution() {
        return 316;
    }
}
