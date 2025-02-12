package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day1 extends AbstractDaySolver<Integer, Integer> {

    private static int solve(BufferedReader reader, boolean checkForPoints) throws IOException {
        String line = reader.readLine();
        String[] split = line.split(", ");

        Direction direction = Direction.NORTH;
        int x = 0;
        int y = 0;

        Point lastPoint = new Point(x, y);
        HashSet<Point> points = new HashSet<>();

        for (String step : split) {
            int blocks = Integer.parseInt(step.substring(1));

            direction = step.charAt(0) == 'L' ? direction.left : direction.right;

            switch (direction) {
                case NORTH -> y += blocks;
                case SOUTH -> y -= blocks;
                case EAST -> x += blocks;
                case WEST -> x -= blocks;
            }

            if (checkForPoints) {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    for (int i = lastPoint.y; i != y; i += (y - i) / Math.abs(y - i)) {
                        if (!points.add(new Point(x, i))) {
                            return Math.abs(x) + Math.abs(i);
                        }
                    }
                } else {
                    for (int i = lastPoint.x; i != x; i += (x - i) / Math.abs(x - i)) {
                        if (!points.add(new Point(i, y))) {
                            return Math.abs(i) + Math.abs(y);
                        }
                    }
                }

                lastPoint = new Point(x, y);
            }
        }

        return Math.abs(x) + Math.abs(y);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        return solve(reader, false);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 273;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        return solve(reader, true);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 115;
    }

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST;

        static {
            NORTH.left = WEST;
            NORTH.right = EAST;

            SOUTH.left = EAST;
            SOUTH.right = WEST;

            EAST.left = NORTH;
            EAST.right = SOUTH;

            WEST.left = SOUTH;
            WEST.right = NORTH;
        }

        public Direction left;
        public Direction right;
    }

    private record Point(int x, int y) {}
}
