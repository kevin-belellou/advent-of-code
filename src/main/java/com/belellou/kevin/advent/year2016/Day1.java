package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day1 extends AbstractDaySolver<Integer> {

    public Day1() {
        super(Day1.class);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return 0;
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
}
