package com.belellou.kevin.advent.generic;

import java.io.BufferedReader;

@SuppressWarnings("unused")
public class Day0 extends AbstractDaySolver<Integer> {

    public Day0() {
        super(Year.YEAR_0, Day.DAY_0);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 0;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 0;
    }
}
