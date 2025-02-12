package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day16 extends AbstractDaySolver<String, String> {

    private static final int LENGTH_1 = 272;
    private static final int LENGTH_2 = 35_651_584;

    private static final char ZERO = '0';
    private static final char ONE = '1';

    private static String generateData(String seed, int length) {
        if (seed.length() >= length) {
            return seed.substring(0, length);
        }

        StringBuilder b = new StringBuilder();

        for (int i = seed.length() - 1; i >= 0; i--) {
            b.append(seed.charAt(i) == ZERO ? ONE : ZERO);
        }

        return generateData(seed + ZERO + b, length);
    }

    private static String getChecksum(String data) {
        StringBuilder checksum = new StringBuilder();

        for (int i = 0; i < data.length(); i += 2) {
            checksum.append(data.charAt(i) == data.charAt(i + 1) ? ONE : ZERO);
        }

        return checksum.length() % 2 == 0 ? getChecksum(checksum.toString()) : checksum.toString();
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) throws IOException {
        String seed = reader.readLine();
        String data = generateData(seed, LENGTH_1);

        return getChecksum(data);
    }

    @Override
    public String getFirstStarSolution() {
        return "01110011101111011";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) throws IOException {
        String seed = reader.readLine();
        String data = generateData(seed, LENGTH_2);

        return getChecksum(data);
    }

    @Override
    public String getSecondStarSolution() {
        return "11001111011000111";
    }
}
