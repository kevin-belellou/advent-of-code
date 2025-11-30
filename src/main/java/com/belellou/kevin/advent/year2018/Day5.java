package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.io.IOException;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day5 extends AbstractDaySolver<Integer, Integer> {

    private static String reducePolymer(String polymer) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < polymer.length() - 1; i++) {
            char c1 = polymer.charAt(i);
            char c2 = polymer.charAt(i + 1);

            if (Math.abs(c1 - c2) != 32) {
                sb.append(c1);

                if (i == polymer.length() - 2) {
                    sb.append(c2);
                }
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    private static int reactPolymer(String polymer) {
        int lastSize;

        do {
            lastSize = polymer.length();
            polymer = reducePolymer(polymer);
        } while (polymer.length() != lastSize);

        return polymer.length();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        String polymer = reader.readLine();

        return reactPolymer(polymer);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 11_194;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        String polymer = reader.readLine();

        int minLength = Integer.MAX_VALUE;

        for (char c = 'a'; c <= 'z'; c++) {
            String newPolymer = polymer.replaceAll("[" + c + String.valueOf(c).toUpperCase() + "]", "");

            minLength = Math.min(minLength, reactPolymer(newPolymer));
        }

        return minLength;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 4_178;
    }
}
