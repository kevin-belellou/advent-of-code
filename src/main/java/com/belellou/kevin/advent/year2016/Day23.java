package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day23 extends AbstractDaySolver<Integer, Integer> {

    private static final String REGISTER_A = "a";
    private static final String REGISTER_B = "b";
    private static final String REGISTER_C = "c";
    private static final String REGISTER_D = "d";

    private static List<Instruction> getInstructions(BufferedReader reader) {
        return reader.lines().map(Day23::parseInstruction).toList();
    }

    private static Instruction parseInstruction(String line) {
        String[] split = line.split(" ");

        InstructionType type = InstructionType.valueOf(split[0].toUpperCase());
        String firstOperand = split[1];
        String secondOperand = split.length > 2 ? split[2] : null;

        return new Instruction(type, firstOperand, secondOperand, false);
    }

    private static Map<String, ModifiableInteger> getRegisters(int registerAValue) {
        return Map.of(REGISTER_A, new ModifiableInteger(registerAValue), //
                      REGISTER_B, new ModifiableInteger(0), //
                      REGISTER_C, new ModifiableInteger(0), //
                      REGISTER_D, new ModifiableInteger(0));
    }

    private static void executeInstructions(List<Instruction> instructions, Map<String, ModifiableInteger> registers) {
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);

            if (!instruction.isValid()) {
                continue;
            }

            switch (instruction.getType()) {
                case CPY -> {
                    if (Instruction.isNumber(instruction.firstOperand)) {
                        registers.get(instruction.secondOperand).setValue(Integer.parseInt(instruction.firstOperand));
                    } else {
                        registers.get(instruction.secondOperand)
                                 .setValue(registers.get(instruction.firstOperand).getValue());
                    }
                }
                case INC -> registers.get(instruction.firstOperand).increment();
                case DEC -> registers.get(instruction.firstOperand).decrement();
                case JNZ -> {
                    if (Instruction.isNumber(instruction.firstOperand) &&
                            Integer.parseInt(instruction.firstOperand) != 0 ||
                            Instruction.isRegister(instruction.firstOperand) &&
                                    registers.get(instruction.firstOperand).getValue() != 0) {
                        i += Integer.parseInt(instruction.secondOperand) - 1;
                    }
                }
                case TGL -> {
                    int offset =
                            Instruction.isNumber(instruction.firstOperand) ? Integer.parseInt(instruction.firstOperand)
                                                                           : registers.get(instruction.firstOperand)
                                                                                      .getValue();

                    int indexToToggle = i + offset;
                    if (indexToToggle < 0 || indexToToggle >= instructions.size()) {
                        continue;
                    }

                    instructions.set(indexToToggle, instructions.get(indexToToggle).toggle());
                }
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Instruction> instructions = getInstructions(reader);
        Map<String, ModifiableInteger> registers = getRegisters(7);

        executeInstructions(instructions, registers);
        return registers.get(REGISTER_A).getValue();
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

    private enum InstructionType {
        CPY,
        INC,
        DEC,
        JNZ,
        TGL
    }

    private record Instruction(InstructionType type, String firstOperand, String secondOperand, boolean toggled) {

        public static boolean isNumber(String operand) {
            return NumberUtils.isCreatable(operand);
        }

        public static boolean isRegister(String operand) {
            return !isNumber(operand);
        }

        public InstructionType getType() {
            if (!toggled) {
                return type;
            }

            return switch (type) {
                case INC -> InstructionType.DEC;
                case DEC, TGL -> InstructionType.INC;
                case JNZ -> InstructionType.CPY;
                case CPY -> InstructionType.JNZ;
            };
        }

        public boolean isValid() {
            return switch (getType()) {
                case CPY -> isRegister(secondOperand);
                case INC, DEC -> isRegister(firstOperand);
                case JNZ -> isNumber(secondOperand);
                case TGL -> isNumber(firstOperand);
            };
        }

        public Instruction toggle() {
            if (toggled) {
                return this;
            }
            return new Instruction(type, firstOperand, secondOperand, true);
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "type=" + type +
                    ", firstOperand='" + firstOperand + '\'' +
                    ", secondOperand='" + secondOperand + '\'' +
                    ", toggled=" + toggled +
                    ", valid=" + isValid() +
                    '}';
        }
    }
}
