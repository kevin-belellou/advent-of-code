package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day12 extends AbstractDaySolver<Integer, Integer> {

    private static final String REGISTER_A = "a";
    private static final String REGISTER_B = "b";
    private static final String REGISTER_C = "c";
    private static final String REGISTER_D = "d";

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

    private static Map<String, ModifiableInteger> getRegisters(int registerCValue) {
        return Map.of(REGISTER_A, new ModifiableInteger(0), //
                      REGISTER_B, new ModifiableInteger(0), //
                      REGISTER_C, new ModifiableInteger(registerCValue), //
                      REGISTER_D, new ModifiableInteger(0));
    }

    private static void executeInstructions(List<Instruction> instructions, Map<String, ModifiableInteger> registers) {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);

            switch (instruction.type) {
                case CPY -> {
                    if (NumberUtils.isCreatable(instruction.firstOperand)) {
                        registers.get(instruction.secondOperand).setValue(Integer.parseInt(instruction.firstOperand));
                    } else {
                        registers.get(instruction.secondOperand)
                                 .setValue(registers.get(instruction.firstOperand).getValue());
                    }
                }
                case INC -> registers.get(instruction.firstOperand).increment();
                case DEC -> registers.get(instruction.firstOperand).decrement();
                case JNZ -> {
                    if (NumberUtils.isCreatable(instruction.firstOperand) &&
                            Integer.parseInt(instruction.firstOperand) != 0 ||
                            !NumberUtils.isCreatable(instruction.firstOperand) &&
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
        Map<String, ModifiableInteger> registers = getRegisters(0);

        executeInstructions(instructions, registers);
        return registers.get(REGISTER_A).getValue();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 318_117;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Instruction> instructions = getInstructions(reader);
        Map<String, ModifiableInteger> registers = getRegisters(1);

        executeInstructions(instructions, registers);
        return registers.get(REGISTER_A).getValue();
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
