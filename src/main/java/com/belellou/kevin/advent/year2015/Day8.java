package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.Day;
import com.belellou.kevin.advent.generic.DisableTest;
import com.belellou.kevin.advent.generic.Year;

@SuppressWarnings("unused")
public class Day8 extends AbstractDaySolver {

    public Day8() {
        super(Year.YEAR_2015, Day.DAY_8);
    }

    private static int countMemoryLength(String line) {
        int memoryLength = -2;
        boolean foundBackslash = false;
        int hexadecimalCharCount = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\\') {
                foundBackslash = !foundBackslash;

                if (foundBackslash) {
                    continue;
                }
            } else if (foundBackslash && c == 'x') {
                hexadecimalCharCount++;
                memoryLength++;
                foundBackslash = false;
                continue;
            }

            if (hexadecimalCharCount == 3) {
                hexadecimalCharCount = 0;
            } else if (hexadecimalCharCount > 0) {
                hexadecimalCharCount++;
                continue;
            }

            memoryLength++;
            foundBackslash = false;
        }

//        System.out.println("Line " + line + " code length is " + line.length());
//        System.out.println("Line " + line + " memory length is " + memoryLength);

        if (line.length() - memoryLength < 2) {
            throw new IllegalStateException("wat ");
        }
        if (memoryLength < 0) {
            throw new IllegalStateException("wut");
        }
        if (!line.startsWith("\"") || !line.endsWith("\"")) {
            throw new IllegalStateException("wot");
        }

        return memoryLength;
    }

    @Override
    protected int doSolveFirstStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        int totalCodeLength = lines.stream().map(String::length).reduce(Integer::sum).orElseThrow();
        int totalMemoryLength = lines.stream().map(Day8::countMemoryLength).reduce(Integer::sum).orElseThrow();

//        System.out.println("total code length is " + totalCodeLength);
//        System.out.println("total memory length is " + totalMemoryLength);

        return totalCodeLength - totalMemoryLength;
    }

    @Override
    public int getFirstStarSolution() {
        return 0;
    }

    @Override
    protected int doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public int getSecondStarSolution() {
        return 0;
    }
}
