package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.Day;
import com.belellou.kevin.advent.generic.Year;

@SuppressWarnings("unused")
public class Day5 extends AbstractDaySolver<Integer> {

    public Day5() {
        super(Year.YEAR_2015, Day.DAY_5);
    }

    private static boolean firstStarStringTest(String string) {
        int vowelCount = 0;
        char lastChar = '0';
        boolean foundDouble = false;

        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u');
        Set<String> forbidden = Set.of("ab", "cd", "pq", "xy");

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (forbidden.contains(lastChar + String.valueOf(c))) {
                return false;
            }

            if (vowels.contains(c)) {
                vowelCount++;
            }

            if (lastChar == c) {
                foundDouble = true;
            }

            lastChar = c;
        }

        return foundDouble && vowelCount >= 3;
    }

    private static boolean secondStarStringTest(String string) {
        char lastChar = '0';
        char lastLastChar = '0';

        Set<String> pairs = new HashSet<>();
        boolean foundPair = false;
        boolean lastPairWasDouble = false;

        boolean foundTrio = false;

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            String pair = lastChar + String.valueOf(c);

            if (pairs.contains(pair) && !(lastPairWasDouble && lastChar == c)) {
                foundPair = true;
            }

            if (lastLastChar == c) {
                foundTrio = true;
            }

            if (foundPair && foundTrio) {
                return true;
            }

            if (i >= 1) {
                pairs.add(pair);
            }

            lastPairWasDouble = !lastPairWasDouble && lastChar == c;

            lastLastChar = lastChar;
            lastChar = c;
        }

        return false;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines().filter(Day5::firstStarStringTest).toList().size();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 255;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return reader.lines().filter(Day5::secondStarStringTest).toList().size();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 55;
    }
}
