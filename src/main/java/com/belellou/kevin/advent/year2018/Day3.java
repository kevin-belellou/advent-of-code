package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day3 extends AbstractDaySolver<Integer, Integer> {

    public static final int OVERLAP_INDICATOR = -1;
    private static final Pattern CLAIM_PATTERN = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

    private static Claim parseClaim(String line) {
        Matcher matcher = CLAIM_PATTERN.matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }

        int id = Integer.parseInt(matcher.group(1));
        int left = Integer.parseInt(matcher.group(2));
        int top = Integer.parseInt(matcher.group(3));
        int width = Integer.parseInt(matcher.group(4));
        int height = Integer.parseInt(matcher.group(5));

        return new Claim(id, left, top, width, height);
    }

    private static int[][] computeFabric(List<Claim> claims) {
        int maxX = claims.stream().mapToInt(claim -> claim.top + claim.height).max().orElseThrow();
        int maxY = claims.stream().mapToInt(claim -> claim.left + claim.width).max().orElseThrow();

        int[][] fabric = new int[maxX][maxY];

        for (Claim claim : claims) {
            for (int i = claim.top; i < claim.top + claim.height; i++) {
                for (int j = claim.left; j < claim.left + claim.width; j++) {
                    fabric[j][i] = fabric[j][i] == 0 ? claim.id : OVERLAP_INDICATOR;
                }
            }
        }

        return fabric;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Claim> claims = reader.lines().map(Day3::parseClaim).toList();

        int[][] fabric = computeFabric(claims);

        long sum = Arrays.stream(fabric)
                         .mapToLong(array -> Arrays.stream(array)
                                                   .filter(claimId -> claimId == OVERLAP_INDICATOR)
                                                   .count())
                         .sum();

        return Math.toIntExact(sum);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 103_806;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Claim> claims = reader.lines().map(Day3::parseClaim).toList();

        int[][] fabric = computeFabric(claims);

        Map<Integer, Integer> claimIdToCount = new HashMap<>();

        Arrays.stream(fabric)
              .forEach(array -> Arrays.stream(array)
                                      .filter(claimId -> claimId != OVERLAP_INDICATOR && claimId != 0)
                                      .forEach(claimId -> claimIdToCount.merge(claimId, 1, Integer::sum)));

        return claimIdToCount.entrySet()
                             .stream()
                             .filter(entry -> {
                                 Claim claim = claims.get(entry.getKey() - 1);

                                 return claim.width * claim.height == entry.getValue();
                             })
                             .map(Map.Entry::getKey)
                             .findFirst()
                             .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 625;
    }

    private record Claim(int id, int left, int top, int width, int height) {}
}
