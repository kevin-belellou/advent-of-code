package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day3 extends AbstractDaySolver<Integer, Integer> {

    public Day3() {
        super(Day3.class);
    }

    private static Integer[] getSplit(String line) {
        return Arrays.stream(line.trim().split("\\s+")).map(Integer::valueOf).toArray(Integer[]::new);
    }

    private static boolean isTriangleValid(Integer[] split) {
        Arrays.sort(split);
        return split[0] + split[1] > split[2];
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        int possibleTriangles = 0;

        for (String line : lines) {
            Integer[] split = getSplit(line);

            if (isTriangleValid(split)) {
                possibleTriangles++;
            }
        }

        return possibleTriangles;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 983;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        Integer[][] triangles = new Integer[3][3];
        int triangleIndex = 0;

        int possibleTriangles = 0;

        for (String line : lines) {
            Integer[] split = getSplit(line);

            triangleIndex++;
            for (int i = 0; i < 3; i++) {
                triangles[i][triangleIndex - 1] = split[i];
            }

            if (triangleIndex == 3) {
                for (int triangle = 0; triangle < 3; triangle++) {
                    if (isTriangleValid(triangles[triangle])) {
                        possibleTriangles++;
                    }
                }

                triangleIndex = 0;
            }
        }

        return possibleTriangles;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_836;
    }
}
