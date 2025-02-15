package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings({"unused"})
public class Day24 extends AbstractDaySolver<Integer, Integer> {

    private static final char WALL = '#';
    private static final char PATH = '.';

    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final char TWO = '2';
    private static final char THREE = '3';
    private static final char FOUR = '4';
    private static final char FIVE = '5';
    private static final char SIX = '6';
    private static final char SEVEN = '7';

    private static final List<Character> NUMBERS = List.of(ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN);

    private static char[][] getMaze(BufferedReader reader) {
        return reader.lines().map(String::toCharArray).toArray(char[][]::new);
    }

    private static Map<Character, Position> getNumberPositions(char[][] maze) {
        Map<Character, Position> result = new HashMap<>(NUMBERS.size());

        for (char number : NUMBERS) {
            result.put(number, findPosition(maze, number));
        }

        return result;
    }

    private static Position findPosition(char[][] maze, char number) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == number) {
                    return Position.of(i, j);
                }
            }
        }

        throw new IllegalArgumentException("Cannot find number " + number);
    }

    private static List<Distance> getDistances(char[][] maze, Map<Character, Position> numberPositions) {
        ScopedValue<List<Position>> previousPositions = ScopedValue.newInstance();

        return null;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        char[][] maze = getMaze(reader);
        Map<Character, Position> numberPositions = getNumberPositions(maze);

        return 0;
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 1;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }

    private static class Position extends ImmutablePair<Integer, Integer> {

        private Position(Integer left, Integer right) {
            super(left, right);
        }

        public static Position of(Integer left, Integer right) {
            return new Position(left, right);
        }
    }

    private record Distance(char from, char to, int distance) {}
}
