package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day18 extends AbstractDaySolver<Integer> {

    public Day18() {
        super(Day18.class);
    }

    private static Boolean[][] getInitialLights(BufferedReader reader, boolean cornerAlwaysOn) {
        Boolean[][] lights = new Boolean[100][100];

        List<String> lines = reader.lines().toList();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            for (int j = 0; j < line.length(); j++) {
                if (cornerAlwaysOn &&
                        (i == 0 && j == 0 || i == 99 && j == 99 || i == 99 && j == 0 || i == 0 && j == 99)) {
                    lights[i][j] = Boolean.TRUE;
                } else {
                    lights[i][j] = line.charAt(j) == '#';
                }
            }
        }
        return lights;
    }

    private static Boolean[][] iterate(Boolean[][] lights, boolean cornerAlwaysOn) {
        Boolean[][] newLights = new Boolean[100][100];

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int neighbours = 0;

                if (cornerAlwaysOn &&
                        (i == 0 && j == 0 || i == 99 && j == 99 || i == 99 && j == 0 || i == 0 && j == 99)) {
                    newLights[i][j] = Boolean.TRUE;
                    continue;
                }

                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (k < 0 || k == 100 || l < 0 || l == 100) {
                            continue;
                        } else if (k == i && l == j) {
                            continue;
                        }

                        if (lights[k][l]) {
                            neighbours++;
                        }
                    }
                }

                newLights[i][j] = neighbours == 3 || (neighbours == 2 && lights[i][j]);
            }
        }

        return newLights;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Boolean[][] lights = getInitialLights(reader, false);

        for (int i = 0; i < 100; i++) {
            lights = iterate(lights, false);
        }

        return (int) Arrays.stream(lights).flatMap(Arrays::stream).filter(Boolean.TRUE::equals).count();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 821;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        Boolean[][] lights = getInitialLights(reader, true);

        for (int i = 0; i < 100; i++) {
            lights = iterate(lights, true);
        }

        return (int) Arrays.stream(lights).flatMap(Arrays::stream).filter(Boolean.TRUE::equals).count();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 886;
    }
}
