package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day12 extends AbstractDaySolver<Integer, Integer> {

    public Day12() {
        super(Day12.class);
    }

    private static List<Instruction> getInstructions(BufferedReader reader) {
        return reader.lines().map(Day12::parseInstruction).toList();
    }

    private static Instruction parseInstruction(String line) {
        String[] split = line.split(" ");

        InstructionType type = InstructionType.valueOf(split[0].toUpperCase());
        String firstOperand = split[1];
        String secondOperand = split.length > 2 ? split[2] : null;

        return new Instruction(type, firstOperand, secondOperand);
    }

    private static void executeInstructions(List<Instruction> instructions, Map<String, ModifiableInteger> registers) {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);

            switch (instruction.type) {
                case CPY -> {
                    if (NumberUtils.isDigits(instruction.firstOperand)) {
                        registers.get(instruction.secondOperand).setValue(Integer.parseInt(instruction.firstOperand));
                    } else {
                        registers.get(instruction.secondOperand)
                                 .setValue(registers.get(instruction.firstOperand).getValue());
                    }
                }
                case INC -> registers.get(instruction.firstOperand).increment();
                case DEC -> registers.get(instruction.firstOperand).decrement();
                case JNZ -> {
                    if (NumberUtils.isDigits(instruction.firstOperand) &&
                            Integer.parseInt(instruction.firstOperand) != 0 ||
                            !NumberUtils.isDigits(instruction.firstOperand) &&
                                    registers.get(instruction.firstOperand).getValue() != 0) {
                        i += Integer.parseInt(instruction.secondOperand) - 1;
                    }
                }
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Instruction> instructions = getInstructions(reader);
        Map<String, ModifiableInteger> registers = Map.of("a", new ModifiableInteger(0), "b", new ModifiableInteger(0),
                                                          "c", new ModifiableInteger(0), "d", new ModifiableInteger(0));

        executeInstructions(instructions, registers);
        return registers.get("a").getValue();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 318_117;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Instruction> instructions = getInstructions(reader);
        Map<String, ModifiableInteger> registers = Map.of("a", new ModifiableInteger(0), "b", new ModifiableInteger(0),
                                                          "c", new ModifiableInteger(1), "d", new ModifiableInteger(0));

        executeInstructions(instructions, registers);
        return registers.get("a").getValue();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 9_227_771;
    }

    private enum InstructionType {
        CPY,
        INC,
        DEC,
        JNZ
    }

    private record Instruction(InstructionType type, String firstOperand, String secondOperand) {}
}
