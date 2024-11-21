package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.commons.codec.digest.DigestUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.Day;
import com.belellou.kevin.advent.generic.Year;

@SuppressWarnings("unused")
public class Day4 extends AbstractDaySolver {

    private static final String FIVE_ZEROES_PATTERN = "00000";
    private static final String SIX_ZEROES_PATTERN = "000000";

    public Day4() {
        super(Year.YEAR_2015, Day.DAY_4);
    }

    private static int findNumber(String line, String pattern) {
        return IntStream.range(1, Integer.MAX_VALUE)
                        .takeWhile(value -> {
                            String s = DigestUtils.md5Hex(line + value);
                            return !s.startsWith(pattern);
                        })
                        .reduce((left, right) -> right)
                        .orElseThrow() + 1;
    }

    @Override
    protected int doSolveFirstStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        return findNumber(line, FIVE_ZEROES_PATTERN);
    }

    @Override
    public int getFirstStarSolution() {
        return 346_386;
    }

    @Override
    protected int doSolveSecondStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        return findNumber(line, SIX_ZEROES_PATTERN);
    }

    @Override
    public int getSecondStarSolution() {
        return 9_958_218;
    }
}