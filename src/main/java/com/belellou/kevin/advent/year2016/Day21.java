package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day21 extends AbstractDaySolver<String, String> {

    private static final String PASSWORD_1 = "abcdefgh";
    private static final String PASSWORD_2 = "fbgdceah";

    private static final String SPACE = " ";
    private static final String UNDERSCORE = "_";

    private static Operation parseOperation(String line) {
        String[] split = line.split(SPACE);

        OperationType operationType = OperationType.valueOf((split[0] + UNDERSCORE + split[1]).toUpperCase());
        return switch (operationType) {
            case SWAP_POSITION, MOVE_POSITION ->
                    new Operation(operationType, Integer.parseInt(split[2]), Integer.parseInt(split[5]));
            case SWAP_LETTER -> new Operation(operationType, split[2].charAt(0), split[5].charAt(0));
            case ROTATE_LEFT, ROTATE_RIGHT ->
                    new Operation(operationType, Integer.parseInt(split[2]), Integer.MIN_VALUE);
            case ROTATE_BASED -> new Operation(operationType, split[6].charAt(0), Integer.MIN_VALUE);
            case REVERSE_POSITIONS ->
                    new Operation(operationType, Integer.parseInt(split[2]), Integer.parseInt(split[4]));
        };
    }

    private static String applyOperations(List<Operation> operations, boolean reverse, String password) {
        char[] array = password.toCharArray();

        for (Operation operation : operations) {
            switch (operation.type) {
                case SWAP_POSITION -> {
                    ArrayUtils.swap(array, operation.firstOperand, operation.secondOperand);
                }
                case SWAP_LETTER -> {
                    int firstOperandIndex = getIndexedOf(array, operation.firstOperand);
                    int secondOperandIndex = getIndexedOf(array, operation.secondOperand);
                    ArrayUtils.swap(array, firstOperandIndex, secondOperandIndex);
                }
                case ROTATE_LEFT -> ArrayUtils.shift(array, reverse ? operation.firstOperand : -operation.firstOperand);
                case ROTATE_RIGHT ->
                        ArrayUtils.shift(array, reverse ? -operation.firstOperand : operation.firstOperand);
                case ROTATE_BASED -> {
                    int charIndex = getIndexedOf(array, operation.firstOperand);
                    if (reverse) {
                        int tmp = charIndex + 2 * array.length;
                        int oldIndex = ((tmp - 1) / 2) % array.length;
                        if (oldIndex >= 4) {
                            oldIndex = ((tmp - 2) / 2) % array.length;
                        }

                        int numberOfRotations = oldIndex < 4 ? oldIndex + 1 : oldIndex + 2;

                        ArrayUtils.shift(array, -numberOfRotations);
                    } else {
                        int numberOfRotations = charIndex < 4 ? charIndex + 1 : charIndex + 2;

                        ArrayUtils.shift(array, numberOfRotations);
                    }
                }
                case REVERSE_POSITIONS ->
                        ArrayUtils.reverse(array, operation.firstOperand, operation.secondOperand + 1);
                case MOVE_POSITION -> {
                    if (reverse) {
                        char c = array[operation.secondOperand];
                        array = ArrayUtils.insert(operation.firstOperand,
                                                  ArrayUtils.remove(array, operation.secondOperand), c);
                    } else {
                        char c = array[operation.firstOperand];
                        array = ArrayUtils.insert(operation.secondOperand,
                                                  ArrayUtils.remove(array, operation.firstOperand), c);
                    }
                }
            }
        }

        return String.valueOf(array);
    }

    private static int getIndexedOf(char[] array, int charCode) {
        return ArrayUtils.indexOf(array, (char) charCode);
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        List<Operation> list = reader.lines().map(Day21::parseOperation).toList();

        return applyOperations(list, false, PASSWORD_1);
    }

    @Override
    public String getFirstStarSolution() {
        return "agcebfdh";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
        List<Operation> list = reader.lines().map(Day21::parseOperation).toList().reversed();

        return applyOperations(list, true, PASSWORD_2);
    }

    @DisableTest
    @Override
    public String getSecondStarSolution() {
        return "1";
    }

    private enum OperationType {
        SWAP_POSITION,
        SWAP_LETTER,
        ROTATE_LEFT,
        ROTATE_RIGHT,
        ROTATE_BASED,
        REVERSE_POSITIONS,
        MOVE_POSITION;

        public boolean useLetters() {
            return switch (this) {
                case SWAP_POSITION, ROTATE_LEFT, ROTATE_RIGHT, REVERSE_POSITIONS, MOVE_POSITION -> false;
                case SWAP_LETTER, ROTATE_BASED -> true;
            };
        }
    }

    private record Operation(OperationType type, int firstOperand, int secondOperand) {}
}
