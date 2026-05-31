package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day11 extends AbstractDaySolver<String, String> {

    private static final int GRID_SIZE = 300;
    private static final String COMMA = ",";

    private static int getSerialNumber(BufferedReader reader) {
        return Integer.parseInt(reader.lines().findFirst().orElseThrow());
    }

    private static int[][] getGrid(int serialNumber) {
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];

        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y] = getPowerLevel(x, y, serialNumber);
            }
        }

        return grid;
    }

    private static int getPowerLevel(int x, int y, int serialNumber) {
        int rackID = x + 10;
        int powerLevel = rackID * y;
        powerLevel += serialNumber;
        powerLevel *= rackID;
        powerLevel = (powerLevel / 100) % 10;
        powerLevel -= 5;
        return powerLevel;
    }

    private static String getSquareKey(int x, int y) {
        return x + COMMA + y;
    }

    private static String getSquareKeyWithSize(int x, int y, int size) {
        return x + COMMA + y + COMMA + size;
    }

    private static void computeSquares(int[][] grid, Map<String, Integer> squares, int size, boolean simpleName) {
        for (int x = 0; x <= GRID_SIZE - size; x++) {
            for (int y = 0; y <= GRID_SIZE - size; y++) {
                int power = 0;

                for (int dx = 0; dx < size; dx++) {
                    for (int dy = 0; dy < size; dy++) {
                        power += grid[x + dx][y + dy];
                    }
                }

                squares.put(simpleName ? getSquareKey(x, y) : getSquareKeyWithSize(x, y, size), power);
            }
        }
    }

    private static String getBestSquare(Map<String, Integer> squares) {
        return squares.entrySet()
                      .stream()
                      .max(Map.Entry.comparingByValue())
                      .map(Map.Entry::getKey)
                      .orElseThrow();
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
//        return doSolveFirstStarByMe(reader);
        return doSolveFirstStarByAI(reader);
    }

    private static String doSolveFirstStarByMe(BufferedReader reader) {
        int serialNumber = getSerialNumber(reader);

        int[][] grid = getGrid(serialNumber);

        Map<String, Integer> squares = new HashMap<>();

        computeSquares(grid, squares, 3, true);

        return getBestSquare(squares);
    }

    @Override
    public String getFirstStarSolution() {
        return "20,62";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
//        return doSolveSecondStarByMe(reader);
        return doSolveSecondStarByAI(reader);
    }

    private static String doSolveSecondStarByMe(BufferedReader reader) {
        int serialNumber = getSerialNumber(reader);

        int[][] grid = getGrid(serialNumber);

        Map<String, Integer> squares = new HashMap<>();

        for (int size = 1; size <= GRID_SIZE; size++) {
            computeSquares(grid, squares, size, false);
        }

        return getBestSquare(squares);
    }

    @Override
    public String getSecondStarSolution() {
        return "229,61,16";
    }

    // ===== AI-generated code =====

    private static int[][] getSummedAreaTable(int[][] grid) {
        int[][] summed = new int[GRID_SIZE + 1][GRID_SIZE + 1];

        for (int x = 1; x <= GRID_SIZE; x++) {
            for (int y = 1; y <= GRID_SIZE; y++) {
                summed[x][y] = grid[x - 1][y - 1]
                        + summed[x - 1][y]
                        + summed[x][y - 1]
                        - summed[x - 1][y - 1];
            }
        }

        return summed;
    }

    private static int getSquarePower(int[][] summed, int x, int y, int size) {
        return summed[x + size][y + size]
                - summed[x][y + size]
                - summed[x + size][y]
                + summed[x][y];
    }

    protected String doSolveFirstStarByAI(BufferedReader reader) {
        int serialNumber = getSerialNumber(reader);

        int[][] grid = getGrid(serialNumber);
        int[][] summed = getSummedAreaTable(grid);

        int size = 3;
        int bestX = 0;
        int bestY = 0;
        int bestPower = Integer.MIN_VALUE;

        for (int x = 0; x <= GRID_SIZE - size; x++) {
            for (int y = 0; y <= GRID_SIZE - size; y++) {
                int power = getSquarePower(summed, x, y, size);

                if (power > bestPower) {
                    bestPower = power;
                    bestX = x;
                    bestY = y;
                }
            }
        }

        return getSquareKey(bestX, bestY);
    }

    protected String doSolveSecondStarByAI(BufferedReader reader) {
        int serialNumber = getSerialNumber(reader);

        int[][] grid = getGrid(serialNumber);
        int[][] summed = getSummedAreaTable(grid);

        int bestX = 0;
        int bestY = 0;
        int bestSize = 0;
        int bestPower = Integer.MIN_VALUE;

        for (int size = 1; size <= GRID_SIZE; size++) {
            for (int x = 0; x <= GRID_SIZE - size; x++) {
                for (int y = 0; y <= GRID_SIZE - size; y++) {
                    int power = getSquarePower(summed, x, y, size);

                    if (power > bestPower) {
                        bestPower = power;
                        bestX = x;
                        bestY = y;
                        bestSize = size;
                    }
                }
            }
        }

        return getSquareKeyWithSize(bestX, bestY, bestSize);
    }
}
