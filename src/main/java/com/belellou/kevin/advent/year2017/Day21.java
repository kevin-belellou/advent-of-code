package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day21 extends AbstractDaySolver<Integer, Integer> {

    private static final char[][] STARTING_PATTERN = Rule.fromPattern(".#./..#/###");

    private static List<Rule> parseRules(BufferedReader reader) {
        return reader.lines()
                     .map(Rule::new)
                     .toList();
    }

    private static char[][] enhanceImage(char[][] image) {
        int size = image.length;

        return null;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Rule> rules = parseRules(reader);

        List<Rule> list = rules.stream()
                               .filter(rule -> rule.matches(STARTING_PATTERN))
                               .toList();

        return 0;
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 1;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }

    private static final class Rule {

        private static int NUMBER = 0;

        private static final Pattern RULE_PATTERN = Pattern.compile("^([.#/]+) => ([.#/]+)$");
        private static final String SEPARATOR = "/";

        public final char[][] outputPattern;

        private final int number;
        private final int inputSize;
        private final char[][][] inputPatterns;

        public Rule(String line) {
            Matcher matcher = RULE_PATTERN.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid rule format: " + line);
            }

            number = NUMBER++;

            String inputPatternString = matcher.group(1);
            inputSize = StringUtils.countMatches(inputPatternString, SEPARATOR) == 1 ? 2 : 3;
            outputPattern = fromPattern(matcher.group(2));

            Set<String> inputPatternStringSet = new HashSet<>();
            inputPatternStringSet.add(inputPatternString);

            inputPatternStringSet.add(verticallyFlipped(inputPatternString));
            inputPatternStringSet.add(horizontallyFlipped(inputPatternString));
            inputPatternStringSet.add(verticallyFlipped(horizontallyFlipped(inputPatternString)));

            inputPatternStringSet.add(rotateRight(inputPatternString));
            inputPatternStringSet.add(rotateRight(horizontallyFlipped(inputPatternString)));
            inputPatternStringSet.add(rotateRight(verticallyFlipped(inputPatternString)));
            inputPatternStringSet.add(rotateRight(verticallyFlipped(horizontallyFlipped(inputPatternString))));

            inputPatterns = inputPatternStringSet.stream()
                                                 .map(Rule::fromPattern)
                                                 .toArray(char[][][]::new);
        }

        public static char[][] fromPattern(String pattern) {
            return switch (StringUtils.countMatches(pattern, '/')) {
                case 1 -> new char[][] {
                        {pattern.charAt(0), pattern.charAt(1)},
                        {pattern.charAt(3), pattern.charAt(4)}
                };
                case 2 -> new char[][] {
                        {pattern.charAt(0), pattern.charAt(1), pattern.charAt(2)},
                        {pattern.charAt(4), pattern.charAt(5), pattern.charAt(6)},
                        {pattern.charAt(8), pattern.charAt(9), pattern.charAt(10)}
                };
                case 3 -> new char[][] {
                        {pattern.charAt(0), pattern.charAt(1), pattern.charAt(2), pattern.charAt(3)},
                        {pattern.charAt(5), pattern.charAt(6), pattern.charAt(7), pattern.charAt(8)},
                        {pattern.charAt(10), pattern.charAt(11), pattern.charAt(12), pattern.charAt(13)},
                        {pattern.charAt(15), pattern.charAt(16), pattern.charAt(17), pattern.charAt(18)}
                };
                default -> throw new IllegalArgumentException("Invalid pattern size: " + pattern);
            };
        }

        private static String verticallyFlipped(String pattern) {
            return transform(pattern,
                             split -> join(reverse(split[1]), reverse(split[0])),
                             split -> join(reverse(split[2]), reverse(split[1]), reverse(split[0])));
        }

        private static String horizontallyFlipped(String pattern) {
            return transform(pattern,
                             split -> join(split[1], split[0]),
                             split -> join(split[2], split[1], split[0]));
        }

        private static String rotateRight(String pattern) {
            return transform(pattern,
                             split -> {
                                 String part1 = String.valueOf(new char[] {split[1].charAt(0), split[0].charAt(0)});
                                 String part2 = String.valueOf(new char[] {split[1].charAt(1), split[0].charAt(1)});
                                 return join(part1, part2);
                             },
                             split -> {
                                 String part1 = String.valueOf(
                                         new char[] {split[2].charAt(0), split[1].charAt(0), split[0].charAt(0)});
                                 String part2 = String.valueOf(
                                         new char[] {split[2].charAt(1), split[1].charAt(1), split[0].charAt(1)});
                                 String part3 = String.valueOf(
                                         new char[] {split[2].charAt(2), split[1].charAt(2), split[0].charAt(2)});
                                 return join(part1, part2, part3);
                             });
        }

        private static String transform(String pattern, Function<String[], String> case2,
                                        Function<String[], String> case3) {
            String[] split = pattern.split(SEPARATOR);

            return switch (split.length) {
                case 2 -> case2.apply(split);
                case 3 -> case3.apply(split);
                default -> throw new IllegalArgumentException("Invalid pattern size: " + pattern);
            };
        }

//        private static String rotateLeft(String pattern) {
//            return transform(pattern,
//                             split -> join(),
//                             split -> join());
//        }

        private static String reverse(String pattern) {
            return StringUtils.reverse(pattern);
        }

        private static String join(String... patterns) {
            return String.join(SEPARATOR, patterns);
        }

        public boolean matches(char[][] inputPattern) {
            if (inputPattern.length != this.inputSize) {
                return false;
            }

            return Arrays.stream(inputPatterns).anyMatch(pattern -> Arrays.deepEquals(pattern, inputPattern));
        }
    }
}
