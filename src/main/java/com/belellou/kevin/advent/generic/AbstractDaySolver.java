package com.belellou.kevin.advent.generic;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDaySolver<FirstSolutionType, SecondSolutionType>
        implements DaySolver<FirstSolutionType, SecondSolutionType> {

    private static final String INPUT_FOLDER = "src/main/resources";
    private static final String INPUT_FILE_NAME = "input-%s.txt";

    private static final String FIRST_SOLUTION = " - First solution: ";
    private static final String SECOND_SOLUTION = " - Second solution: ";

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    private final Year year;
    private final Day day;

    private final Path inputPath;

    protected AbstractDaySolver() {
        this.year = Year.getYear(getNumberFrom(getClass().getPackageName()));
        this.day = Day.getDay(getNumberFrom(getClass().getSimpleName()));

        inputPath = Path.of(INPUT_FOLDER, year.toString(), String.format(INPUT_FILE_NAME, day.toString()));
    }

    private static String getNumberFrom(String input) {
        Matcher matcher = NUMBER_PATTERN.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        return matcher.group(1);
    }

    private BufferedReader getReader() {
        try {
            return Files.newBufferedReader(inputPath);
        } catch (IOException e) {
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
