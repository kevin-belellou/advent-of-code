package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.ArrayUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day2 extends AbstractDaySolver<Integer, String> {

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        AtomicInteger twoCount = new AtomicInteger();
        AtomicInteger threeCount = new AtomicInteger();

        reader.lines().forEach(line -> {
            int[] counts = new int[26];

            for (char c : line.toCharArray()) {
                counts[c - 'a']++;
            }

            if (ArrayUtils.contains(counts, 2)) {
                twoCount.getAndIncrement();
            }
            if (ArrayUtils.contains(counts, 3)) {
                threeCount.getAndIncrement();
            }
        });

        return twoCount.get() * threeCount.get();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 6_723;
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        for (int i = 0; i < lines.size(); i++) {
            String line1 = lines.get(i);

            for (int j = i + 1; j < lines.size(); j++) {
                String line2 = lines.get(j);

                int diffIndex = isSimilar(line1, line2);
                if (diffIndex != -1) {
                    return line1.substring(0, diffIndex) + line1.substring(diffIndex + 1);
                }
            }
        }

        throw new IllegalStateException("No similar strings found");
    }

    @Override
    public String getSecondStarSolution() {
        //noinspection SpellCheckingInspection
        return "prtkqyluiusocwvaezjmhmfgx";
    }

    private static int isSimilar(String a, String b) {
        if (a.length() != b.length()) {
            return -1;
        }

        int diffIndex = -1;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (diffIndex != -1) {
                    return -1;
                }
                diffIndex = i;
            }
        }

        return diffIndex;
    }
}
