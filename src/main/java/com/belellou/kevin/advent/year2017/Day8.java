package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day8 extends AbstractDaySolver<Integer, Integer> {

    private static void computeInstructions(BufferedReader reader, List<Register> registers) {
        reader.lines()
              .map(line -> Instruction.of(line, registers))
              .forEach(Day8::computeInstruction);
    }

    private static void computeInstruction(Instruction instruction) {
        boolean conditionIsValid = instruction.conditionOperator.predicate.test(
                instruction.conditionRegister.getValue(),
                instruction.conditionValue);

        if (!conditionIsValid) {
            return;
        }

        instruction.target.setValue(
                instruction.operator.function.applyAsInt(instruction.target.getValue(), instruction.value));
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Register> registers = new ArrayList<>();
        computeInstructions(reader, registers);

        return registers.stream()
                        .mapToInt(Register::getValue)
                        .max()
                        .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 6_343;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Register> registers = new ArrayList<>();
        computeInstructions(reader, registers);

        return registers.stream()
                        .mapToInt(Register::getMaxValue)
                        .max()
                        .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 7_184;
    }

    private enum Operator {
        INCREMENT("inc", Integer::sum),
        DECREMENT("dec", (x, y) -> x - y);

        public final String symbol;
        public final ToIntBiFunction<Integer, Integer> function;

        Operator(String symbol, ToIntBiFunction<Integer, Integer> function) {
            this.symbol = symbol;
            this.function = function;
        }

        public static Operator of(String symbol) {
            for (Operator operator : Operator.values()) {
                if (operator.symbol.equals(symbol)) {
                    return operator;
                }
            }

            throw new IllegalArgumentException("Invalid operator " + symbol);
        }
    }

    private enum ConditionOperator {
        GREATER(">", (x, y) -> x > y),
        GREATER_OR_EQUAL(">=", (x, y) -> x >= y),
        LESS("<", (x, y) -> x < y),
        LESS_OR_EQUAL("<=", (x, y) -> x <= y),
        EQUAL("==", Integer::equals),
        NOT_EQUAL("!=", (x, y) -> !x.equals(y));

        public final String symbol;
        public final BiPredicate<Integer, Integer> predicate;

        ConditionOperator(String symbol, BiPredicate<Integer, Integer> predicate) {
            this.symbol = symbol;
            this.predicate = predicate;
        }

        public static ConditionOperator of(String symbol) {
            for (ConditionOperator operator : ConditionOperator.values()) {
                if (operator.symbol.equals(symbol)) {
                    return operator;
                }
            }

            throw new IllegalArgumentException("Invalid condition operator " + symbol);
        }
    }

    private record Register(String name, ModifiableInteger value, ModifiableInteger maxValue) {

        public int getValue() {
            return value.getValue();
        }

        public void setValue(int value) {
            this.value.setValue(value);

            setMaxValue(value);
        }

        public int getMaxValue() {
            return maxValue.getValue();
        }

        private void setMaxValue(int maxValue) {
            this.maxValue.setValue(Math.max(this.getMaxValue(), maxValue));
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Register register)) {
                return false;
            }
            return Objects.equals(name, register.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
    }

    private record Instruction(Register target, Operator operator, int value, Register conditionRegister,
                               ConditionOperator conditionOperator, int conditionValue) {

        private static final Pattern PATTERN = Pattern.compile(
                "([a-z]+) (inc|dec) (-?\\d+) if ([a-z]+) ([<>!=]+) (-?\\d+)");

        public static Instruction of(String line, List<Register> registers) {
            Matcher matcher = PATTERN.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid instruction format: " + line);
            }

            Register target = getOrCreateRegister(matcher.group(1), registers);
            Operator operator = Operator.of(matcher.group(2));
            int value = Integer.parseInt(matcher.group(3));
            Register conditionRegister = getOrCreateRegister(matcher.group(4), registers);
            ConditionOperator conditionOperator = ConditionOperator.of(matcher.group(5));
            int conditionValue = Integer.parseInt(matcher.group(6));

            return new Instruction(target, operator, value, conditionRegister, conditionOperator, conditionValue);
        }

        private static Register getOrCreateRegister(String name, List<Register> registers) {
            return registers.stream()
                            .filter(register -> register.name.equals(name))
                            .findFirst()
                            .orElseGet(() -> {
                                Register register = new Register(name, new ModifiableInteger(0),
                                                                 new ModifiableInteger(0));
                                registers.add(register);
                                return register;
                            });
        }
    }
}
