package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day4 extends AbstractDaySolver<Integer, Integer> {

    private static Map<Integer, Map<Integer, Integer>> getGuardToMinutesAsleep(List<Record> records) {
        Map<Integer, Map<Integer, Integer>> guardToMinutesAsleep = new HashMap<>();
        int guardId = Record.NO_GUARD_ID;
        LocalDateTime asleepDateTime = null;

        for (Record record : records) {
            switch (record.action()) {
                case BEGINS_SHIFT:
                    guardId = record.guardId();
                    break;
                case FALLS_ASLEEP:
                    asleepDateTime = record.dateTime;
                    break;
                case WAKES_UP:
                    if (record.dateTime.getHour() != 0) {
                        throw new IllegalStateException("Not at midnight");
                    }

                    Map<Integer, Integer> map = guardToMinutesAsleep.computeIfAbsent(guardId, k -> new HashMap<>());

                    int startMinute =
                            Objects.requireNonNull(asleepDateTime).getHour() == 0 ? asleepDateTime.getMinute() : 0;

                    IntStream.range(startMinute, record.dateTime.getMinute())
                             .forEach(minute -> map.merge(minute, 1, Integer::sum));

                    break;
            }
        }
        return guardToMinutesAsleep;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Record> records = reader.lines().map(Record::parse).sorted().toList();

        Map<Integer, Map<Integer, Integer>> guardToMinutesAsleep = getGuardToMinutesAsleep(records);

        int maxSum = 0;
        int result = 0;

        for (Map.Entry<Integer, Map<Integer, Integer>> entry : guardToMinutesAsleep.entrySet()) {
            int sum = entry.getValue()
                           .values()
                           .stream()
                           .reduce(Integer::sum)
                           .orElseThrow();

            if (sum <= maxSum) {
                continue;
            }
            maxSum = sum;

            Map.Entry<Integer, Integer> maxEntry = entry.getValue()
                                                        .entrySet()
                                                        .stream()
                                                        .max(Map.Entry.comparingByValue())
                                                        .orElseThrow();

            result = maxEntry.getKey() * entry.getKey();
        }

        return result;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 21_083;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Record> records = reader.lines().map(Record::parse).sorted().toList();

        Map<Integer, Map<Integer, Integer>> guardToMinutesAsleep = getGuardToMinutesAsleep(records);

        int maxCount = 0;
        int result = 0;

        for (Map.Entry<Integer, Map<Integer, Integer>> entry : guardToMinutesAsleep.entrySet()) {
            Map.Entry<Integer, Integer> maxEntry = entry.getValue()
                                                        .entrySet()
                                                        .stream()
                                                        .max(Map.Entry.comparingByValue())
                                                        .orElseThrow();

            if (maxEntry.getValue() > maxCount) {
                maxCount = maxEntry.getValue();
                result = entry.getKey() * maxEntry.getKey();
            }
        }

        return result;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 53_024;
    }

    private enum Action {
        BEGINS_SHIFT,
        WAKES_UP,
        FALLS_ASLEEP
    }

    private record Record(LocalDateTime dateTime, Action action, int guardId) implements Comparable<Record> {

        public static final int NO_GUARD_ID = -1;
        private static final Pattern PATTERN = Pattern.compile(
                "\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})] (wakes up|falls asleep|Guard #(\\d+) begins shift)");

        public static Record parse(String line) {
            Matcher matcher = PATTERN.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }

            LocalDateTime dateTime = LocalDateTime.of(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5))
            );

            Action action = Arrays.stream(Action.values())
                                  .filter(a -> matcher.group(6).contains(a.name()
                                                                          .replace("_", " ")
                                                                          .toLowerCase()))
                                  .findFirst()
                                  .orElseThrow();

            if (action == Action.BEGINS_SHIFT) {
                return new Record(dateTime, action, Integer.parseInt(matcher.group(7)));
            }

            return new Record(dateTime, action, NO_GUARD_ID);
        }

        @Override
        public int compareTo(Record o) {
            return this.dateTime.compareTo(o.dateTime);
        }
    }
}
