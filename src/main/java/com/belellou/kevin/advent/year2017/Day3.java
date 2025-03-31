package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day3 extends AbstractDaySolver<Integer, Integer> {

    private static int[] findCoordinates(int value) {
        if (value == 1) {
            return new int[] {0, 0}; // The center
        }

        // Find the layer where the value exists
        int layer = 0;
        while (Math.pow(2 * layer + 1, 2) < value) {
            layer++;
        }

        // Calculate the start and end of the layer
        int layerStart = (int) Math.pow(2 * (layer - 1) + 1, 2) + 1;
        int layerEnd = (int) Math.pow(2 * layer + 1, 2);

        // Length of one side of the layer
        int sideLength = 2 * layer;

        // Offset within the layer
        int offset = value - layerStart;

        // Determine which side of the layer and the position
        int side = offset / sideLength;
        int position = offset % sideLength;

        // Compute x, y based on the side
        int x = 0;
        int y = 0;
        if (side == 0) { // Left side (bottom to top)
            x = layer;
            y = -layer + 1 + position;
        } else if (side == 1) { // Top side (right to left)
            x = layer - 1 - position;
            y = layer;
        } else if (side == 2) { // Right side (top to bottom)
            x = -layer;
            y = layer - 1 - position;
        } else if (side == 3) { // Bottom side (left to right)
            x = -layer + 1 + position;
            y = -layer;
        }

        return new int[] {x, y};
    }

    private static int createGridUntilValueIsFound(int inputValue) {
        int maxGridSize = IntStream.range(1, Integer.MAX_VALUE)
                                   .dropWhile(value -> value * value < inputValue)
                                   .findFirst()
                                   .orElseThrow();

        int[][] grid = new int[maxGridSize][maxGridSize];

        int center = maxGridSize / 2;
        grid[center][center] = 1;

        // Step size to move outward in the spiral
        // (step alternates between horizontal and vertical as we spiral outward)
        int step = 1;

        int x = center;
        int y = center;

        while (true) {
            // Spiral Right → Up → Left → Down
            for (int[] direction : new int[][] {{0, 1}, {-1, 0}, {0, -1}, {1, 0}}) {
                for (int i = 0; i < step; i++) {
                    x += direction[0];
                    y += direction[1];

                    int number = sumOfAdjacentSquares(grid, x, y);

                    if (number > inputValue) {
                        return number;
                    }

                    grid[x][y] = number;
                }

                // Increase the step size every two directions (Right+Up, Left+Down)
                if (direction[1] == 0) {
                    step++;
                }
            }
        }
    }

    private static int sumOfAdjacentSquares(int[][] grid, int x, int y) {
        return IntStream.range(-1, 2)
                        .map(dx -> IntStream.range(-1, 2)
                                            .map(dy -> grid[x + dx][y + dy])
                                            .sum())
                        .sum();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        int inputValue = Integer.parseInt(reader.readLine());

        int[] inputCoordinates = findCoordinates(inputValue);

        return Math.abs(inputCoordinates[0]) + Math.abs(inputCoordinates[1]);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 552;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        int inputValue = Integer.parseInt(reader.readLine());

        return createGridUntilValueIsFound(inputValue);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 330_785;
    }
}
