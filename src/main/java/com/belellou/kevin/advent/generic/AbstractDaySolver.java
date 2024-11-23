package com.belellou.kevin.advent.generic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractDaySolver<T> implements DaySolver<T> {

    private static final String SEPARATOR = "/";
    private static final String INPUT_FOLDER = "src/main/resources/";
    private static final String INPUT_FILE_NAME = "/input.txt";

    private static final String FIRST_SOLUTION = " - First solution: ";
    private static final String SECOND_SOLUTION = " - Second solution: ";

    private final Year year;
    private final Day day;

    private final String input;

    protected AbstractDaySolver(Year year, Day day) {
        this.year = year;
        this.day = day;

        input = INPUT_FOLDER + year.toString() + SEPARATOR + day.toString() + INPUT_FILE_NAME;
    }

    private BufferedReader getReader() {
        try {
            FileReader fileReader = new FileReader(input);
            return new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T solveFirstStar() {
        try (BufferedReader reader = getReader()) {
            T firstSolution = doSolveFirstStar(reader);

            System.out.println(this + FIRST_SOLUTION + firstSolution);

            return firstSolution;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T solveSecondStar() {
        try (BufferedReader reader = getReader()) {
            T secondSolution = doSolveSecondStar(reader);

            System.out.println(this + SECOND_SOLUTION + secondSolution);

            return secondSolution;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T doSolveFirstStar(BufferedReader reader) throws IOException;

    protected abstract T doSolveSecondStar(BufferedReader reader) throws IOException;

    @Override
    public String toString() {
        return "Day " + day + " of " + year;
    }
}
