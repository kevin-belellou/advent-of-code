package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day16 extends AbstractDaySolver<Integer, Integer> {

    private static final String GROUP_SUE = "Sue (\\d+)";
    private static final String GROUP_CHILDREN = "children: (\\d+)";
    private static final String GROUP_CATS = "cats: (\\d+)";
    private static final String GROUP_SAMOYEDS = "samoyeds: (\\d+)";
    private static final String GROUP_POMERANIANS = "pomeranians: (\\d+)";
    private static final String GROUP_AKITAS = "akitas: (\\d+)";
    private static final String GROUP_VIZSLAS = "vizslas: (\\d+)";
    private static final String GROUP_GOLDFISH = "goldfish: (\\d+)";
    private static final String GROUP_TREES = "trees: (\\d+)";
    private static final String GROUP_CARS = "cars: (\\d+)";
    private static final String GROUP_PERFUMES = "perfumes: (\\d+)";

    private static final Map<String, Pattern> PATTERN_MAP = new HashMap<>();
    private static final Map<String, Compound> COMPOUND_MAP = new HashMap<>();
    private static final Sue REAL_SUE;

    static {
        COMPOUND_MAP.put(GROUP_CHILDREN, new Compound(GROUP_CHILDREN, Range.EXACT));
        COMPOUND_MAP.put(GROUP_CATS, new Compound(GROUP_CATS, Range.GREATER_THAN));
        COMPOUND_MAP.put(GROUP_SAMOYEDS, new Compound(GROUP_SAMOYEDS, Range.EXACT));
        COMPOUND_MAP.put(GROUP_POMERANIANS, new Compound(GROUP_POMERANIANS, Range.FEWER_THAN));
        COMPOUND_MAP.put(GROUP_AKITAS, new Compound(GROUP_AKITAS, Range.EXACT));
        COMPOUND_MAP.put(GROUP_VIZSLAS, new Compound(GROUP_VIZSLAS, Range.EXACT));
        COMPOUND_MAP.put(GROUP_GOLDFISH, new Compound(GROUP_GOLDFISH, Range.FEWER_THAN));
        COMPOUND_MAP.put(GROUP_TREES, new Compound(GROUP_TREES, Range.GREATER_THAN));
        COMPOUND_MAP.put(GROUP_CARS, new Compound(GROUP_CARS, Range.EXACT));
        COMPOUND_MAP.put(GROUP_PERFUMES, new Compound(GROUP_PERFUMES, Range.EXACT));

        PATTERN_MAP.put(GROUP_SUE, Pattern.compile(GROUP_SUE));
        PATTERN_MAP.put(GROUP_CHILDREN, Pattern.compile(GROUP_CHILDREN));
        PATTERN_MAP.put(GROUP_CATS, Pattern.compile(GROUP_CATS));
        PATTERN_MAP.put(GROUP_SAMOYEDS, Pattern.compile(GROUP_SAMOYEDS));
        PATTERN_MAP.put(GROUP_POMERANIANS, Pattern.compile(GROUP_POMERANIANS));
        PATTERN_MAP.put(GROUP_AKITAS, Pattern.compile(GROUP_AKITAS));
        PATTERN_MAP.put(GROUP_VIZSLAS, Pattern.compile(GROUP_VIZSLAS));
        PATTERN_MAP.put(GROUP_GOLDFISH, Pattern.compile(GROUP_GOLDFISH));
        PATTERN_MAP.put(GROUP_TREES, Pattern.compile(GROUP_TREES));
        PATTERN_MAP.put(GROUP_CARS, Pattern.compile(GROUP_CARS));
        PATTERN_MAP.put(GROUP_PERFUMES, Pattern.compile(GROUP_PERFUMES));

        REAL_SUE = Sue.from(0, 3, 7, 2, 3, 0, 0, 5, 3, 2, 1);
    }

    public Day16() {
        super(Day16.class);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines()
                     .map(Sue::from)
                     .filter(sue -> sue.isCloseOf(false))
                     .findFirst()
                     .orElseThrow().number;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 103;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return reader.lines()
                     .map(Sue::from)
                     .filter(sue -> sue.isCloseOf(true))
                     .findFirst()
                     .orElseThrow().number;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 405;
    }

    private enum Range {
        EXACT,
        FEWER_THAN,
        GREATER_THAN
    }

    private record Sue(int number, Map<Compound, Integer> map) {

        private static Sue from(String line) {
            return from(getNumber(line), getChildren(line), getCats(line), getSamoyeds(line), getPomeranians(line),
                        getAkitas(line), getVizslas(line), getGoldfish(line), getTrees(line), getCars(line),
                        getPerfumes(line));
        }

        @SuppressWarnings("DuplicatedCode")
        private static Sue from(Integer number, Integer children, Integer cats, Integer samoyeds, Integer pomeranians,
                                Integer akitas, Integer vizslas, Integer goldfish, Integer trees, Integer cars,
                                Integer perfumes) {
            Map<Compound, Integer> map = new HashMap<>();
            map.put(COMPOUND_MAP.get(GROUP_CHILDREN), children);
            map.put(COMPOUND_MAP.get(GROUP_CATS), cats);
            map.put(COMPOUND_MAP.get(GROUP_SAMOYEDS), samoyeds);
            map.put(COMPOUND_MAP.get(GROUP_POMERANIANS), pomeranians);
            map.put(COMPOUND_MAP.get(GROUP_AKITAS), akitas);
            map.put(COMPOUND_MAP.get(GROUP_VIZSLAS), vizslas);
            map.put(COMPOUND_MAP.get(GROUP_GOLDFISH), goldfish);
            map.put(COMPOUND_MAP.get(GROUP_TREES), trees);
            map.put(COMPOUND_MAP.get(GROUP_CARS), cars);
            map.put(COMPOUND_MAP.get(GROUP_PERFUMES), perfumes);

            return new Sue(number, map);
        }

        private static Integer getNumber(String line) {
            return getInteger(GROUP_SUE, line);
        }

        private static Integer getChildren(String line) {
            return getInteger(GROUP_CHILDREN, line);
        }

        private static Integer getCats(String line) {
            return getInteger(GROUP_CATS, line);
        }

        private static Integer getSamoyeds(String line) {
            return getInteger(GROUP_SAMOYEDS, line);
        }

        private static Integer getPomeranians(String line) {
            return getInteger(GROUP_POMERANIANS, line);
        }

        private static Integer getAkitas(String line) {
            return getInteger(GROUP_AKITAS, line);
        }

        private static Integer getVizslas(String line) {
            return getInteger(GROUP_VIZSLAS, line);
        }

        private static Integer getGoldfish(String line) {
            return getInteger(GROUP_GOLDFISH, line);
        }

        private static Integer getTrees(String line) {
            return getInteger(GROUP_TREES, line);
        }

        private static Integer getCars(String line) {
            return getInteger(GROUP_CARS, line);
        }

        private static Integer getPerfumes(String line) {
            return getInteger(GROUP_PERFUMES, line);
        }

        private static Integer getInteger(String groupName, String line) {
            Matcher matcher = PATTERN_MAP.get(groupName).matcher(line);
            if (!matcher.find()) {
                return null;
            }

            String match = matcher.group(1);
            return match == null ? null : Integer.valueOf(match);
        }

        private boolean isCloseOf(boolean respectRange) {
            for (String group : PATTERN_MAP.keySet()) {
                if (group.equals(GROUP_SUE)) {
                    continue;
                }

                if (!check(group, respectRange)) {
                    return false;
                }
            }

            return true;
        }

        private boolean check(String groupName, boolean respectRange) {
            Compound compound = COMPOUND_MAP.get(groupName);
            Integer value = map.get(compound);

            if (respectRange && compound.range != Range.EXACT) {
                if (compound.range == Range.FEWER_THAN) {
                    return value == null || value.compareTo(REAL_SUE.map.get(compound)) < 0;
                } else {
                    return value == null || value.compareTo(REAL_SUE.map.get(compound)) > 0;
                }
            } else {
                return value == null || value.equals(REAL_SUE.map.get(compound));
            }
        }
    }

    private record Compound(String name, Range range) {}
}
