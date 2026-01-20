package com.belellou.kevin.advent.generic;

/**
 * Interface for solving Advent of Code daily puzzles.
 * Each day typically has two star challenges with potentially different solution types.
 *
 * @param <FirstSolutionType>  the type of the solution for the first star challenge
 * @param <SecondSolutionType> the type of the solution for the second star challenge
 */
public interface DaySolver<FirstSolutionType, SecondSolutionType> {

    /**
     * Solves the first star challenge for this day.
     *
     * @return the solution to the first star challenge
     */
    FirstSolutionType solveFirstStar();

    /**
     * Returns the expected solution for the first star challenge.
     * This is typically used for verification purposes.
     *
     * @return the expected solution to the first star challenge
     */
    FirstSolutionType getFirstStarSolution();

    /**
     * Solves the second star challenge for this day.
     *
     * @return the solution to the second star challenge
     */
    SecondSolutionType solveSecondStar();

    /**
     * Returns the expected solution for the second star challenge.
     * This is typically used for verification purposes.
     *
     * @return the expected solution to the second star challenge
     */
    SecondSolutionType getSecondStarSolution();
}
