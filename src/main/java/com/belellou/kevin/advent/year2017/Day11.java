package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.jgrapht.alg.util.Triple;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day11 extends AbstractDaySolver<Integer, Integer> {

    private static CubeCoordinates cubeSubtract(CubeCoordinates a, CubeCoordinates b) {
        return CubeCoordinates.of(a.getFirst() - b.getFirst(),
                                  a.getSecond() - b.getSecond(),
                                  a.getThird() - b.getThird());
    }

    private static int cubeDistance(CubeCoordinates a, CubeCoordinates b) {
        CubeCoordinates vector = cubeSubtract(a, b);
        return (Math.abs(vector.getFirst()) + Math.abs(vector.getSecond()) + Math.abs(vector.getThird())) / 2;
    }

    private static void makeStep(CubeCoordinates current, String step) {
        switch (step) {
            case "n" -> current.goNorth();
            case "ne" -> current.goNorthEast();
            case "se" -> current.goSouthEast();
            case "s" -> current.goSouth();
            case "sw" -> current.goSouthWest();
            case "nw" -> current.goNorthWest();
            default -> throw new RuntimeException("Unknown step");
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        CubeCoordinates start = CubeCoordinates.of(0, 0, 0);
        CubeCoordinates current = start.clone();

        Arrays.stream(reader.lines()
                            .findFirst()
                            .orElseThrow()
                            .split(","))
              .forEach(step -> makeStep(current, step));

        return cubeDistance(start, current);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 759;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        CubeCoordinates start = CubeCoordinates.of(0, 0, 0);
        CubeCoordinates current = start.clone();
        AtomicReference<CubeCoordinates> max = new AtomicReference<>(start.clone());

        Arrays.stream(reader.lines()
                            .findFirst()
                            .orElseThrow()
                            .split(","))
              .forEach(step -> {
                  makeStep(current, step);

                  if (cubeDistance(start, current) > cubeDistance(start, max.get())) {
                      max.set(current.clone());
                  }
              });

        return cubeDistance(start, max.get());
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_501;
    }

    private static class CubeCoordinates extends Triple<Integer, Integer, Integer> {

        private CubeCoordinates(Integer x, Integer y, Integer z) {
            super(x, y, z);
        }

        public static CubeCoordinates of(Integer x, Integer y, Integer z) {
            return new CubeCoordinates(x, y, z);
        }

        @SuppressWarnings("MethodDoesntCallSuperMethod")
        public CubeCoordinates clone() {
            return new CubeCoordinates(first, second, third);
        }

        public void goNorth() {
            second--;
            third++;
        }

        public void goNorthEast() {
            first++;
            second--;
        }

        public void goSouthEast() {
            first++;
            third--;
        }

        public void goSouth() {
            second++;
            third--;
        }

        public void goSouthWest() {
            first--;
            second++;
        }

        public void goNorthWest() {
            first--;
            third++;
        }
    }
}
