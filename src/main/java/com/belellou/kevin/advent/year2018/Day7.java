package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day7 extends AbstractDaySolver<String, Integer> {

    private static final Pattern STEP_PATTERN = Pattern.compile("Step (.) must be finished before step (.) can begin.");

    private static final int NUMBER_OF_WORKERS = 5;
    private static final int STEP_DURATION = 60;
    private static final Step EMPTY_STEP = new Step("EMPTY", new HashSet<>(), 0);

    private static Map<String, Step> getStepsMap(BufferedReader reader) {
        Map<String, Step> steps = new HashMap<>();

        reader.lines().forEach(line -> {
            Matcher matcher = STEP_PATTERN.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid input: " + line);
            }

            Step currentStep = getStep(matcher.group(1), steps);
            Step stepUnlocked = getStep(matcher.group(2), steps);

            stepUnlocked.unlockedBy.add(currentStep);
        });

        return steps;
    }

    private static Step getStep(String name, Map<String, Step> steps) {
        Step step = steps.getOrDefault(name, new Step(name));
        steps.put(name, step);
        return step;
    }

    private static TreeSet<Step> initializeTreeSet(Map<String, Step> steps) {
        TreeSet<Step> availableSteps = new TreeSet<>();

        steps.values()
             .stream()
             .filter(step -> step.unlockedBy.isEmpty())
             .forEach(availableSteps::add);

        return availableSteps;
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        Map<String, Step> steps = getStepsMap(reader);

        TreeSet<Step> availableSteps = initializeTreeSet(steps);

        StringBuilder sb = new StringBuilder();
        Set<Step> executedSteps = new HashSet<>();

        while (!availableSteps.isEmpty()) {
            Step current = availableSteps.first();
            sb.append(current.name());

            executedSteps.add(current);
            availableSteps.clear();

            steps.values()
                 .stream()
                 .filter(step -> executedSteps.containsAll(step.unlockedBy))
                 .filter(step -> !executedSteps.contains(step))
                 .forEach(availableSteps::add);
        }

        return sb.toString();
    }

    @Override
    public String getFirstStarSolution() {
        //noinspection SpellCheckingInspection
        return "SCLPAMQVUWNHODRTGYKBJEFXZI";
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        Map<String, Step> steps = getStepsMap(reader);

        int seconds = 0;
        Set<Step> executedSteps = new HashSet<>();
        TreeSet<Step> availableSteps = initializeTreeSet(steps);

        List<Worker> workers = new ArrayList<>(NUMBER_OF_WORKERS);
        IntStream.range(0, NUMBER_OF_WORKERS).forEach(i -> workers.add(new Worker()));

        while (executedSteps.size() < steps.size()) {
            for (Step step : availableSteps) {
                workers.stream()
                       .filter(worker -> worker.getStep() == EMPTY_STEP)
                       .findFirst()
                       .ifPresent(worker -> worker.setStep(step));
            }

            workers.forEach(worker -> worker.getStep().tick());

            workers.stream()
                   .filter(worker -> worker.getStep().isFinished())
                   .forEach(worker -> {
                       executedSteps.add(worker.getStep());
                       worker.setStep(EMPTY_STEP);
                   });

            availableSteps.clear();

            steps.values()
                 .stream()
                 .filter(step -> !step.hasStarted())
                 .filter(step -> executedSteps.containsAll(step.unlockedBy))
                 .filter(step -> !executedSteps.contains(step))
                 .forEach(availableSteps::add);

            seconds++;
        }

        return seconds;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 1_234;
    }

    private static final class Step implements Comparable<Step> {

        private final String name;
        private final Set<Step> unlockedBy;
        private final int duration;

        private int remainingTime;

        private Step(String name, Set<Step> unlockedBy, int duration) {
            this.name = name;
            this.unlockedBy = unlockedBy;
            this.duration = duration;

            remainingTime = duration;
        }

        public Step(String name) {
            this(name, new HashSet<>(), getDuration(name));
        }

        private static int getDuration(String name) {
            return name.charAt(0) - 'A' + STEP_DURATION + 1;
        }

        public void tick() {
            remainingTime--;
        }

        public boolean hasStarted() {
            return remainingTime != duration;
        }

        public boolean isFinished() {
            return remainingTime == 0;
        }

        @Override
        public int compareTo(Step o) {
            return name.compareTo(o.name);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Step step)) {
                return false;
            }
            return Objects.equals(name, step.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        public String name() {
            return name;
        }

        public Set<Step> unlockedBy() {
            return unlockedBy;
        }

        public int duration() {
            return duration;
        }

        @Override
        public String toString() {
            return "Step{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static final class Worker {

        private Step step;

        public Worker() {
            step = EMPTY_STEP;
        }

        public Step getStep() {
            return step;
        }

        public void setStep(Step step) {
            this.step = step;
        }

        @Override
        public String toString() {
            return "Worker{" +
                    "step=" + step +
                    '}';
        }
    }
}
