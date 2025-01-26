package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day20 extends AbstractDaySolver<Long, Long> {

    private static final String DASH = "-";

    private static final long MIN_IP = 0L;
    private static final long MAX_IP = 4_294_967_295L;

    public Day20() {
        super(Day20.class);
    }

    private static List<BlacklistEntry> getBlacklist(BufferedReader reader) {
        List<BlacklistEntry> blacklist = reader.lines().map(Day20::parseEntry).sorted().toList();

        return consolidateBlacklist(blacklist);
    }

    private static BlacklistEntry parseEntry(String line) {
        String[] split = line.split(DASH);
        return new BlacklistEntry(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    private static List<BlacklistEntry> consolidateBlacklist(List<BlacklistEntry> blacklist) {
        ArrayList<BlacklistEntry> consolidatedBlackList = new ArrayList<>();
        consolidatedBlackList.add(blacklist.getFirst());

        for (int i = 1; i < blacklist.size(); i++) {
            BlacklistEntry currentEntry = blacklist.get(i);
            BlacklistEntry previousEntry = consolidatedBlackList.getLast();

            if (previousEntry.end + 1 >= currentEntry.start) {
                consolidatedBlackList.set(consolidatedBlackList.size() - 1, new BlacklistEntry(previousEntry.start,
                                                                                               Math.max(
                                                                                                       previousEntry.end,
                                                                                                       currentEntry.end)));
            } else {
                consolidatedBlackList.add(currentEntry);
            }
        }

        return consolidatedBlackList;
    }

    @Override
    protected Long doSolveFirstStar(BufferedReader reader) {
        List<BlacklistEntry> blacklist = getBlacklist(reader);

        return blacklist.getFirst().end + 1;
    }

    @Override
    public Long getFirstStarSolution() {
        return 19_449_262L;
    }

    @Override
    protected Long doSolveSecondStar(BufferedReader reader) {
        List<BlacklistEntry> blacklist = getBlacklist(reader);
        long allowedIps = 0;

        for (int i = 0; i < blacklist.size() - 1; i++) {
            BlacklistEntry entry = blacklist.get(i);

            if (i == 0 && entry.start > 0) {
                allowedIps += entry.start - 1;
            } else if (i == blacklist.size() - 1 && entry.end < MAX_IP) {
                allowedIps += MAX_IP - entry.end;
            }

            if (i != blacklist.size() - 1) {
                BlacklistEntry nextEntry = blacklist.get(i + 1);
                allowedIps += nextEntry.start - entry.end - 1;
            }
        }

        return allowedIps;
    }

    @Override
    public Long getSecondStarSolution() {
        return 119L;
    }

    private record BlacklistEntry(Long start, Long end) implements Comparable<BlacklistEntry> {

        @Override
        public int compareTo(BlacklistEntry o) {
            return start.compareTo(o.start);
        }
    }
}
