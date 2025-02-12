package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day10 extends AbstractDaySolver<Integer, Integer> {

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
    protected Integer doSolveFirstStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        for (int i = 0; i < 40; i++) {
            line = readLine(line);
        }

        return line.length();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 252_594;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        for (int i = 0; i < 50; i++) {
            line = readLine(line);
        }

        return line.length();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 3_579_328;
    }
}
