package com.belellou.kevin.advent.generic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDaySolver<FirstSolutionType, SecondSolutionType>
        implements DaySolver<FirstSolutionType, SecondSolutionType> {

    private static final String SEPARATOR = "/";
    private static final String INPUT_FOLDER = "src/main/resources/";
    private static final String INPUT_FILE_NAME = "/input.txt";

    private static final String FIRST_SOLUTION = " - First solution: ";
    private static final String SECOND_SOLUTION = " - Second solution: ";
    private static final String REGEX_NUMBER = "(\\d+)";

    private final Year year;
    private final Day day;

    private final String input;

    protected AbstractDaySolver(Class<? extends AbstractDaySolver<FirstSolutionType, SecondSolutionType>> clazz) {
        this.year = Year.getYear(getNumberFrom(clazz.getPackageName()));
        this.day = Day.getDay(getNumberFrom(clazz.getSimpleName()));

        input = INPUT_FOLDER + year.toString() + SEPARATOR + day.toString() + INPUT_FILE_NAME;
    }

    private static String getNumberFrom(String input) {
        Matcher matcher = Pattern.compile(REGEX_NUMBER).matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        return matcher.group(1);
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
    public FirstSolutionType solveFirstStar() {
        try (BufferedReader reader = getReader()) {
            FirstSolutionType firstSolution = doSolveFirstStar(reader);

            System.out.println(this + FIRST_SOLUTION + firstSolution);

            return firstSolution;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SecondSolutionType solveSecondStar() {
        try (BufferedReader reader = getReader()) {
            SecondSolutionType secondSolution = doSolveSecondStar(reader);

            System.out.println(this + SECOND_SOLUTION + secondSolution);

            return secondSolution;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract FirstSolutionType doSolveFirstStar(BufferedReader reader) throws IOException;

    protected abstract SecondSolutionType doSolveSecondStar(BufferedReader reader) throws IOException;

    @Override
    public String toString() {
        return "Day " + day + " of " + year;
    }
}
