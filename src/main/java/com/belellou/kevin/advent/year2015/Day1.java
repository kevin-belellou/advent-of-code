package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.Day;
import com.belellou.kevin.advent.generic.Year;

public class Day1 extends AbstractDaySolver {

    private static final int STARTING_FLOOR = 0;

    public Day1() {
        super(Year.YEAR_2015, Day.DAY_1);
    }

    @Override
    protected int doSolveFirstStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        int up = StringUtils.countMatches(line, "(");
        int down = StringUtils.countMatches(line, ")");

        int final_floor = STARTING_FLOOR + up - down;
        System.out.println("Final floor is: " + final_floor);

        return final_floor;
    }

    @Override
    protected int doSolveSecondStar(BufferedReader reader) throws IOException {
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
                int basementPosition = i + 1;
                System.out.println("Basement entered a position " + basementPosition);
                return basementPosition;
            }
        }
    }
}
