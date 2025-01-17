package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day15 extends AbstractDaySolver<Integer, Integer> {

    public Day15() {
        super(Day15.class);
    }

    private static Disc createDisc(String line) {
        String[] split = line.split(" ");
        return new Disc(Integer.parseInt(split[3]), Integer.parseInt(split[11].split("\\.")[0]));
    }

    private static int getFirstValidTime(List<Disc> discs) {
        int discCount = discs.size();
        int time = 0;

        while (true) {
            int discOK = 0;

            for (int i = 0; i < discCount; i++) {
                Disc disc = discs.get(i);
                if ((time + disc.initialPosition + i + 1) % disc.positionNumber != 0) {
                    break;
                }

                discOK++;
            }

            if (discOK == discCount) {
                return time;
            }

            time++;
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Disc> discs = reader.lines().map(Day15::createDisc).toList();

        return getFirstValidTime(discs);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 400_589;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Disc> discs = reader.lines().map(Day15::createDisc).toList();
        discs = new ArrayList<>(discs);
        discs.add(new Disc(11, 0));

        return getFirstValidTime(discs);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 3_045_959;
    }

    private record Disc(int positionNumber, int initialPosition) {}
}
