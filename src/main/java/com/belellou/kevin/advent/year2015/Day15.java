package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ejml.data.DMatrixIterator;
import org.ejml.simple.SimpleMatrix;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day15 extends AbstractDaySolver<Integer> {

    private static final String GROUP_NAME = "name";
    private static final String GROUP_CAPACITY = "capacity";
    private static final String GROUP_DURABILITY = "durability";
    private static final String GROUP_FLAVOR = "flavor";
    private static final String GROUP_TEXTURE = "texture";
    private static final String GROUP_CALORIES = "calories";

    private static final Pattern PATTERN = Pattern.compile("^" +
                                                                   "(?<" + GROUP_NAME + ">\\w+)" +
                                                                   ": capacity " +
                                                                   "(?<" + GROUP_CAPACITY + ">-?\\d+)" +
                                                                   ", durability " +
                                                                   "(?<" + GROUP_DURABILITY + ">-?\\d+)" +
                                                                   ", flavor " +
                                                                   "(?<" + GROUP_FLAVOR + ">-?\\d+)" +
                                                                   ", texture " +
                                                                   "(?<" + GROUP_TEXTURE + ">-?\\d+)" +
                                                                   ", calories " +
                                                                   "(?<" + GROUP_CALORIES + ">-?\\d+)" +
                                                                   "$");

    public Day15() {
        super(Day15.class);
    }

    private static Ingredient getIngredient(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }

        return new Ingredient(matcher);
    }

    private static SimpleMatrix getIngredientsMatrix(Collection<Ingredient> ingredients,
                                                     Function<Ingredient, double[]> toValues) {
        double[][] data = ingredients.stream()
                                     .map(toValues)
                                     .toArray(double[][]::new);

        return new SimpleMatrix(data);
    }

    private static List<double[][]> getRatioData() {
        List<double[][]> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    list.add(new double[][] {{i, j, k, 100 - i - j - k}});
                }
            }
        }

        return list;
    }

    private static double getScore(SimpleMatrix matrix) {
        return getScore(matrix, false);
    }

    private static double getScoreWithoutCalories(SimpleMatrix matrix) {
        return getScore(matrix, true);
    }

    private static double getScore(SimpleMatrix matrix, boolean ignoreLastColum) {
        double score = 0.0;

        DMatrixIterator iterator = matrix.iterator(true, 0, 0, matrix.getNumRows() - 1,
                                                   ignoreLastColum ? matrix.getNumCols() - 2 : matrix.getNumCols() - 1);
        while (iterator.hasNext()) {
            double next = iterator.next();

            if (next <= 0.0) {
                score = 0.0;
                break;
            }

            score = score == 0.0 ? next : score * next;
        }
        return score;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Ingredient> list = reader.lines()
                                      .map(Day15::getIngredient)
                                      .toList();

        SimpleMatrix ingredientsMatrix = getIngredientsMatrix(list, Ingredient::getPartialValues);

        List<double[][]> ratioData = getRatioData();

        return (int) getRatioData().stream()
                                   .map(SimpleMatrix::new)
                                   .map(ratioMatrix -> ratioMatrix.mult(ingredientsMatrix))
                                   .mapToDouble(Day15::getScore)
                                   .max()
                                   .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 18_965_440;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Ingredient> list = reader.lines()
                                      .map(Day15::getIngredient)
                                      .toList();

        SimpleMatrix ingredientsMatrix = getIngredientsMatrix(list, Ingredient::getFullValues);

        List<double[][]> ratioData = getRatioData();

        return (int) getRatioData().stream()
                                   .map(SimpleMatrix::new)
                                   .map(ratioMatrix -> ratioMatrix.mult(ingredientsMatrix))
                                   .filter(matrix -> matrix.get(0, matrix.getNumCols() - 1) == 500)
                                   .mapToDouble(Day15::getScoreWithoutCalories)
                                   .max()
                                   .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 15_862_900;
    }

    private record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {

        private Ingredient(Matcher matcher) {
            this(getName(matcher), getCapacity(matcher), getDurability(matcher), getFlavor(matcher),
                 getTexture(matcher), getCalories(matcher));
        }

        private static String getName(Matcher matcher) {
            return matcher.group(GROUP_NAME);
        }

        private static int getCapacity(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_CAPACITY));
        }

        private static int getDurability(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_DURABILITY));
        }

        private static int getFlavor(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_FLAVOR));
        }

        private static int getTexture(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_TEXTURE));
        }

        private static int getCalories(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_CALORIES));
        }

        public double[] getPartialValues() {
            return new double[] {capacity, durability, flavor, texture};
        }

        public double[] getFullValues() {
            return new double[] {capacity, durability, flavor, texture, calories};
        }
    }
}
