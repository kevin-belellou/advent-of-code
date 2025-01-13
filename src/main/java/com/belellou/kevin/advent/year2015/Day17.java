package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day17 extends AbstractDaySolver<Integer, Integer> {

    private static final int EGGNOG = 150;

    public Day17() {
        super(Day17.class);
    }

    private static void countCombinations(List<Integer> containers, int eggnogLeft, ModifiableInteger count,
                                          List<ModifiableInteger> chains) {
        if (chains.isEmpty()) {
            chains.add(new ModifiableInteger(0));
        }
        chains.get(count.getValue()).increment();

        for (int i = 0; i < containers.size(); i++) {
            int nextContainer = containers.get(i);

            if (nextContainer < eggnogLeft) {
                countCombinations(containers.subList(i + 1, containers.size()), eggnogLeft - nextContainer, count,
                                  chains);
            } else if (nextContainer == eggnogLeft) {
                int currentChain = chains.get(count.getValue()).getValue();
                count.increment();
                chains.add(new ModifiableInteger(currentChain));
            }
        }

        chains.get(count.getValue()).decrement();
    }

    private static void doResolve(BufferedReader reader, ModifiableInteger count, List<ModifiableInteger> chains) {
        List<Integer> containers = reader.lines()
                                         .map(Integer::valueOf)
                                         .toList();

        countCombinations(containers, EGGNOG, count, chains);
        chains.removeLast();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        ModifiableInteger count = new ModifiableInteger(0);
        doResolve(reader, count, new ArrayList<>());

        return count.getValue();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_304;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<ModifiableInteger> chains = new ArrayList<>();
        doResolve(reader, new ModifiableInteger(0), chains);

        return chains.stream()
                     .collect(Collectors.groupingBy(ModifiableInteger::getValue, TreeMap::new,
                                                    Collectors.toList()))
                     .firstEntry()
                     .getValue()
                     .size();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 18;
    }
}
