package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day23 extends AbstractDaySolver<Integer, Integer> {

    public Day23() {
        super(Day23.class);
    }

    private static void executeProgram(BufferedReader reader, AtomicLong a, AtomicLong b) {
        List<Instruction> instructions = reader.lines().map(line -> Instruction.parse(line, a, b)).toList();

        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);

            switch (instruction.type) {
                case HALF -> instruction.register.set(instruction.register.get() / 2L);
                case TRIPLE -> instruction.register.set(instruction.register.get() * 3L);
                case INCREMENT -> instruction.register.incrementAndGet();
                case JUMP -> i += instruction.offset - 1;
                case JUMP_IF_EVEN -> {
                    if (instruction.register.get() % 2L == 0) {
                        i += instruction.offset - 1;
                    }
                }
                case JUMP_IF_ONE -> {
                    if (instruction.register.get() == 1) {
                        i += instruction.offset - 1;
                    }
                }
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        AtomicLong a = new AtomicLong(0);
        AtomicLong b = new AtomicLong(0);

        executeProgram(reader, a, b);

        return b.intValue();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 170;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        AtomicLong a = new AtomicLong(1);
        AtomicLong b = new AtomicLong(0);

        executeProgram(reader, a, b);

        return b.intValue();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 247;
    }

    private enum InstructionType {

        HALF("hlf"),
        TRIPLE("tpl"),
        INCREMENT("inc"),
        JUMP("jmp"),
        JUMP_IF_EVEN("jie"),
        JUMP_IF_ONE("jio");

        public final String code;

        InstructionType(String code) {
            this.code = code;
        }

        public static InstructionType fromCode(String code) {
            for (InstructionType instructionType : values()) {
                if (instructionType.code.equals(code)) {
                    return instructionType;
                }
            }
            throw new IllegalArgumentException("Unknown code: " + code);
        }
    }

    private record Instruction(InstructionType type, AtomicLong register, int offset) {

        private static final String SPACE = " ";
        private static final String A = "a";

        public static Instruction parse(String line, AtomicLong registerA, AtomicLong registerB) {
            String[] split = line.split(SPACE);

            InstructionType type = InstructionType.fromCode(split[0]);

            AtomicLong register = switch (type) {
                case JUMP -> null;
                default -> split[1].startsWith(A) ? registerA : registerB;
            };

            int offset = switch (type) {
                case JUMP -> Integer.parseInt(split[1]);
                case JUMP_IF_EVEN, JUMP_IF_ONE -> Integer.parseInt(split[2]);
                default -> 0;
            };

            return new Instruction(type, register, offset);
        }
    }
}
