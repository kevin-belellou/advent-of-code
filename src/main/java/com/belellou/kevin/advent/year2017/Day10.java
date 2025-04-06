package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day10 extends AbstractDaySolver<Integer, String> {

    private static final int LIST_LENGTH = 256;

    static List<Integer> getSparseHash(List<Integer> selectionLengths, int numberOfRounds) {
        List<Integer> numbers = new ArrayList<>(IntStream.range(0, LIST_LENGTH)
                                                         .boxed()
                                                         .toList());
        int currentPosition = 0;
        int skipSize = 0;

        while (numberOfRounds-- > 0) {
            for (Integer selectionLength : selectionLengths) {
                if (selectionLength <= LIST_LENGTH - currentPosition) {
                    List<Integer> reversedSelection = new ArrayList<>(
                            numbers.subList(currentPosition, currentPosition + selectionLength).reversed());

                    for (int i = 0; i < selectionLength; i++) {
                        numbers.set(currentPosition + i, reversedSelection.get(i));
                    }
                } else {
                    int firstPart = LIST_LENGTH - currentPosition;
                    int rest = selectionLength - firstPart;

                    List<Integer> firstPartSelection = numbers.subList(currentPosition, LIST_LENGTH);
                    List<Integer> secondPartSelection = numbers.subList(0, rest);

                    List<Integer> finalSelection = new ArrayList<>(firstPartSelection);
                    finalSelection.addAll(secondPartSelection);
                    List<Integer> reversed = finalSelection.reversed();

                    for (int i = currentPosition; i < LIST_LENGTH; i++) {
                        numbers.set(i, reversed.get(i - currentPosition));
                    }

                    for (int i = 0; i < rest; i++) {
                        numbers.set(i, reversed.get(firstPart + i));
                    }
                }

                currentPosition = (currentPosition + selectionLength + skipSize) % LIST_LENGTH;
                skipSize++;
            }
        }

        return numbers;
    }

    static List<Integer> getAsciiCodes(String line) {
        List<Integer> inputLine = new ArrayList<>(line.chars().boxed().toList());
        inputLine.addAll(List.of(17, 31, 73, 47, 23));
        return inputLine;
    }

    static String getKnotHash(List<Integer> sparseHash) {
        return sparseHash.stream()
                         .gather(Gatherers.windowFixed(16))
                         .map(integers -> integers.stream()
                                                  .reduce((a, b) -> a ^ b)
                                                  .orElseThrow())
                         .map(Integer::toHexString)
                         .map(hex -> hex.length() == 1 ? "0" + hex : hex)
                         .collect(Collectors.joining());
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        List<Integer> selectionLengths = Arrays.stream(reader.readLine().split(","))
                                               .map(Integer::valueOf)
                                               .toList();

        List<Integer> sparseHash = getSparseHash(selectionLengths, 1);

        return sparseHash.get(0) * sparseHash.get(1);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 826;
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        List<Integer> asciiCodes = getAsciiCodes(line);

        List<Integer> sparseHash = getSparseHash(asciiCodes, 64);

        return getKnotHash(sparseHash);
    }

    @Override
    public String getSecondStarSolution() {
        return "d067d3f14d07e09c2e7308c3926605c4";
    }
}
