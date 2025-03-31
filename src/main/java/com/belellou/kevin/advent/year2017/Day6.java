package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day6 extends AbstractDaySolver<Integer, Integer> {

    private static Pair<Integer, Integer> countSteps(BufferedReader reader) throws IOException {
        List<Integer> line = new ArrayList<>(Arrays.stream(reader.readLine().split("\\s+"))
                                                   .map(Integer::valueOf)
                                                   .toList());

        Map<List<Integer>, Integer> set = new HashMap<>();
        int steps = 0;
        Integer lastSeenStep;

        while (true) {
            lastSeenStep = set.put(line, steps);
            if (lastSeenStep != null) {
                break;
            }

            steps++;

            int maxBlocks = line.stream().max(Comparator.naturalOrder()).orElseThrow();
            int maxBlocksIndex = line.indexOf(maxBlocks);
            line.set(maxBlocksIndex, 0);

            while (maxBlocks > 0) {
                maxBlocksIndex = (maxBlocksIndex + 1) % line.size();

                line.set(maxBlocksIndex, line.get(maxBlocksIndex) + 1);

                maxBlocks--;
            }
        }

        return Pair.of(steps, lastSeenStep);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        return countSteps(reader).getLeft();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 11_137;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        Pair<Integer, Integer> pair = countSteps(reader);

        return pair.getLeft() - pair.getRight();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_037;
    }
}
