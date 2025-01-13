package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day1 extends AbstractDaySolver<Integer, Integer> {

    private static final int STARTING_FLOOR = 0;

    public Day1() {
        super(Day1.class);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int up = StringUtils.countMatches(line, "(");
        int down = StringUtils.countMatches(line, ")");

        return STARTING_FLOOR + up - down;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 74;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int currentFloor = STARTING_FLOOR;
        for (int i = 0; true; i++) {
            char c = line.charAt(i);

            if (c == '(') {
                currentFloor++;
            } else if (c == ')') {
                currentFloor--;
            } else {
                throw new IllegalStateException("Unexpected character: " + c);
            }

            if (currentFloor == -1) {
                return i + 1;
            }
        }
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_795;
    }
}
