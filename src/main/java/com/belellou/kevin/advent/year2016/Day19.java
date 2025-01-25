package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day19 extends AbstractDaySolver<Integer, Integer> {

    public Day19() {
        super(Day19.class);
    }

    private static Optional<Integer> findVictim1(Map<Integer, Boolean> map, int currentElfIndex,
                                                 int numberOfElvesLeft) {
        int nextIndex = currentElfIndex;

        while (true) {
            nextIndex++;

            if (nextIndex == map.size()) {
                nextIndex = 0;
            } else if (nextIndex == currentElfIndex) {
                return Optional.empty();
            }

            if (map.get(nextIndex)) {
                return Optional.of(nextIndex);
            }
        }
    }

    private static Optional<Integer> findVictim2(Map<Integer, Boolean> map, int currentElfIndex,
                                                 int numberOfElvesLeft) {
        int steps = numberOfElvesLeft / 2;
        int nextIndex = currentElfIndex;
        int stepsMade = 0;

        do {
            nextIndex++;

            if (nextIndex == map.size()) {
                nextIndex = 0;
            } else if (nextIndex == currentElfIndex) {
                return Optional.empty();
            }

            if (map.get(nextIndex)) {
                stepsMade++;
            }
        } while (stepsMade < steps);

        return Optional.of(nextIndex);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        int numberOfElves = Integer.parseInt(reader.readLine());

        Map<Integer, Boolean> map = new HashMap<>(numberOfElves);
        IntStream.range(0, numberOfElves).forEach(i -> map.put(i, Boolean.TRUE));

        int elvesLeft = numberOfElves;

        do {
            for (int i = 0; i < numberOfElves; i++) {
                Boolean hasPresents = map.get(i);
                if (hasPresents) {
                    Optional<Integer> victim = findVictim1(map, i, elvesLeft);

                    if (victim.isPresent()) {
                        map.put(victim.get(), Boolean.FALSE);
                        elvesLeft--;
                    }
                }
            }
        } while (elvesLeft != 1);

        return map.entrySet().stream().filter(Entry::getValue).findFirst().orElseThrow().getKey() + 1;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_834_903;
    }

//    @Override
//    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
//        int numberOfElves = Integer.parseInt(reader.readLine());

    /// /        int numberOfElves = 5;
//
//        Map<Integer, Boolean> map = new HashMap<>(numberOfElves);
//        IntStream.range(0, numberOfElves).forEach(i -> map.put(i, Boolean.TRUE));
//
//        int elvesLeft = numberOfElves;
//
//        do {
//            for (int i = 0; i < numberOfElves; i++) {
//                Boolean hasPresents = map.get(i);
//                if (hasPresents) {
//                    Optional<Integer> victim = findVictim2(map, i, elvesLeft);
//
//                    if (victim.isPresent()) {
//                        map.put(victim.get(), Boolean.FALSE);
//                        elvesLeft--;
//
//                        if (elvesLeft == 1) {
//                            break;
//                        }
//                    }
//                }
//            }
//        } while (elvesLeft != 1);
//
//        return map.entrySet().stream().filter(Entry::getValue).findFirst().orElseThrow().getKey() + 1;
//    }
    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        int numberOfElves = Integer.parseInt(reader.readLine());
//        int numberOfElves = 5;

        List<Integer> list = new ArrayList<>(IntStream.rangeClosed(1, numberOfElves).boxed().toList());
        LinkedHashSet<Integer> set = new LinkedHashSet<>(list);

        int index = 0;

        while (true) {
//            int steps = list.size() / 2;
//
//            list.remove((index + steps) % list.size());
//            if (list.size() == 1) {
//                return list.getFirst();
//            }
//
//            index++;
//            if (index > list.size()) {
//                index = 0;
//            }

            int steps = set.size() / 2;

            set.remove((index + steps) % set.size());
            if (set.size() == 1) {
                return set.getFirst();
            }

            index++;
            if (index > set.size()) {
                index = 0;
            }
        }
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }
}
