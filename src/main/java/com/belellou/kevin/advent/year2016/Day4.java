package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day4 extends AbstractDaySolver<Integer> {

    private static final Pattern PATTERN = Pattern.compile("^([a-z|-]+)-([0-9]+)\\[([a-z]{5})]$");

    public Day4() {
        super(Day4.class);
    }

    private static Optional<Integer> getSectorIdIfValid(Matcher matcher) {
        String name = matcher.group(1);
        int sectorId = Integer.parseInt(matcher.group(2));
        String checksum = matcher.group(3);

        Map<Character, Integer> occurrences = new HashMap<>();
        for (char c : name.toCharArray()) {
            if (c != '-') {
                occurrences.merge(c, 1, Integer::sum);
            }
        }

        String realChecksum = occurrences.entrySet()
                                         .stream()
                                         .sorted(getEntryComparator())
                                         .limit(5)
                                         .map(Map.Entry::getKey)
                                         .map(String::valueOf)
                                         .reduce(String::concat)
                                         .orElseThrow();

        return realChecksum.equals(checksum) ? Optional.of(sectorId) : Optional.empty();
    }

    private static Comparator<Map.Entry<Character, Integer>> getEntryComparator() {
        return (o1, o2) -> {
            Integer o1Value = o1.getValue();
            Integer o2Value = o2.getValue();
            if (!Objects.equals(o1Value, o2Value)) {
                return o2Value - o1Value;
            }

            return Character.compare(o1.getKey(), o2.getKey());
        };
    }

    private static String caesarCypher(String s, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : s.toCharArray()) {
            if (character != '-') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines()
                     .map(PATTERN::matcher)
                     .filter(Matcher::matches)
                     .map(Day4::getSectorIdIfValid)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .reduce(Integer::sum)
                     .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 278_221;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return reader.lines()
                     .map(PATTERN::matcher)
                     .filter(Matcher::matches)
                     .map(matcher -> new ImmutablePair<>(matcher.group(1), getSectorIdIfValid(matcher)))
                     .filter(pair -> pair.getRight().isPresent())
                     .map(pair -> new ImmutablePair<>(caesarCypher(pair.getLeft(), pair.getRight().get()),
                                                      pair.getRight().get()))
                     .filter(pair -> pair.getLeft().equals("northpole-object-storage"))
                     .map(Pair::getRight)
                     .findFirst()
                     .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 267;
    }
}
