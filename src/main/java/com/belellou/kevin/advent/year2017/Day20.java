package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day20 extends AbstractDaySolver<Integer, Integer> {

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Particle> particles = reader.lines()
                                         .map(Particle::fromString)
                                         .toList();

        IntStream.range(0, 500)
                 .parallel()
                 .forEach(_ -> particles.parallelStream().forEach(Particle::update));

        return particles.stream().
                        min(Comparator.comparingInt(Particle::getManhattanDistance))
                        .map(Particle::number)
                        .orElseThrow();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 364;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Particle> particles = new ArrayList<>(reader.lines()
                                                         .map(Particle::fromString)
                                                         .toList());

        IntStream.range(0, 40)
                 .forEach(_ -> {
                     particles.parallelStream().forEach(Particle::update);
                     particles.removeIf(particle -> particles.parallelStream().anyMatch(particle::hasSamePosition));
                 });

        return particles.size();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 420;
    }

    private record Particle(int number, int[] position, int[] velocity, int[] acceleration) {

        private static final Pattern PATTERN = Pattern.compile(
                "^p=<(-?\\d+),(-?\\d+),(-?\\d+)>, v=<(-?\\d+),(-?\\d+),(-?\\d+)>, a=<(-?\\d+),(-?\\d+),(-?\\d+)>$");
        private static int NUMBER = 0;

        public static Particle fromString(String line) {
            int[] position = new int[3];
            int[] velocity = new int[3];
            int[] acceleration = new int[3];

            Matcher matcher = PATTERN.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid particle line: " + line);
            }

            for (int i = 0; i < 3; i++) {
                position[i] = Integer.parseInt(matcher.group(i + 1));
                velocity[i] = Integer.parseInt(matcher.group(i + 4));
                acceleration[i] = Integer.parseInt(matcher.group(i + 7));
            }

            return new Particle(NUMBER++, position, velocity, acceleration);
        }

        public void update() {
            velocity[0] += acceleration[0];
            velocity[1] += acceleration[1];
            velocity[2] += acceleration[2];

            position[0] += velocity[0];
            position[1] += velocity[1];
            position[2] += velocity[2];
        }

        public int getManhattanDistance() {
            return Math.abs(position[0]) + Math.abs(position[1]) + Math.abs(position[2]);
        }

        public boolean hasSamePosition(Particle particle) {
            if (this == particle) {
                return false;
            }

            return Arrays.equals(position, particle.position);
        }

        @Override
        public String toString() {
            return "Particle{" +
                    "number=" + number +
                    ", position=" + Arrays.toString(position) +
                    ", velocity=" + Arrays.toString(velocity) +
                    ", acceleration=" + Arrays.toString(acceleration) +
                    '}';
        }
    }
}
