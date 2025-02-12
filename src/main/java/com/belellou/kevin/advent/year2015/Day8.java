package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day8 extends AbstractDaySolver<Integer, Integer> {

    private static final Pattern PATTERN_HEXA = Pattern.compile("\\\\x[a-f0-9]{2}");
    private static final Pattern PATTERN_QUOTE = Pattern.compile("\\\\\"");
    private static final Pattern PATTERN_SLASH = Pattern.compile("\\\\\\\\");

    private static final Map<String, Integer> mem = new HashMap<>();
    private static final Map<String, Integer> memAgain = new HashMap<>();

    private static int countMemoryLengthAgain(String line) {
        String newLine = PATTERN_HEXA.matcher(line).replaceAll("-");
        newLine = PATTERN_QUOTE.matcher(newLine).replaceAll("-");
        newLine = PATTERN_SLASH.matcher(newLine).replaceAll("-");

        memAgain.put(line, newLine.length() - 2);

        return newLine.length() - 2;
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

            if (hexadecimalCharCount > 0) {
                hexadecimalCharCount++;

                if (hexadecimalCharCount == 3) {
                    hexadecimalCharCount = 0;
                }

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

        mem.put(line, memoryLength);

        return memoryLength;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        int totalCodeLength = lines.stream().map(String::length).reduce(Integer::sum).orElseThrow();
        int totalMemoryLength = lines.stream().map(Day8::countMemoryLength).reduce(Integer::sum).orElseThrow();
        int totalMemoryLengthAgain = lines.stream()
                                          .map(Day8::countMemoryLengthAgain)
                                          .reduce(Integer::sum)
                                          .orElseThrow();

//        System.out.println("total code length is " + totalCodeLength);
//        System.out.println("total memory length is " + totalMemoryLength);
//        System.out.println("total memory length again is " + totalMemoryLengthAgain);

        for (String line : lines) {
            Integer i1 = mem.get(line);
            Integer i2 = memAgain.get(line);

            if (!Objects.equals(i1, i2)) {
                System.out.println("Difference about " + line + " (mem: " + i1 + ", memAgain: " + i2 + ")");
            }
        }

        return totalCodeLength - totalMemoryLengthAgain;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1350;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 0;
    }
}
