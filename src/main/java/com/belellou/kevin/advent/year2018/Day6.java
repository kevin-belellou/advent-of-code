package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day6 extends AbstractDaySolver<Integer, Integer> {

    private static final Point NULL_POINT = new Point(-1, -1);
    private static final Point SAFE_POINT = new Point(-2, -2);

    private static final int SAFE_DISTANCE = 10_000;

    private static Integer getMax(List<Point> points, Function<Point, Integer> function) {
        return points.stream()
                     .map(function)
                     .max(Integer::compareTo)
                     .orElseThrow();
    }

    private static int getDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private static int count(Point[][] grid, Point point) {
        return (int) Arrays.stream(grid)
                           .flatMap(Arrays::stream)
                           .filter(currentPoint -> currentPoint == point)
                           .count();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Point> points = new ArrayList<>(reader.lines().map(Point::new).toList());

        int maxX = getMax(points, Point::x) + 1;
        int maxY = getMax(points, Point::y) + 1;

        Point[][] grid = new Point[maxX][maxY];

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Point p = new Point(i, j);

                List<Integer> distances = points.stream()
                                                .map(point -> getDistance(point, p))
                                                .toList();

                int minDistance = distances.stream().min(Integer::compareTo).orElseThrow();
                long count = distances.stream().filter(distance -> distance == minDistance).count();

                grid[i][j] = count > 1 ? NULL_POINT : points.get(distances.indexOf(minDistance));
            }
        }

        Set<Point> infinitePoints = new HashSet<>();

        for (int i = 0; i < maxX; i++) {
            infinitePoints.add(grid[i][0]);
            infinitePoints.add(grid[i][maxY - 1]);
        }
        for (int j = 0; j < maxY; j++) {
            infinitePoints.add(grid[0][j]);
            infinitePoints.add(grid[maxX - 1][j]);
        }

        points.removeAll(infinitePoints);

        return points.stream()
                     .mapToInt(point -> count(grid, point))
                     .max()
                     .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 4_589;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Point> points = new ArrayList<>(reader.lines().map(Point::new).toList());

        int maxX = getMax(points, Point::x);
        int maxY = getMax(points, Point::y);

        Point[][] grid = new Point[maxX][maxY];

        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                Point p = new Point(i, j);

                int sum = points.stream()
                                .map(point -> getDistance(point, p))
                                .reduce(Integer::sum)
                                .orElseThrow();

                grid[i][j] = sum < SAFE_DISTANCE ? SAFE_POINT : NULL_POINT;
            }
        }

        return count(grid, SAFE_POINT);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 40_252;
    }

    private record Point(int x, int y) {

        public Point(String line) {
            String[] split = line.split(", ");
            this(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
        }
    }
}
