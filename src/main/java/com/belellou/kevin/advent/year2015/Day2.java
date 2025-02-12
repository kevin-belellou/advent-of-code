package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day2 extends AbstractDaySolver<Integer, Integer> {

    private static final Pattern PATTERN = Pattern.compile("^(\\d+)x(\\d+)x(\\d+)$");

    private static int findAreaNeeded(String line) {
        Dimensions dimensions = getDimensions(line);

        int side1Area = dimensions.length * dimensions.width;
        int side2Area = dimensions.width * dimensions.height;
        int side3Area = dimensions.height * dimensions.length;

        return 2 * side1Area + 2 * side2Area + 2 * side3Area + NumberUtils.min(side1Area, side2Area, side3Area);
    }

    private static int findLengthNeeded(String line) {
        Dimensions dimensions = getDimensions(line);

        int volume = dimensions.length * dimensions.width * dimensions.height;
        Integer[] sorted = dimensions.sorted();

        return volume + 2 * sorted[0] + 2 * sorted[1];
    }

    private static Dimensions getDimensions(String line) {
        Matcher matcher = PATTERN.matcher(line);
        //noinspection ResultOfMethodCallIgnored
        matcher.matches();

        int length = Integer.parseInt(matcher.group(1));
        int width = Integer.parseInt(matcher.group(2));
        int height = Integer.parseInt(matcher.group(3));

        return new Dimensions(length, width, height);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines().map(Day2::findAreaNeeded).reduce(Integer::sum).orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_588_178;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return reader.lines().map(Day2::findLengthNeeded).reduce(Integer::sum).orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 3_783_758;
    }

    private record Dimensions(int length, int width, int height) {

        Integer[] sorted() {
            return Stream.of(length, width, height).sorted().toList().toArray(new Integer[0]);
        }
    }
}
