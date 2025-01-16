package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day9 extends AbstractDaySolver<Integer, Integer> {

    public Day9() {
        super(Day9.class);
    }

    private static String decompress(String line) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '(') {
                int nextIndexOfX = line.indexOf('x', i);
                int charCount = Integer.parseInt(line.substring(i + 1, nextIndexOfX));

                int nextIndexOfClosingParenthesis = line.indexOf(')', nextIndexOfX + 1);
                int repetitionCount = Integer.parseInt(line.substring(nextIndexOfX + 1, nextIndexOfClosingParenthesis));

                String substring = line.substring(nextIndexOfClosingParenthesis + 1,
                                                  nextIndexOfClosingParenthesis + 1 + charCount);
                IntStream.range(0, repetitionCount).forEach(j -> builder.append(substring));

                i = charCount + nextIndexOfClosingParenthesis;
            } else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        String decompressedLine = decompress(line);

        return decompressedLine.length();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 115_118;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        String decompressedLine;
        do {
            decompressedLine = decompress(line);
        } while (decompressedLine.contains("("));

        return decompressedLine.length();
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }
}
