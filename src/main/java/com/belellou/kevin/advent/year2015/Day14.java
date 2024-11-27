package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day14 extends AbstractDaySolver<Integer> {

    private static final String GROUP_REINDEER = "reindeer";
    private static final String GROUP_SPEED = "speed";
    private static final String GROUP_SPEED_DURATION = "speedDuration";
    private static final String GROUP_REST_DURATION = "restDuration";

    private static final Pattern PATTERN = Pattern.compile("^" +
                                                                   "(?<" + GROUP_REINDEER + ">\\w+)" +
                                                                   " can fly " +
                                                                   "(?<" + GROUP_SPEED + ">\\d+)" +
                                                                   " km/s for " +
                                                                   "(?<" + GROUP_SPEED_DURATION + ">\\d+)" +
                                                                   " seconds, but then must rest for " +
                                                                   "(?<" + GROUP_REST_DURATION + ">\\d+)" +
                                                                   " seconds." +
                                                                   "$");

    private static final int DURATION = 2503;

    public Day14() {
        super(Day14.class);
    }

    private static Reindeer getReindeer(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }

        return new Reindeer(matcher);
    }

    private static int getMaxDistance(Reindeer reindeer, int duration) {
        int loopCount = duration / reindeer.loopDuration;
        int modulo = duration % reindeer.loopDuration;

        if (modulo >= reindeer.speedDuration) {
            return ++loopCount * reindeer.speed * reindeer.speedDuration;
        } else {
            return loopCount * reindeer.speed * reindeer.speedDuration + modulo * reindeer.speed;
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        return reader.lines()
                     .map(Day14::getReindeer)
                     .mapToInt(reindeer -> getMaxDistance(reindeer, DURATION))
                     .max()
                     .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 2_640;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Reindeer> reindeerList = reader.lines().map(Day14::getReindeer).toList();

        Map<Reindeer, ModifiableInteger> scores = new HashMap<>(reindeerList.size());
        for (Reindeer reindeer : reindeerList) {
            scores.put(reindeer, new ModifiableInteger(0));
        }

        for (int i = 1; i <= DURATION; i++) {
            int finalI = i;
            List<Map.Entry<Reindeer, Integer>> list = reindeerList.stream()
                                                                  .map(reindeer -> Map.entry(reindeer,
                                                                                             getMaxDistance(reindeer,
                                                                                                            finalI)))
                                                                  .toList();

            Integer maxDistance = list.stream()
                                      .max(Comparator.comparingInt(Map.Entry::getValue))
                                      .orElseThrow()
                                      .getValue();

            list.stream()
                .filter(entry -> Objects.equals(entry.getValue(), maxDistance))
                .forEach(entry -> scores.get(entry.getKey()).increment());
        }

        return scores.values()
                     .stream()
                     .mapToInt(ModifiableInteger::getValue)
                     .max()
                     .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_102;
    }

    private record Reindeer(String name, int speed, int speedDuration, int restDuration, int loopDuration) {

        private Reindeer(Matcher matcher) {
            this(getName(matcher), getSpeed(matcher), getSpeedDuration(matcher), getRestDuration(matcher));
        }

        private Reindeer(String name, int speed, int speedDuration, int restDuration) {
            this(name, speed, speedDuration, restDuration, speedDuration + restDuration);
        }

        private static String getName(Matcher matcher) {
            return matcher.group(GROUP_REINDEER);
        }

        private static int getSpeed(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_SPEED));
        }

        private static int getSpeedDuration(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_SPEED_DURATION));
        }

        private static int getRestDuration(Matcher matcher) {
            return Integer.parseInt(matcher.group(GROUP_REST_DURATION));
        }
    }
}
