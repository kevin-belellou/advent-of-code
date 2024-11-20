package com.belellou.kevin.advent;

import java.util.List;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.belellou.kevin.advent.generic.DaySolver;
import com.belellou.kevin.advent.year2015.Day1;
import com.belellou.kevin.advent.year2015.Day2;
import com.belellou.kevin.advent.year2015.Day3;
import com.belellou.kevin.advent.year2015.Day4;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySolverTest {

    private static final List<TestCase> TEST_CASES = List.of(new TestCase(new Day1(), 74, 1_795),
                                                             new TestCase(new Day2(), 1_588_178, 3_783_758),
                                                             new TestCase(new Day3(), 2_081, 2_341),
                                                             new TestCase(new Day4(), 346_386, 9_958_218));

    private static final String FIRST_SOLUTION = " - First solution";
    private static final String SECOND_SOLUTION = " - Second solution";

    @TestFactory
    List<DynamicTest> testDaySolvers() {
        return TEST_CASES.stream()
                         .<DynamicTest>mapMulti((testCase, consumer) -> {
                             consumer.accept(dynamicTestOf(testCase, true));
                             consumer.accept(dynamicTestOf(testCase, false));
                         }).toList();
    }

    private static DynamicTest dynamicTestOf(TestCase testCase, boolean firstSolution) {
        return DynamicTest.dynamicTest(testCase.daySolver + (firstSolution ? FIRST_SOLUTION : SECOND_SOLUTION),
                                       firstSolution
                                       ? () -> assertThat(testCase.daySolver.solveFirstStar())
                                               .isEqualTo(testCase.firstSolution)
                                       : () -> assertThat(testCase.daySolver.solveSecondStar())
                                               .isEqualTo(testCase.secondSolution)
        );
    }

    protected record TestCase(DaySolver daySolver, int firstSolution, int secondSolution) {}
}
