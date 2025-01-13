package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day11 extends AbstractDaySolver<String, String> {

    public Day11() {
        super(Day11.class);
    }

    private static boolean isPasswordInvalid(String password) {
        if (password.matches("^.*[iol].*$")) {
            return true;
        }

        boolean firstPairFound = false;
        char firstPairChar = 0;
        boolean secondPairFound = false;

        boolean straightFound = false;

        for (int i = 0; i < password.length() - 1; i++) {
            char currentChar = password.charAt(i);
            char nextChar = password.charAt(i + 1);

            if (nextChar == currentChar) {
                if (!firstPairFound) {
                    firstPairFound = true;
                    firstPairChar = currentChar;
                } else if (nextChar != firstPairChar) {
                    secondPairFound = true;
                }
            } else if (nextChar == currentChar + 1 && i < password.length() - 2) {
                char nextNextChar = password.charAt(i + 2);

                if (nextNextChar == nextChar + 1) {
                    straightFound = true;
                }
            }

            if (firstPairFound && secondPairFound && straightFound) {
                return false;
            }
        }

        return true;
    }

    private static String incrementPassword(String password) {
        StringBuilder newPassword = new StringBuilder();

        boolean incrementNextChar = true;

        for (int i = password.length() - 1; i >= 0; i--) {
            char currentChar = password.charAt(i);

            if (!incrementNextChar) {
                newPassword.append(currentChar);
                continue;
            }

            if (currentChar == 'z') {
                newPassword.append('a');
            } else {
                newPassword.append((char) (currentChar + 1));
                incrementNextChar = false;
            }
        }

        return newPassword.reverse().toString();
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        String password = reader.lines().findFirst().orElseThrow();

        do {
            password = incrementPassword(password);
        } while (isPasswordInvalid(password));

        return password;
    }

    @Override
    public String getFirstStarSolution() {
        return "vzbxxyzz";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
        String password = getFirstStarSolution();

        do {
            password = incrementPassword(password);
        } while (isPasswordInvalid(password));

        return password;
    }

    @Override
    public String getSecondStarSolution() {
        return "vzcaabcc";
    }
}
