package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Map;

import com.google.gson.Gson;
import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day12 extends AbstractDaySolver<Integer, Integer> {

    private static final String RED = "red";

    private static void readMap(Map<?, ?> map, ModifiableInteger count, boolean stopForRed) {
        if (stopForRed && map.containsValue(RED)) {
            return;
        }

        readCollection(map.keySet(), count, stopForRed);
        readCollection(map.values(), count, stopForRed);
    }

    private static void readCollection(Collection<?> collection, ModifiableInteger count, boolean stopForRed) {
        for (Object o : collection) {
            if (o instanceof Collection<?> objects) {
                readCollection(objects, count, stopForRed);
            } else if (o instanceof Map<?, ?> map) {
                readMap(map, count, stopForRed);
            } else {
                try {
                    count.setValue((int) (count.getValue() + Double.parseDouble(o.toString())));
                } catch (NumberFormatException e) {
                    // Do nothing
                }
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        Map<?, ?> map = new Gson().fromJson(line, Map.class);
        ModifiableInteger count = new ModifiableInteger(0);

        readMap(map, count, false);

        return count.getValue();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 111_754;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        String line = reader.lines().findFirst().orElseThrow();

        Map<?, ?> map = new Gson().fromJson(line, Map.class);
        ModifiableInteger count = new ModifiableInteger(0);

        readMap(map, count, true);

        return count.getValue();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 65_402;
    }
}
