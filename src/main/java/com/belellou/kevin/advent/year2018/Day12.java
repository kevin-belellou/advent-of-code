package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day12 extends AbstractDaySolver<Integer, Integer> {

    private static final String ALIVE = "#";
    private static final String DEAD = ".";

    private static final int GENERATIONS = 20;

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        String[] initialState = reader.readLine().split(StringUtils.SPACE)[2].split(StringUtils.EMPTY);

        LinkedList<String> plants = new LinkedList<>(Arrays.asList(initialState));

        List<Note> notes = reader.lines()
                                 .skip(1)
                                 .map(Note::new)
                                 .toList();

        String[] array = plants.toArray(new String[0]);
        LinkedList<String> backup = new LinkedList<>(plants);

        for (int i = 0; i < GENERATIONS; i++) {
            plants.addFirst(DEAD);
            plants.addFirst(DEAD);
            plants.addLast(DEAD);
            plants.addLast(DEAD);

            LinkedList<String> copy = new LinkedList<>(plants);

            for (int j = 2; j < plants.size() - 3; j++) {
                String pattern = String.join(StringUtils.EMPTY, plants.subList(j - 2, j + 3));
                Note note = notes.stream()
                                 .filter(n -> n.pattern().equals(pattern))
                                 .findFirst()
                                 .orElseThrow();
                copy.set(j, note.result());
            }

            plants = copy;
        }

        int sum = 0;
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).equals(ALIVE)) {
                sum += i - GENERATIONS * 2;
            }
        }

        // 2nd try
        for (int i = 0; i < GENERATIONS; i++) {
            backup.addFirst(DEAD);
            backup.addFirst(DEAD);
            backup.addLast(DEAD);
            backup.addLast(DEAD);

            List<Integer> hits = new ArrayList<>(50);

            for (int j = 2; j < backup.size() - 3; j++) {
                String pattern = String.join(StringUtils.EMPTY, backup.subList(j - 2, j + 3));
                int finalJ = j;
                notes.stream()
                     .filter(n -> n.pattern().equals(pattern))
                     .filter(n -> n.result.equals(ALIVE))
                     .findAny()
                     .ifPresent(n -> hits.add(finalJ - 2));

//                if (note.result().equals(ALIVE)) hits.add(j);
            }

            System.out.println(hits);
        }

        return sum;
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 1;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }

    private record Note(String pattern, String result) {

        public Note(String note) {
            this(note.substring(0, 5), note.substring(9));
        }
    }
}
