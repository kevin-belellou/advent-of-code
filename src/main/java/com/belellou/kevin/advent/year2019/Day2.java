package com.belellou.kevin.advent.year2019;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.StructuredTaskScope;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

import static java.util.concurrent.StructuredTaskScope.Joiner;

@SuppressWarnings("unused")
public class Day2 extends AbstractDaySolver<Integer, Integer> {

    private static final int MAX = 99;
    private static final int TARGET = 19_690_720;

    private static List<Integer> getBaseProgram(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(","))
                     .map(Integer::valueOf)
                     .toList();
    }

    private static Integer runProgram(List<Integer> baseProgram, int noun, int verb) {
        List<Integer> program = new ArrayList<>(baseProgram);

        program.set(1, noun);
        program.set(2, verb);

        for (int i = 0; i < program.size(); i += 4) {
            Integer opcode = program.get(i);

            if (opcode == 99) {
                break;
            }

            Integer operand1 = program.get(i + 1);
            Integer operand2 = program.get(i + 2);
            Integer resultIndex = program.get(i + 3);

            switch (opcode) {
                case 1 -> program.set(resultIndex, program.get(operand1) + program.get(operand2));
                case 2 -> program.set(resultIndex, program.get(operand1) * program.get(operand2));
                default -> throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }
        }

        return program.getFirst();
    }

    private static Integer findTarget(List<Integer> baseProgram, int noun, int verb) {
        if (runProgram(new ArrayList<>(baseProgram), noun, verb) == 19690720) {
            return 100 * noun + verb;
        }

        throw new CompletionException("Target not found", null);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        return runProgram(getBaseProgram(reader), 12, 2);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 4_576_384;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        List<Integer> baseProgram = getBaseProgram(reader);

        try (StructuredTaskScope<Object, Object> scope = StructuredTaskScope.open(Joiner.anySuccessfulOrThrow())) {
            for (int noun = 0; noun <= MAX; noun++) {
                for (int verb = 0; verb <= MAX; verb++) {
                    int finalNoun = noun;
                    int finalVerb = verb;
                    scope.fork(() -> findTarget(baseProgram, finalNoun, finalVerb));
                }
            }

            return (Integer) scope.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getSecondStarSolution() {
        return 5_398;
    }
}
