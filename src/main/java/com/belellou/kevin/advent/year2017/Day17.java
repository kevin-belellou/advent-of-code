package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day17 extends AbstractDaySolver<Integer, Integer> {

    private static final int ITERATIONS_1 = 2_017;
    private static final int ITERATIONS_2 = 50_000_000;

    private static int getNumberOfSteps(BufferedReader reader) {
        return reader.lines().findFirst().map(Integer::valueOf).orElseThrow();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        int numberOfSteps = getNumberOfSteps(reader);

        List<Integer> circularBuffer = new ArrayList<>(ITERATIONS_1 + 1);
        circularBuffer.add(0);
        int currentPosition = 0;

        for (int i = 1; i <= ITERATIONS_1; i++) {
            int index = (numberOfSteps % circularBuffer.size() + currentPosition) % circularBuffer.size() + 1;

            circularBuffer.add(index, i);
            currentPosition = index;
        }

        return circularBuffer.get(currentPosition + 1);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_670;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        int numberOfSteps = getNumberOfSteps(reader);
        int bufferSize = 1;
        int currentPosition = 0;
        int valueAfterZero = 0;

        for (int i = 1; i <= ITERATIONS_2; i++) {
            int index = ((numberOfSteps % bufferSize) + currentPosition) % bufferSize + 1;

            // Only update valueAfterZero if we inserted at position 1 (right after 0)
            if (index == 1) {
                valueAfterZero = i;
            }

            currentPosition = index;
            bufferSize++;
        }

        return valueAfterZero;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 2_316_253;
    }
}
