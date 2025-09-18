package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day18 extends AbstractDaySolver<Long, Integer> {

    private static List<Instruction> parseInstructions(BufferedReader reader) {
        return reader.lines()
                     .map(Instruction::fromString)
                     .toList();
    }

    private static int executeInstruction(List<Instruction> instructions, int instructionIndex, Program firstProgram,
                                          Program secondProgram) {
        if (instructionIndex >= 0 && instructionIndex < instructions.size()) {
            Instruction instruction = instructions.get(instructionIndex);

            Optional<Long> executionResult = firstProgram.execute(instruction);

            if (executionResult.isPresent()) {
                switch (instruction.type()) {
                    case SOUND -> secondProgram.queue.add(executionResult.orElseThrow());
                    case RECOVER -> instructionIndex--;
                    case JUMP -> instructionIndex += executionResult.orElseThrow().intValue();
                    default -> throw new IllegalStateException("Invalid instruction type");
                }
            }
        }

        return instructionIndex;
    }

    @Override
    protected Long doSolveFirstStar(BufferedReader reader) {
        List<Instruction> instructions = parseInstructions(reader);

        Map<Character, Long> registers = new HashMap<>();
        long lastSound = Integer.MIN_VALUE;

        for (int i = 0; i >= 0 && i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);

            switch (instruction.type()) {
                case SOUND -> lastSound = instruction.firstOperandValue(registers);
                case SET -> registers.put((char) instruction.firstOperand(), instruction.secondOperandValue(registers));
                case ADD -> registers.put((char) instruction.firstOperand(),
                                          instruction.firstOperandValue(registers) +
                                                  instruction.secondOperandValue(registers));
                case MULTIPLY -> registers.put((char) instruction.firstOperand(),
                                               instruction.firstOperandValue(registers) *
                                                       instruction.secondOperandValue(registers));
                case MODULO -> registers.put((char) instruction.firstOperand(),
                                             instruction.firstOperandValue(registers) %
                                                     instruction.secondOperandValue(registers));
                case RECOVER -> {
                    if (instruction.firstOperandValue(registers) != 0) {
                        return lastSound;
                    }
                }
                case JUMP -> {
                    if (instruction.firstOperandValue(registers) > 0) {
                        i += (int) (instruction.secondOperandValue(registers) - 1);
                    }
                }
            }
        }

        throw new IllegalStateException("No solution found");
    }

    @Override
    public Long getFirstStarSolution() {
        return 4_601L;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Instruction> instructions = parseInstructions(reader);

        Program program0 = new Program(0);
        Program program1 = new Program(1);

        int i = 0;
        int j = 0;

        while (true) {
            i = executeInstruction(instructions, i, program0, program1);
            j = executeInstruction(instructions, j, program1, program0);

            i++;
            j++;

            if (program0.isWaitingForInput && program1.isWaitingForInput
                    || (i < 0 || i >= instructions.size()) && (j < 0 || j >= instructions.size())) {
                return program1.sendCounter;
            }
        }
    }

    @Override
    public Integer getSecondStarSolution() {
        return 6_858;
    }

    enum InstructionType {
        SOUND("snd"),
        SET("set"),
        ADD("add"),
        MULTIPLY("mul"),
        MODULO("mod"),
        RECOVER("rcv"),
        JUMP("jgz");

        private final String name;

        InstructionType(String name) {
            this.name = name;
        }

        public static InstructionType fromName(String name) {
            for (InstructionType instructionType : values()) {
                if (instructionType.name.equals(name)) {
                    return instructionType;
                }
            }
            throw new IllegalArgumentException("Invalid instruction type");
        }
    }

    private record Instruction(InstructionType type, int firstOperand, boolean isFirstOperandANumber, int secondOperand,
                               boolean isSecondOperandANumber) {

        public static Instruction fromString(String instruction) {
            String[] split = instruction.split(" ");

            InstructionType type = InstructionType.fromName(split[0]);

            Pair<Integer, Boolean> operand1 = extractOperand(split[1]);
            Pair<Integer, Boolean> operand2 = null;
            if (split.length == 3) {
                operand2 = extractOperand(split[2]);
            }

            return new Instruction(type, operand1.getLeft(), operand1.getRight(),
                                   operand2 != null ? operand2.getLeft() : 0,
                                   operand2 != null ? operand2.getRight() : false);
        }

        private static Pair<Integer, Boolean> extractOperand(String string) {
            boolean isSecondOperandANumber;
            int secondOperand;
            if (string.matches("-?\\d+")) {
                secondOperand = Integer.parseInt(string);
                isSecondOperandANumber = true;
            } else {
                secondOperand = string.charAt(0);
                isSecondOperandANumber = false;
            }

            return Pair.of(secondOperand, isSecondOperandANumber);
        }

        public long firstOperandValue(Map<Character, Long> registers) {
            return isFirstOperandANumber ? firstOperand : registers.getOrDefault((char) firstOperand, 0L);
        }

        public long secondOperandValue(Map<Character, Long> registers) {
            return isSecondOperandANumber ? secondOperand : registers.getOrDefault((char) secondOperand, 0L);
        }
    }

    private static class Program {

        public final Queue<Long> queue = new ArrayDeque<>();

        private final Map<Character, Long> registers = new HashMap<>();

        public boolean isWaitingForInput = false;
        public int sendCounter = 0;

        public Program(long id) {
            registers.put('p', id);
        }

        public Optional<Long> execute(Day18.Instruction instruction) {
            switch (instruction.type()) {
                case SOUND -> {
                    sendCounter++;
                    return Optional.of(instruction.firstOperandValue(registers));
                }
                case SET -> registers.put((char) instruction.firstOperand(), instruction.secondOperandValue(registers));
                case ADD -> registers.put((char) instruction.firstOperand(),
                                          instruction.firstOperandValue(registers) +
                                                  instruction.secondOperandValue(registers));
                case MULTIPLY -> registers.put((char) instruction.firstOperand(),
                                               instruction.firstOperandValue(registers) *
                                                       instruction.secondOperandValue(registers));
                case MODULO -> registers.put((char) instruction.firstOperand(),
                                             instruction.firstOperandValue(registers) %
                                                     instruction.secondOperandValue(registers));
                case RECOVER -> {
                    Long value = queue.poll();
                    if (value == null) {
                        isWaitingForInput = true;
                        return Optional.of(Long.MIN_VALUE);
                    } else {
                        registers.put((char) instruction.firstOperand(), value);
                        isWaitingForInput = false;
                    }
                }
                case JUMP -> {
                    if (instruction.firstOperandValue(registers) > 0) {
                        return Optional.of(instruction.secondOperandValue(registers) - 1);
                    }
                }
            }

            return Optional.empty();
        }
    }
}
