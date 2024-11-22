package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.Day;
import com.belellou.kevin.advent.generic.Year;

@SuppressWarnings("unused")
public class Day10 extends AbstractDaySolver {

    public Day10() {
        super(Year.YEAR_2015, Day.DAY_10);
    }

    private static String readLine(String line) {
        int length = line.length();
        int pos = 0;
        StringBuilder builder = new StringBuilder();

        while (pos < length) {
            int prePos = pos;
            char c = line.charAt(pos);

            do {
                pos++;
            } while (pos < length && line.charAt(pos) == c);

            int count = pos - prePos;
            builder.append(count).append(c);
        }

        return builder.toString();
    }

    @Override
    protected int doSolveFirstStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        for (int i = 0; i < 40; i++) {
            line = readLine(line);
        }

        return line.length();
    }

    @Override
    public int getFirstStarSolution() {
        return 252_594;
    }

    @Override
    protected int doSolveSecondStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        for (int i = 0; i < 50; i++) {
            line = readLine(line);
        }

        return line.length();
    }

    @Override
    public int getSecondStarSolution() {
        return 3_579_328;
    }
}
