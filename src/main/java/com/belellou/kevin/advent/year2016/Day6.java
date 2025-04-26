package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day6 extends AbstractDaySolver<String, String> {

    private static List<Map<Character, Integer>> extractFrequencies(BufferedReader reader) {
        List<Map<Character, Integer>> frequencies = new ArrayList<>();

        reader.lines().forEach(line -> {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (frequencies.size() <= i) {
                    frequencies.add(new HashMap<>());
                }

                frequencies.get(i).put(c, frequencies.get(i).getOrDefault(c, 0) + 1);
            }
        });

        return frequencies;
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        List<Map<Character, Integer>> frequencies = extractFrequencies(reader);

        return frequencies.stream()
                          .map(frequency -> frequency.entrySet()
                                                     .stream()
                                                     .max(Comparator.comparingInt(Map.Entry::getValue))
                                                     .orElseThrow())
                          .map(Map.Entry::getKey)
                          .map(String::valueOf)
                          .reduce(String::concat)
                          .orElseThrow();
    }

    @Override
    public String getFirstStarSolution() {
        //noinspection SpellCheckingInspection
        return "agmwzecr";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) {
        List<Map<Character, Integer>> frequencies = extractFrequencies(reader);

        return frequencies.stream()
                          .map(frequency -> frequency.entrySet()
                                                     .stream()
                                                     .min(Comparator.comparingInt(Map.Entry::getValue))
                                                     .orElseThrow())
                          .map(Map.Entry::getKey)
                          .map(String::valueOf)
                          .reduce(String::concat)
                          .orElseThrow();
    }

    @Override
    public String getSecondStarSolution() {
        //noinspection SpellCheckingInspection
        return "owlaxqvq";
    }
}
