package com.belellou.kevin.advent.generic;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract base class for solving Advent of Code daily puzzles.
 * <p>
 * This class provides common functionality for reading puzzle inputs and executing solutions
 * for both parts (stars) of a daily puzzle. It automatically derives the year and day from
 * the implementing class's package name and class name respectively, and locates the
 * corresponding input file.
 * </p>
 *
 * @param <FirstSolutionType>  the type of the solution for the first star
 * @param <SecondSolutionType> the type of the solution for the second star
 */
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

    /**
     * Constructs a new AbstractDaySolver instance.
     * <p>
     * This constructor automatically extracts the year from the implementing class's package name
     * and the day from the class name. It then constructs the path to the corresponding input file
     * based on these values.
     * </p>
     *
     * @throws IllegalArgumentException if the package name does not contain a year number,
     *                                  or the class name does not contain a day number
     */
    protected AbstractDaySolver() {
        this.year = Year.getYear(getNumberFrom(getClass().getPackageName()));
        this.day = Day.getDay(getNumberFrom(getClass().getSimpleName()));

        inputPath = Path.of(INPUT_FOLDER, year.toString(), String.format(INPUT_FILE_NAME, day.toString()));
    }

    /**
     * Extracts the first numeric value from the given input string.
     *
     * @param input the string to extract a number from
     *
     * @return the first numeric value found in the input
     *
     * @throws IllegalArgumentException if no numeric value is found
     */
    private static String getNumberFrom(String input) {
        Matcher matcher = NUMBER_PATTERN.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        return matcher.group(1);
    }

    /**
     * Creates a buffered reader for the input file.
     *
     * @return a buffered reader for reading the puzzle input
     *
     * @throws RuntimeException if the input file cannot be read
     */
    private BufferedReader getReader() {
        try {
            return Files.newBufferedReader(inputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Solves the first star of the puzzle.
     * <p>
     * This method reads the input file, delegates to the abstract doSolveFirstStar method,
     * prints the solution to standard output, and returns the result.
     * </p>
     *
     * @return the solution for the first star
     *
     * @throws RuntimeException if an I/O error occurs while reading the input
     */
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

    /**
     * Solves the second star of the puzzle.
     * <p>
     * This method reads the input file, delegates to the abstract doSolveSecondStar method,
     * prints the solution to standard output, and returns the result.
     * </p>
     *
     * @return the solution for the second star
     *
     * @throws RuntimeException if an I/O error occurs while reading the input
     */
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

    /**
     * Implements the solution logic for the first star.
     * <p>
     * Subclasses must implement this method to provide the specific solution logic
     * for the first part of the puzzle.
     * </p>
     *
     * @param reader a buffered reader for reading the puzzle input
     *
     * @return the solution for the first star
     *
     * @throws IOException if an I/O error occurs while reading the input
     */
    protected abstract FirstSolutionType doSolveFirstStar(BufferedReader reader) throws IOException;

    /**
     * Implements the solution logic for the second star.
     * <p>
     * Subclasses must implement this method to provide the specific solution logic
     * for the second part of the puzzle.
     * </p>
     *
     * @param reader a buffered reader for reading the puzzle input
     *
     * @return the solution for the second star
     *
     * @throws IOException if an I/O error occurs while reading the input
     */
    protected abstract SecondSolutionType doSolveSecondStar(BufferedReader reader) throws IOException;

    /**
     * Returns a string representation of this solver.
     *
     * @return a string in the format "Day DD of YYYY"
     */
    @Override
    public String toString() {
        return "Day " + day + " of " + year;
    }
}
