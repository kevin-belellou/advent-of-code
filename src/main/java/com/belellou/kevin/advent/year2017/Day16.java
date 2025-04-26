package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day16 extends AbstractDaySolver<String, String> {

    private static final Pattern PATTERN = Pattern.compile("^[sxp](?:(\\d+)$|(\\d+)/(\\d+)$|([a-p])/([a-p])$)");

    private static final char[] STARTING_DANCE_POSITIONS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};

    private static List<DanceStep> getDanceProgram(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine()
                                   .split(","))
                     .map(PATTERN::matcher)
                     .map(matcher -> {
                         if (!matcher.find()) {
                             throw new IllegalArgumentException("Invalid dance move");
                         }

                         DanceMove danceMove = DanceMove.fromChar(matcher.group(0).charAt(0));

                         return switch (danceMove) {
                             case SPIN -> new DanceStep(danceMove, Integer.parseInt(matcher.group(1)), 0);
                             case EXCHANGE -> new DanceStep(danceMove, Integer.parseInt(matcher.group(2)),
                                                            Integer.parseInt(matcher.group(3)));
                             case PARTNER ->
                                     new DanceStep(danceMove, matcher.group(4).charAt(0), matcher.group(5).charAt(0));
                         };
                     })
                     .toList();
    }

    private static void dance(char[] dancePositions, List<DanceStep> danceProgram) {
        // Allocate a temp array
        char[] temp = new char[dancePositions.length];

        // Create a position lookup array for operations
        int[] positionLookup = new int[113]; // ASCII 'a' to 'p' are 97 to 112
        for (int i = 0; i < dancePositions.length; i++) {
            positionLookup[dancePositions[i]] = i;
        }

        for (DanceStep danceStep : danceProgram) {
            switch (danceStep.danceMove) {
                case SPIN -> {
                    System.arraycopy(dancePositions, dancePositions.length - danceStep.firstOperand,
                                     temp, 0, danceStep.firstOperand);
                    System.arraycopy(dancePositions, 0, temp, danceStep.firstOperand,
                                     dancePositions.length - danceStep.firstOperand);
                    System.arraycopy(temp, 0, dancePositions, 0, dancePositions.length);

                    // Could be optimized
                    for (int i = 0; i < dancePositions.length; i++) {
                        positionLookup[dancePositions[i]] = i;
                    }
                }
                case EXCHANGE -> {
                    char tempChar = dancePositions[danceStep.firstOperand];
                    dancePositions[danceStep.firstOperand] = dancePositions[danceStep.secondOperand];
                    dancePositions[danceStep.secondOperand] = tempChar;

                    // Update position lookup
                    positionLookup[dancePositions[danceStep.firstOperand]] = danceStep.firstOperand;
                    positionLookup[dancePositions[danceStep.secondOperand]] = danceStep.secondOperand;
                }
                case PARTNER -> {
                    int firstIndex = positionLookup[(char) danceStep.firstOperand];
                    int secondIndex = positionLookup[(char) danceStep.secondOperand];

                    char tempChar = dancePositions[firstIndex];
                    dancePositions[firstIndex] = dancePositions[secondIndex];
                    dancePositions[secondIndex] = tempChar;

                    // Update position lookup
                    positionLookup[dancePositions[firstIndex]] = firstIndex;
                    positionLookup[dancePositions[secondIndex]] = secondIndex;
                }
            }
        }
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) throws IOException {
        List<DanceStep> danceProgram = getDanceProgram(reader);

        char[] dancePositions = Arrays.copyOf(STARTING_DANCE_POSITIONS, STARTING_DANCE_POSITIONS.length);
        dance(dancePositions, danceProgram);

        return String.valueOf(dancePositions);
    }

    @Override
    public String getFirstStarSolution() {
        //noinspection SpellCheckingInspection
        return "padheomkgjfnblic";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) throws IOException {
        List<DanceStep> danceProgram = getDanceProgram(reader);

        char[] dancePositions = Arrays.copyOf(STARTING_DANCE_POSITIONS, STARTING_DANCE_POSITIONS.length);
        Map<String, Integer> seen = new HashMap<>();
        int iterations = 1_000_000_000;

        for (int i = 0; i < iterations; i++) {
            String currentState = String.valueOf(dancePositions);

            // If we've seen this state before, we found a cycle
            if (seen.containsKey(currentState)) {
                int cycleLength = i - seen.get(currentState);
                int remainingIterations = (iterations - i) % cycleLength;

                // Fast-forward to the end by only doing the remaining iterations
                for (int j = 0; j < remainingIterations; j++) {
                    dance(dancePositions, danceProgram);
                }
                break;
            }

            seen.put(currentState, i);
            dance(dancePositions, danceProgram);
        }

        return String.valueOf(dancePositions);
    }

    @Override
    public String getSecondStarSolution() {
        //noinspection SpellCheckingInspection
        return "bfcdeakhijmlgopn";
    }

    enum DanceMove {
        SPIN('s'),
        EXCHANGE('x'),
        PARTNER('p');

        private final char dance;

        DanceMove(char dance) {
            this.dance = dance;
        }

        public static DanceMove fromChar(char dance) {
            for (DanceMove move : DanceMove.values()) {
                if (move.dance == dance) {
                    return move;
                }
            }

            throw new IllegalArgumentException("Invalid dance move");
        }
    }

    record DanceStep(DanceMove danceMove, int firstOperand, int secondOperand) {}
}
