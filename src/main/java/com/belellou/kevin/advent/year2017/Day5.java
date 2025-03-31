package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day5 extends AbstractDaySolver<Integer, Integer> {

    private static int countSteps(BufferedReader reader, UnaryOperator<Integer> operator) {
        List<Integer> lines = new ArrayList<>(reader.lines().map(Integer::valueOf).toList());

        int index = 0;
        int steps = 0;

        while (index < lines.size()) {
            steps++;

            Integer offset = lines.get(index);

            lines.set(index, operator.apply(offset));

            index += offset;
        }

        return steps;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return countSteps(reader, (offset) -> offset + 1);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 375_042;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return countSteps(reader, (offset) -> offset >= 3 ? offset - 1 : offset + 1);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 28_707_598;
    }
}
