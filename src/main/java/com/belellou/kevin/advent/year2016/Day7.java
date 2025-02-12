package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.MutablePair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day7 extends AbstractDaySolver<Long, Long> {

    private static final char OPENING_BRACKET = '[';
    private static final char CLOSING_BRACKET = ']';

    private static boolean doesIpSupportTls(String ip) {
        boolean hasAbba = false;
        boolean isInHypernet = false;

        for (int i = 3; i < ip.length(); i++) {
            char c = ip.charAt(i);

            if (c == OPENING_BRACKET) {
                isInHypernet = true;
            } else if (c == CLOSING_BRACKET) {
                isInHypernet = false;
            } else if (c == ip.charAt(i - 3) && c != ip.charAt(i - 2) && ip.charAt(i - 2) == ip.charAt(i - 1)) {
                if (isInHypernet) {
                    return false;
                }
                hasAbba = true;
            }
        }

        return hasAbba;
    }

    private static boolean doesIpSupportSsl(String ip) {
        Set<MutablePair<Character, Character>> abas = new HashSet<>();
        Set<MutablePair<Character, Character>> babs = new HashSet<>();

        boolean isInHypernet = false;

        for (int i = 2; i < ip.length(); i++) {
            char c = ip.charAt(i);

            if (c == OPENING_BRACKET) {
                isInHypernet = true;
            } else if (c == CLOSING_BRACKET) {
                isInHypernet = false;
            } else if (c == ip.charAt(i - 2) && c != ip.charAt(i - 1)) {
                if (isInHypernet) {
                    MutablePair<Character, Character> pair = MutablePair.of(c, ip.charAt(i - 1));

                    if (abas.contains(pair)) {
                        return true;
                    }

                    babs.add(pair);
                } else {
                    MutablePair<Character, Character> pair = MutablePair.of(ip.charAt(i - 1), c);

                    if (babs.contains(pair)) {
                        return true;
                    }

                    abas.add(pair);
                }
            }
        }

        return false;
    }

    @Override
    protected Long doSolveFirstStar(BufferedReader reader) {
        return reader.lines().filter(Day7::doesIpSupportTls).count();
    }

    @Override
    public Long getFirstStarSolution() {
        return 110L;
    }

    @Override
    protected Long doSolveSecondStar(BufferedReader reader) {
        return reader.lines().filter(Day7::doesIpSupportSsl).count();
    }

    @Override
    public Long getSecondStarSolution() {
        return 242L;
    }
}
