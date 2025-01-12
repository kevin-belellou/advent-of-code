package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day2 extends AbstractDaySolver<String> {

    public Day2() {
        super(Day2.class);
    }

    private static String getCode(List<String> lines, KeypadButton startingButton) {
        List<KeypadButton> finalButtons = new ArrayList<>(lines.size());

        for (String line : lines) {
            KeypadButton button = startingButton;

            for (char c : line.toCharArray()) {
                switch (c) {
                    case 'U' -> button = button.getUp();
                    case 'D' -> button = button.getDown();
                    case 'L' -> button = button.getLeft();
                    case 'R' -> button = button.getRight();
                    default -> throw new IllegalStateException("Unexpected value: " + c);
                }
            }

            finalButtons.add(button);
        }

        return finalButtons.stream().map(KeypadButton::value).reduce(String::concat).orElseThrow();
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        return getCode(reader.lines().toList(), SimpleKeypadButton.FIVE);
    }

    @Override
    public String getFirstStarSolution() {
        return "97289";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
        return getCode(reader.lines().toList(), AdvancedKeypadButton.FIVE);
    }

    @Override
    public String getSecondStarSolution() {
        return "9A7DC";
    }

    private enum SimpleKeypadButton implements KeypadButton {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE;

        static {
            ONE.up = ONE;
            ONE.down = FOUR;
            ONE.left = ONE;
            ONE.right = TWO;

            TWO.up = TWO;
            TWO.down = FIVE;
            TWO.left = ONE;
            TWO.right = THREE;

            THREE.up = THREE;
            THREE.down = SIX;
            THREE.left = TWO;
            THREE.right = THREE;

            FOUR.up = ONE;
            FOUR.down = SEVEN;
            FOUR.left = FOUR;
            FOUR.right = FIVE;

            FIVE.up = TWO;
            FIVE.down = EIGHT;
            FIVE.left = FOUR;
            FIVE.right = SIX;

            SIX.up = THREE;
            SIX.down = NINE;
            SIX.left = FIVE;
            SIX.right = SIX;

            SEVEN.up = FOUR;
            SEVEN.down = SEVEN;
            SEVEN.left = SEVEN;
            SEVEN.right = EIGHT;

            EIGHT.up = FIVE;
            EIGHT.down = EIGHT;
            EIGHT.left = SEVEN;
            EIGHT.right = NINE;

            NINE.up = SIX;
            NINE.down = NINE;
            NINE.left = EIGHT;
            NINE.right = NINE;
        }

        public SimpleKeypadButton up;
        public SimpleKeypadButton down;
        public SimpleKeypadButton left;
        public SimpleKeypadButton right;

        @Override
        public String value() {
            return String.valueOf(ordinal() + 1);
        }

        @Override
        public KeypadButton getUp() {
            return up;
        }

        @Override
        public KeypadButton getDown() {
            return down;
        }

        @Override
        public KeypadButton getLeft() {
            return left;
        }

        @Override
        public KeypadButton getRight() {
            return right;
        }
    }

    private enum AdvancedKeypadButton implements KeypadButton {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        A,
        B,
        C,
        D;

        static {
            ONE.up = ONE;
            ONE.down = THREE;
            ONE.left = ONE;
            ONE.right = ONE;

            TWO.up = TWO;
            TWO.down = SIX;
            TWO.left = TWO;
            TWO.right = THREE;

            THREE.up = ONE;
            THREE.down = SEVEN;
            THREE.left = TWO;
            THREE.right = FOUR;

            FOUR.up = FOUR;
            FOUR.down = EIGHT;
            FOUR.left = THREE;
            FOUR.right = FOUR;

            FIVE.up = FIVE;
            FIVE.down = FIVE;
            FIVE.left = FIVE;
            FIVE.right = SIX;

            SIX.up = TWO;
            SIX.down = A;
            SIX.left = FIVE;
            SIX.right = SEVEN;

            SEVEN.up = THREE;
            SEVEN.down = B;
            SEVEN.left = SIX;
            SEVEN.right = EIGHT;

            EIGHT.up = FOUR;
            EIGHT.down = C;
            EIGHT.left = SEVEN;
            EIGHT.right = NINE;

            NINE.up = NINE;
            NINE.down = NINE;
            NINE.left = EIGHT;
            NINE.right = NINE;

            A.up = SIX;
            A.down = A;
            A.left = A;
            A.right = B;

            B.up = SEVEN;
            B.down = D;
            B.left = A;
            B.right = C;

            C.up = EIGHT;
            C.down = C;
            C.left = B;
            C.right = C;

            D.up = B;
            D.down = D;
            D.left = D;
            D.right = D;
        }

        public AdvancedKeypadButton up;
        public AdvancedKeypadButton down;
        public AdvancedKeypadButton left;
        public AdvancedKeypadButton right;

        @Override
        public String value() {
            return switch (this) {
                case ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE -> String.valueOf(ordinal() + 1);
                case A, B, C, D -> name();
            };
        }

        @Override
        public KeypadButton getUp() {
            return up;
        }

        @Override
        public KeypadButton getDown() {
            return down;
        }

        @Override
        public KeypadButton getLeft() {
            return left;
        }

        @Override
        public KeypadButton getRight() {
            return right;
        }
    }

    private interface KeypadButton {

        String value();

        KeypadButton getUp();

        KeypadButton getDown();

        KeypadButton getLeft();

        KeypadButton getRight();
    }
}
