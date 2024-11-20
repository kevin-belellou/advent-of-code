package com.belellou.kevin.advent.year2015;

import org.junit.jupiter.api.Test;

import com.belellou.kevin.advent.generic.DaySolver;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test {

    private static final DaySolver DAY_1 = new Day1();

    @Test
    public void testSolveFirstStar() {
        assertThat(DAY_1.solveFirstStar()).isEqualTo(74);
    }

    @Test
    public void testSolveSecondStar() {
        assertThat(DAY_1.solveSecondStar()).isEqualTo(1795);
    }
}
