package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day9 extends AbstractDaySolver<Integer, Integer> {

    private static int readLine(BufferedReader reader, List<Group> groups) throws IOException {
        String line = reader.readLine();

        Group firstGroup = Group.of(1, 0);
        groups.add(firstGroup);

        return readGroup(firstGroup, line, groups);
    }

    private static int readGroup(Group currentGroup, String line, List<Group> groups) {
        boolean isGarbage = false;
        boolean isEscaped = false;

        int garbageCount = 0;

        for (int i = currentGroup.start + 1; i < line.length(); i++) {
            if (isEscaped) {
                isEscaped = false;
                continue;
            }

            char currentChar = line.charAt(i);

            switch (currentChar) {
                case '!' -> {
                    if (isGarbage) {
                        isEscaped = true;
                    }
                }
                case '<' -> {
                    if (!isGarbage) {
                        isGarbage = true;
                    } else {
                        garbageCount++;
                    }
                }
                case '>' -> {
                    if (isGarbage) {
                        isGarbage = false;
                    }
                }
                case '{' -> {
                    if (!isGarbage) {
                        Group newGroup = Group.of(currentGroup.score + 1, i);
                        groups.add(newGroup);

                        garbageCount += readGroup(newGroup, line, groups);

                        i = newGroup.getEnd();
                    } else {
                        garbageCount++;
                    }
                }
                case '}' -> {
                    if (!isGarbage) {
                        currentGroup.setEnd(i);
                        return garbageCount;
                    } else {
                        garbageCount++;
                    }
                }
                default -> {
                    if (isGarbage) {
                        garbageCount++;
                    }
                }
            }
        }

        throw new IllegalArgumentException("Improper input");
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        List<Group> groups = new ArrayList<>();
        readLine(reader, groups);

        return groups.stream()
                     .mapToInt(Group::score)
                     .sum();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 12_396;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        List<Group> groups = new ArrayList<>();
        return readLine(reader, groups);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 6_346;
    }

    private record Group(int score, int start, ModifiableInteger end) {

        public static Group of(int score, int start) {
            return new Group(score, start, new ModifiableInteger(Integer.MIN_VALUE));
        }

        public int getEnd() {
            return end.getValue();
        }

        public void setEnd(int end) {
            this.end.setValue(end);
        }
    }
}
