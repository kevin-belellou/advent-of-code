package com.belellou.kevin.advent.generic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractDaySolver implements DaySolver {

    private static final String SEPARATOR = "/";
    private static final String INPUT_FOLDER = "src/main/resources/";
    private static final String INPUT_FILE_NAME = "/input.txt";

    private final String input;

    protected AbstractDaySolver(Year year, Day day) {
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
    public int solveFirstStar() {
        try (BufferedReader reader = getReader()) {
            return doSolveFirstStar(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int solveSecondStar() {
        try (BufferedReader reader = getReader()) {
            return doSolveSecondStar(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract int doSolveFirstStar(BufferedReader reader) throws IOException;

    protected abstract int doSolveSecondStar(BufferedReader reader) throws IOException;
}
