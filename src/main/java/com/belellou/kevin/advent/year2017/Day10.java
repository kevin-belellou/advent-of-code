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

    private static List<Integer> getNumbers() {
        return new ArrayList<>(IntStream.range(0, LIST_LENGTH)
                                        .boxed()
                                        .toList());
    }

    private static void doRounds(List<Integer> selectionLengths, List<Integer> numbers, int numberOfRounds) {
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

                    ArrayList<Integer> finalSelection = new ArrayList<>(firstPartSelection);
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
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        List<Integer> selectionLengths = Arrays.stream(reader.readLine().split(","))
                                               .map(Integer::valueOf)
                                               .toList();

        List<Integer> numbers = getNumbers();

        doRounds(selectionLengths, numbers, 1);

        return numbers.get(0) * numbers.get(1);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 826;
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) throws IOException {
        String line = reader.readLine();

        List<Integer> inputLine = new ArrayList<>(line.chars().boxed().toList());
        inputLine.addAll(List.of(17, 31, 73, 47, 23));

        List<Integer> numbers = getNumbers();

        doRounds(inputLine, numbers, 64);

        return numbers.stream()
                      .gather(Gatherers.windowFixed(16))
                      .map(integers -> integers.stream()
                                               .reduce((a, b) -> a ^ b)
                                               .orElseThrow())
                      .map(Integer::toHexString)
                      .map(hex -> hex.length() == 1 ? "0" + hex : hex)
                      .collect(Collectors.joining());
    }

    @Override
    public String getSecondStarSolution() {
        return "d067d3f14d07e09c2e7308c3926605c4";
    }
}
