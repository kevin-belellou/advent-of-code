package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day25 extends AbstractDaySolver<Integer> {

    private static final long SEED = 20_151_125;
    private static final long MULTIPLIER = 252_533;
    private static final long DIVIDER = 33_554_393;

    private static final Pattern ROW_PATTERN = Pattern.compile("row (\\d+)");
    private static final Pattern COLUMN_PATTERN = Pattern.compile("column (\\d+)");

    public Day25() {
        super(Day25.class);
    }

    private static int getNumberOfOperations(String line) {
        Matcher matcher = ROW_PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid row");
        }
        int row = Integer.parseInt(matcher.group(1));

        matcher = COLUMN_PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid column");
        }
        int column = Integer.parseInt(matcher.group(1));

        return ((row - 1) * (2 * column + row - 2)) / 2 + (column * (column + 1)) / 2 - 1;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int numberOfOperations = getNumberOfOperations(line);

        long[] array = LongStream.iterate(SEED, integer -> (integer * MULTIPLIER) % DIVIDER)
                                 .limit(numberOfOperations + 1)
                                 .toArray();

        if (array.length != numberOfOperations + 1) {
            throw new IllegalArgumentException("Invalid number of operations");
        }

        return Math.toIntExact(array[array.length - 1]);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 2_650_453;
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
