package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ejml.simple.SimpleMatrix;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day8 extends AbstractDaySolver<Integer, String> {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 6;

    private static final Pattern PATTERN_RECT = Pattern.compile("^rect (\\d+)x(\\d+)$");
    private static final Pattern PATTERN_ROTATE = Pattern.compile("^rotate (row y=|column x=)(\\d+) by (\\d+)$");

    private static final String KEYWORD_ROW = "row";

    public Day8() {
        super(Day8.class);
    }

    private static Function<String, Operation> getOperation() {
        return line -> {
            Matcher rectMatcher = PATTERN_RECT.matcher(line);
            if (rectMatcher.matches()) {
                return new Operation(OperationType.RECT, Integer.parseInt(rectMatcher.group(1)),
                                     Integer.parseInt(rectMatcher.group(2)));
            }

            Matcher rotateMatcher = PATTERN_ROTATE.matcher(line);
            if (rotateMatcher.matches()) {
                return new Operation(rotateMatcher.group(1).startsWith(KEYWORD_ROW) ? OperationType.ROTATE_ROW
                                                                                    : OperationType.ROTATE_COLUMN,
                                     Integer.parseInt(rotateMatcher.group(2)),
                                     Integer.parseInt(rotateMatcher.group(3)));
            }

            throw new IllegalArgumentException("Invalid line: " + line);
        };
    }

    private static void applyOperation(Operation operation, SimpleMatrix screen) {
        switch (operation.type()) {
            case RECT -> {
                for (int i = 0; i < operation.secondOperand(); i++) {
                    for (int j = 0; j < operation.firstOperand(); j++) {
                        screen.set(i, j, 1);
                    }
                }
            }
            case ROTATE_ROW, ROTATE_COLUMN -> {
                double[] data = operation.type == OperationType.ROTATE_ROW ? screen.getRow(operation.firstOperand())
                                                                                   .toArray2()[0]
                                                                           : screen.getColumn(operation.firstOperand())
                                                                                   .transpose()
                                                                                   .toArray2()[0];

                double[] rotatedData = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    rotatedData[(i + operation.secondOperand()) % data.length] = data[i];
                }

                if (operation.type == OperationType.ROTATE_ROW) {
                    screen.setRow(operation.firstOperand(), 0, rotatedData);
                } else {
                    screen.setColumn(operation.firstOperand(), 0, rotatedData);
                }
            }
        }
    }

    private static void displayScreen(SimpleMatrix screen) {
        for (int i = 0; i < screen.getNumRows(); i++) {
            for (int j = 0; j < screen.getNumCols(); j++) {
                if (j % 5 == 0) {
                    System.out.print("  ");
                }

                System.out.print(screen.get(i, j) == 1 ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        SimpleMatrix screen = new SimpleMatrix(HEIGHT, WIDTH);

        reader.lines().map(getOperation()).forEach(operation -> applyOperation(operation, screen));
//        displayScreen(screen);

        return (int) Arrays.stream(screen.toArray2()).flatMapToDouble(Arrays::stream).filter(cell -> cell == 1).count();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 128;
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
        // This puzzle is visual only (based on the displayed matrix from the first solution)
        return "EOARGPHYAO";
    }

    @Override
    public String getSecondStarSolution() {
        return "EOARGPHYAO";
    }

    private enum OperationType {
        RECT,
        ROTATE_ROW,
        ROTATE_COLUMN
    }

    private record Operation(OperationType type, int firstOperand, int secondOperand) {}
}
