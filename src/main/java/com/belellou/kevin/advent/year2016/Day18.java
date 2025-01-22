package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day18 extends AbstractDaySolver<Integer, Integer> {

    private static final int FIRST_ROW_COUNT = 40;
    private static final int SECOND_ROW_COUNT = 400_000;

    private static final char SAFE_TILE = '.';
    private static final char TRAP_TILE = '^';

    public Day18() {
        super(Day18.class);
    }

    private static void generateTiles(List<String> tiles, int rowCount) {
        for (int i = 1; i < rowCount; i++) {
            StringBuilder builder = new StringBuilder();
            String previousLine = tiles.get(i - 1);

            for (int j = 0; j < previousLine.length(); j++) {
                boolean leftTileTrapped = j > 0 && previousLine.charAt(j - 1) == TRAP_TILE;
                boolean centerTileTrapped = previousLine.charAt(j) == TRAP_TILE;
                boolean rightTileTrapped = j < previousLine.length() - 1 && previousLine.charAt(j + 1) == TRAP_TILE;

                builder.append(leftTileTrapped ^ rightTileTrapped ? TRAP_TILE : SAFE_TILE);
            }

            tiles.add(builder.toString());
        }
    }

    private static int countSafeTiles(List<String> tiles) {
        return tiles.stream()
                    .mapToInt(line -> StringUtils.countMatches(line, SAFE_TILE))
                    .reduce(Integer::sum)
                    .orElseThrow();
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        List<String> tiles = new ArrayList<>();

        tiles.add(reader.readLine());
        generateTiles(tiles, FIRST_ROW_COUNT);

        return countSafeTiles(tiles);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_974;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        List<String> tiles = new ArrayList<>();

        tiles.add(reader.readLine());
        generateTiles(tiles, SECOND_ROW_COUNT);

        return countSafeTiles(tiles);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 19_991_126;
    }
}
