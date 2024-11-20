package com.belellou.kevin.advent.year2015;

import org.junit.jupiter.api.Test;

import com.belellou.kevin.advent.generic.DaySolver;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {

    private static final DaySolver DAY_2 = new Day2();

    @Test
    public void testSolveFirstStar() {
        assertThat(DAY_2.solveFirstStar()).isEqualTo(1_588_178);
    }

    @Test
    public void testSolveSecondStar() {
        assertThat(DAY_2.solveSecondStar()).isEqualTo(3_783_758);
    }
}
