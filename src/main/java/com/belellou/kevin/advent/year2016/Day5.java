package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day5 extends AbstractDaySolver<String, String> {

    @Override
    protected String doSolveFirstStar(BufferedReader reader) throws IOException {
        String doorId = reader.readLine();

        long counter = 0;
        StringBuilder password = new StringBuilder();

        while (password.length() < 8) {
            String hash = DigestUtils.md5Hex(doorId + counter);

            if (hash.startsWith("00000")) {
                password.append(hash.charAt(5));
            }

            counter++;
        }

        return password.toString();
    }

    @Override
    public String getFirstStarSolution() {
        return "f97c354d";
    }

    @Override
    protected String doSolveSecondStar(BufferedReader reader) throws IOException {
        String doorId = reader.readLine();

        long counter = 0;
        StringBuilder password = new StringBuilder("--------");
        Set<Integer> positionsFound = new HashSet<>();

        while (positionsFound.size() < 8) {
            String hash = DigestUtils.md5Hex(doorId + counter);

            if (hash.startsWith("00000")) {
                try {
                    int index = Integer.parseInt(String.valueOf(hash.charAt(5)));

                    if (index < 8 && index >= 0 && !positionsFound.contains(index)) {
                        password.setCharAt(index, hash.charAt(6));
                        positionsFound.add(index);
                    }
                } catch (NumberFormatException e) {
                    // Do nothing
                }
            }

            counter++;
        }

        return password.toString();
    }

    @Override
    public String getSecondStarSolution() {
        return "863dde27";
    }
}
