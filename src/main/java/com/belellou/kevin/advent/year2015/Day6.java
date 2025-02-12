package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day6 extends AbstractDaySolver<Integer, Integer> {

    private static final String GROUP_COMMAND = "command";
    private static final String GROUP_X0 = "x0";
    private static final String GROUP_Y0 = "y0";
    private static final String GROUP_X1 = "x1";
    private static final String GROUP_Y1 = "y1";

    private static final Pattern PATTERN = Pattern.compile("^" +
                                                                   "(?<" + GROUP_COMMAND + ">[\\s\\S]+) " +
                                                                   "(?<" + GROUP_X0 + ">\\d+)," +
                                                                   "(?<" + GROUP_Y0 + ">\\d+)" +
                                                                   " through " +
                                                                   "(?<" + GROUP_X1 + ">\\d+)," +
                                                                   "(?<" + GROUP_Y1 + ">\\d+)" +
                                                                   "$");

    private static final String COMMAND_TURN_ON = "turn on";
    private static final String COMMAND_TURN_OFF = "turn off";
    private static final String COMMAND_TOGGLE = "toggle";

    private static Instruction matcherToInstruction(Matcher matcher) {
        //noinspection ResultOfMethodCallIgnored
        matcher.matches();

        String command = matcher.group(GROUP_COMMAND);
        int x0 = Integer.parseInt(matcher.group(GROUP_X0));
        int y0 = Integer.parseInt(matcher.group(GROUP_Y0));
        int x1 = Integer.parseInt(matcher.group(GROUP_X1));
        int y1 = Integer.parseInt(matcher.group(GROUP_Y1));

        return new Instruction(command, x0, y0, x1, y1);
    }

    private static void flipLights(Boolean[][] lights, Instruction instruction, boolean turnOn, boolean toggle) {
        for (int i = instruction.x0; i <= instruction.x1; i++) {
            for (int j = instruction.y0; j <= instruction.y1; j++) {
                lights[i][j] = toggle ? !lights[i][j] : turnOn;
            }
        }
    }

    private static void brightLights(int[][] lights, Instruction instruction, boolean turnOn, boolean toggle) {
        int diff = toggle ? 2
                          : turnOn
                            ? 1
                            : -1;

        for (int i = instruction.x0; i <= instruction.x1; i++) {
            for (int j = instruction.y0; j <= instruction.y1; j++) {
                lights[i][j] = Math.max(lights[i][j] + diff, 0);
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Boolean[][] lights = new Boolean[1000][1000];
        Arrays.stream(lights).forEach(booleans -> Arrays.fill(booleans, false));

        reader.lines()
              .map(PATTERN::matcher)
              .map(Day6::matcherToInstruction)
              .forEach(instruction -> {

                  switch (instruction.command) {
                      case COMMAND_TURN_ON -> flipLights(lights, instruction, true, false);
                      case COMMAND_TURN_OFF -> flipLights(lights, instruction, false, false);
                      case COMMAND_TOGGLE -> flipLights(lights, instruction, false, true);
                      default -> throw new IllegalStateException("Unexpected value: " + instruction.command);
                  }
              });

        return (int) Arrays.stream(lights).flatMap(Arrays::stream).filter(Boolean.TRUE::equals).count();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 569_999;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        int[][] lights = new int[1000][1000];

        reader.lines()
              .map(PATTERN::matcher)
              .map(Day6::matcherToInstruction)
              .forEach(instruction -> {
                  switch (instruction.command) {
                      case COMMAND_TURN_ON -> brightLights(lights, instruction, true, false);
                      case COMMAND_TURN_OFF -> brightLights(lights, instruction, false, false);
                      case COMMAND_TOGGLE -> brightLights(lights, instruction, false, true);
                      default -> throw new IllegalStateException("Unexpected value: " + instruction.command);
                  }
              });

        return Arrays.stream(lights).flatMapToInt(Arrays::stream).reduce(Integer::sum).orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 17_836_115;
    }

    private record Instruction(String command, int x0, int y0, int x1, int y1) {}
}
