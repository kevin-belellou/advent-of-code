package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day3 extends AbstractDaySolver<Integer, Integer> {

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int length = line.length();

        int xPos = 0;
        int yPos = 0;

        Set<Position> houses = new HashSet<>();

        for (int i = 0; i < length; i++) {
            switch (line.charAt(i)) {
                case '>':
                    xPos++;
                    break;
                case '<':
                    xPos--;
                    break;
                case '^':
                    yPos++;
                    break;
                case 'v':
                    yPos--;
                    break;
            }

            houses.add(new Position(xPos, yPos));
        }

        return houses.size() + 1;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 2_081;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int length = line.length();

        int xPosSanta = 0;
        int yPosSanta = 0;
        int xPosRoboSanta = 0;
        int yPosRoboSanta = 0;

        Set<Position> houses = new HashSet<>();

        for (int i = 0; i < length; i++) {
            switch (line.charAt(i)) {
                case '>':
                    if (i % 2 == 0) {
                        xPosSanta++;
                    } else {
                        xPosRoboSanta++;
                    }
                    break;
                case '<':
                    if (i % 2 == 0) {
                        xPosSanta--;
                    } else {
                        xPosRoboSanta--;
                    }
                    break;
                case '^':
                    if (i % 2 == 0) {
                        yPosSanta++;
                    } else {
                        yPosRoboSanta++;
                    }
                    break;
                case 'v':
                    if (i % 2 == 0) {
                        yPosSanta--;
                    } else {
                        yPosRoboSanta--;
                    }
                    break;
            }

            houses.add(new Position(xPosSanta, yPosSanta));
            houses.add(new Position(xPosRoboSanta, yPosRoboSanta));
        }

        return houses.size();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 2_341;
    }

    private record Position(int x, int y) {}
}
