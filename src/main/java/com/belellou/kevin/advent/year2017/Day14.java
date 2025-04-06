package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day14 extends AbstractDaySolver<Integer, Integer> {

    private static final char ONE = '1';
    private static final char USED = '#';

    private static List<String> computeBinaryGrid(String line) {
        return IntStream.rangeClosed(0, 127)
                        .mapToObj(i -> line + "-" + i)
                        .map(Day10::getAsciiCodes)
                        .map(inputLine -> Day10.getSparseHash(inputLine, 64))
                        .map(Day10::getKnotHash)
                        .map(collect -> IntStream.range(0, collect.length())
                                                 .map(i -> Integer.parseInt(collect, i, i + 1, 16))
                                                 .mapToObj(Integer::toBinaryString)
                                                 .map(s -> String.format("%4s", s).replace(' ', '0'))
                                                 .collect(Collectors.joining()))
                        .toList();
    }

    private static void markAdjacentUsed(char[][] grid, int i, int j) {
        if (grid[i][j] != ONE) {
            return;
        }

        grid[i][j] = USED;

        if (i > 0) {
            markAdjacentUsed(grid, i - 1, j);
        }
        if (i < grid.length - 1) {
            markAdjacentUsed(grid, i + 1, j);
        }
        if (j > 0) {
            markAdjacentUsed(grid, i, j - 1);
        }
        if (j < grid[0].length - 1) {
            markAdjacentUsed(grid, i, j + 1);
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        return computeBinaryGrid(reader.readLine()).stream()
                                                   .mapToInt(value -> StringUtils.countMatches(value, "1"))
                                                   .sum();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 8_292;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        char[][] grid = computeBinaryGrid(reader.readLine()).stream()
                                                            .map(String::toCharArray)
                                                            .toArray(char[][]::new);

        int numberOfRegions = 0;

        for (int i = 0; i < grid.length; i++) {
            char[] row = grid[i];

            for (int j = 0; j < row.length; j++) {
                char currentChar = row[j];

                if (currentChar != ONE) {
                    continue;
                }

                numberOfRegions++;
                markAdjacentUsed(grid, i, j);
            }
        }

        return numberOfRegions;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_069;
    }
}
