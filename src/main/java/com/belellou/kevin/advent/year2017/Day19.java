package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day19 extends AbstractDaySolver<String, Integer> {

    private static int route(List<String> lines, List<Character> path) {
        int x = 0;
        int y = lines.getFirst().indexOf("|");
        Direction direction = Direction.DOWN;
        int steps = 1;

        while (true) {
            switch (direction) {
                case UP -> x--;
                case DOWN -> x++;
                case LEFT -> y--;
                case RIGHT -> y++;
            }

            char currentChar = lines.get(x).charAt(y);

            switch (currentChar) {
                case '|', '-', ' ' -> {
                }
                case '+' -> {
                    if (direction == Direction.UP || direction == Direction.DOWN) {
                        if (y > 0 && lines.get(x).charAt(y - 1) == '-') {
                            direction = Direction.LEFT;
                        } else if (y < lines.get(x).length() - 1 && lines.get(x).charAt(y + 1) == '-') {
                            direction = Direction.RIGHT;
                        }
                    } else {
                        if (x > 0 && lines.get(x - 1).charAt(y) == '|') {
                            direction = Direction.UP;
                        } else if (x < lines.size() - 1 && lines.get(x + 1).charAt(y) == '|') {
                            direction = Direction.DOWN;
                        }
                    }
                }
                default -> path.add(currentChar);
            }

            if (currentChar == ' ') {
                break;
            }

            steps++;
        }

        return steps;
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        List<Character> path = new ArrayList<>();
        route(lines, path);

        return path.stream().map(Object::toString).collect(Collectors.joining());
    }

    @Override
    public String getFirstStarSolution() {
        //noinspection SpellCheckingInspection
        return "DWNBGECOMY";
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        return route(lines, new ArrayList<>());
    }

    @Override
    public Integer getSecondStarSolution() {
        return 17_228;
    }

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
