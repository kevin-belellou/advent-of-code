package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day14 extends AbstractDaySolver<Long, Long> {

    private static Long find64thKeyIndex(BufferedReader reader, boolean useKeyStretching) throws IOException {
        String salt = reader.readLine();
        long index = 0;

        List<Pair<Character, Long>> list = new ArrayList<>();
        List<Pair<Character, Long>> keys = new ArrayList<>();

        while (true) {
            String hash = DigestUtils.md5Hex(salt + index);

            if (useKeyStretching) {
                for (int i = 0; i < 2016; i++) {
                    hash = DigestUtils.md5Hex(hash);
                }
            }

            boolean foundTriplet = false;

            for (int i = 2; i < hash.length(); i++) {
                char c = hash.charAt(i);

                String triplet = new StringBuilder().repeat(c, 3).toString();
                String quintuplet = new StringBuilder().repeat(c, 5).toString();

                if (!foundTriplet && hash.substring(i - 2, i + 1).equals(triplet)) {
                    foundTriplet = true;
                    list.add(ImmutablePair.of(c, index));
                }

                if (i >= 4 && hash.substring(i - 4, i + 1).equals(quintuplet)) {
                    long finalCounter = index;
                    List<Pair<Character, Long>> winners = list.stream()
                                                              .filter(o -> o.getLeft() == c &&
                                                                      o.getRight() != finalCounter &&
                                                                      o.getRight() + 1000 >= finalCounter)
                                                              .toList();

                    for (Pair<Character, Long> winner : winners) {
                        list.remove(winner);
                        keys.add(winner);

                        // Keys should be verified in order, so we overshoot the target
                        if (keys.size() == 64 + 10) {
                            keys.sort(Comparator.comparingLong(Pair::getRight));
                            return keys.get(63).getRight();
                        }
                    }
                }
            }

            index++;
        }
    }

    @Override
    protected Long doSolveFirstStar(BufferedReader reader) throws IOException {
        return find64thKeyIndex(reader, false);
    }

    @Override
    public Long getFirstStarSolution() {
        return 18_626L;
    }

    @Override
    protected Long doSolveSecondStar(BufferedReader reader) throws IOException {
        return find64thKeyIndex(reader, true);
    }

    @Override
    public Long getSecondStarSolution() {
        return 20_092L;
    }
}
