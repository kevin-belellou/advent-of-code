package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day13 extends AbstractDaySolver<Integer, Integer> {

    private static Map<Integer, Integer> getGrid(BufferedReader reader) {
        Map<Integer, Integer> map = new HashMap<>();

        reader.lines()
              .forEach((line) -> {
                  String[] split = line.split(": ");
                  int depth = Integer.parseInt(split[0]);
                  int range = Integer.parseInt(split[1]);

                  map.put(depth, range);
              });

        return map;
    }

    private static int getSeverity(Map<Integer, Integer> grid, int time, boolean fastFail) {
        int severity = 0;

        for (Map.Entry<Integer, Integer> entry : grid.entrySet()) {
            int depth = entry.getKey();
            int range = entry.getValue();

            int period = range * 2 - 2;

            if ((range - 1) - Math.abs(((depth + time) % period) - (range - 1)) == 0) {
                if (fastFail) {
                    return Integer.MAX_VALUE;
                }

                severity += depth * range;
            }
        }

        return severity;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Map<Integer, Integer> grid = getGrid(reader);

        return getSeverity(grid, 0, false);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_632;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        Map<Integer, Integer> grid = getGrid(reader);

        // Had to use fast fail because a severity score of 0 does not mean you haven't been caught.
        return IntStream.range(10, Integer.MAX_VALUE)
                        .dropWhile(time -> getSeverity(grid, time, true) != 0)
                        .findFirst()
                        .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 3_834_136;
    }
}
