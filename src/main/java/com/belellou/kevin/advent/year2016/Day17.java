package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.function.BinaryOperator;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings({"unused"})
public class Day17 extends AbstractDaySolver<String, Integer> {

    private static final Position startingPosition = Position.of(0, 0);
    private static final Position vaultPosition = Position.of(3, 3);

    private static String findPath(Position position, String passcode, String path, boolean findShortestPath)
            throws InterruptedException, ExecutionException {
        if (position.equals(vaultPosition)) {
            return path;
        }

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<Subtask<String>> subtasks = new ArrayList<>();

            String hash = DigestUtils.md5Hex(passcode + path).substring(0, 4);

            Arrays.stream(Direction.values())
                  .filter(direction -> isDirectionAccessible(direction, position, hash))
                  .forEach(direction -> {
                      Position newPosition = switch (direction) {
                          case UP -> Position.of(position.getLeft(), position.getRight() - 1);
                          case DOWN -> Position.of(position.getLeft(), position.getRight() + 1);
                          case LEFT -> Position.of(position.getLeft() - 1, position.getRight());
                          case RIGHT -> Position.of(position.getLeft() + 1, position.getRight());
                      };

                      subtasks.add(scope.fork(() -> findPath(newPosition, passcode, path + direction.name().charAt(0),
                                                             findShortestPath)));
                  });

            scope.join().throwIfFailed();

            Comparator<String> comparator = Comparator.comparingInt(String::length);
            BinaryOperator<String> binaryOperator =
                    findShortestPath ? BinaryOperator.minBy(comparator) : BinaryOperator.maxBy(comparator);

            return subtasks.stream().map(Subtask::get).filter(Objects::nonNull).reduce(binaryOperator).orElse(null);
        }
    }

    private static boolean isDirectionAccessible(Direction direction, Position position, String hash) {
        boolean doorOpen = isDoorOpen(hash.charAt(direction.ordinal()));

        return switch (direction) {
            case UP -> doorOpen && position.getRight() != 0;
            case DOWN -> doorOpen && position.getRight() != 3;
            case LEFT -> doorOpen && position.getLeft() != 0;
            case RIGHT -> doorOpen && position.getLeft() != 3;
        };
    }

    private static boolean isDoorOpen(char c) {
        return c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f';
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) throws IOException {
        try {
            return findPath(startingPosition, reader.readLine(), "", true);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFirstStarSolution() {
        return "RRRLDRDUDD";
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        try {
            return findPath(startingPosition, reader.readLine(), "", false).length();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getSecondStarSolution() {
        return 706;
    }

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static class Position extends ImmutablePair<Integer, Integer> {

        private Position(Integer left, Integer right) {
            super(left, right);
        }

        public static Position of(Integer left, Integer right) {
            return new Position(left, right);
        }
    }
}
