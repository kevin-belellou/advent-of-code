package com.belellou.kevin.advent.generic;

public interface DaySolver<FirstSolutionType, SecondSolutionType> {

    FirstSolutionType solveFirstStar();

    FirstSolutionType getFirstStarSolution();

    SecondSolutionType solveSecondStar();

    SecondSolutionType getSecondStarSolution();
}
