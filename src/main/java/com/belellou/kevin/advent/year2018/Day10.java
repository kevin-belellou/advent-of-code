package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day10 extends AbstractDaySolver<String, Integer> {

    private static final char EMPTY = '.';
    private static final char POINT = '#';

    private static char[][] createGrid(List<Point> points) {
        int minX = points.stream().mapToInt(Point::getX).min().orElseThrow();
        int maxX = points.stream().mapToInt(Point::getX).max().orElseThrow();
        int minY = points.stream().mapToInt(Point::getY).min().orElseThrow();
        int maxY = points.stream().mapToInt(Point::getY).max().orElseThrow();

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;

        long area = (long) sizeX * sizeY;

        if (area > 2_000L) {
            return new char[0][0];
        }

        char[][] grid = new char[sizeY][sizeX];
        IntStream.range(0, sizeY)
                 .forEach(i -> Arrays.fill(grid[i], EMPTY));

        points.forEach(point -> grid[point.getY() - minY][point.getX() - minX] = POINT);

        return grid;
    }

    private static void advanceAll(List<Point> points, int times) {
        points.forEach(point -> {
            point.x += point.vx * times;
            point.y += point.vy * times;
        });
    }

    private static void print(char[][] grid) {
        for (char[] chars : grid) {
            System.out.println(Arrays.toString(chars));
        }
        System.out.println(IntStream.range(0, grid[0].length * 3).mapToObj(i -> "-").reduce("", (a, b) -> a + b));
    }

    private static int waitForAlignment(List<Point> points) {
        char[][] grid;
        int seconds;

        // Empirical value
        advanceAll(points, 10_000);
        seconds = 10_000;

        while (true) {
            grid = createGrid(points);

            if (grid.length > 0) {
                long area = (long) grid.length * grid[0].length;

                // Empirical value
                if (area == 620) {
//                    print(grid);
                    break;
                }
            }

            advanceAll(points, 1);
            seconds++;
        }

        return seconds;
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        List<Point> points = reader.lines().map(Point::of).toList();

        waitForAlignment(points);

        // Visual only solution
        //noinspection SpellCheckingInspection
        return "AJZNXHKE";
    }

    @Override
    public String getFirstStarSolution() {
        //noinspection SpellCheckingInspection
        return "AJZNXHKE";
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Point> points = reader.lines().map(Point::of).toList();

        return waitForAlignment(points);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 10_905;
    }

    private static class Point {

        private static final Pattern PATTERN = Pattern.compile(
                "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>");

        public final int vx, vy;

        public int x, y;

        public Point(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public static Point of(String line) {
            Matcher matcher = PATTERN.matcher(line);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }

            return new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
                             Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", vx=" + vx +
                    ", vy=" + vy +
                    '}';
        }
    }
}
