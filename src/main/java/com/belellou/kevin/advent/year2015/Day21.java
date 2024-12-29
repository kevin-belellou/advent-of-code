package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day21 extends AbstractDaySolver<Integer> {

    private static final List<Item> WEAPONS = List.of(new Item(ItemType.WEAPON, 8, 4, 0),
                                                      new Item(ItemType.WEAPON, 10, 5, 0),
                                                      new Item(ItemType.WEAPON, 25, 6, 0),
                                                      new Item(ItemType.WEAPON, 40, 7, 0),
                                                      new Item(ItemType.WEAPON, 74, 8, 0));

    private static final List<Item> ARMORS = List.of(new Item(ItemType.ARMOR, 13, 0, 1),
                                                     new Item(ItemType.ARMOR, 31, 0, 2),
                                                     new Item(ItemType.ARMOR, 53, 0, 3),
                                                     new Item(ItemType.ARMOR, 75, 0, 4),
                                                     new Item(ItemType.ARMOR, 102, 0, 5));

    private static final List<Item> RINGS = List.of(new Item(ItemType.RING, 25, 1, 0),
                                                    new Item(ItemType.RING, 50, 2, 0),
                                                    new Item(ItemType.RING, 100, 3, 0),
                                                    new Item(ItemType.RING, 20, 0, 1),
                                                    new Item(ItemType.RING, 40, 0, 2),
                                                    new Item(ItemType.RING, 80, 0, 3));

    public Day21() {
        super(Day21.class);
    }

    private static Character readBoss(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        return new Character(getNumberFromLine(lines.get(0)), getNumberFromLine(lines.get(1)),
                             getNumberFromLine(lines.get(2)));
    }

    private static int getNumberFromLine(String line) {
        return Integer.parseInt(line.split(": ")[1]);
    }

    private static boolean doesPlayerWin(Character player, Character boss) {
        int playerHp = player.hp;
        int bossHp = boss.hp;

        while (playerHp > 0 && bossHp > 0) {
            bossHp -= Math.max(1, player.damage - boss.armor);
            if (bossHp <= 0) {
                break;
            }

            playerHp -= Math.max(1, boss.damage - player.armor);
        }

        return playerHp > 0;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Character boss = readBoss(reader);

        int minCost = Integer.MAX_VALUE;

        for (Item weapons : WEAPONS) {
            // Only weapon
            Character player = new Character(100, weapons.damage, 0);
            if (doesPlayerWin(player, boss)) {
                minCost = Math.min(minCost, weapons.cost);
            }

            // Weapon + armor
            for (Item armor : ARMORS) {
                player = new Character(100, weapons.damage, armor.armor);
                if (doesPlayerWin(player, boss)) {
                    minCost = Math.min(minCost, weapons.cost + armor.cost);
                }

                // Weapon + armor + 1 ring
                for (Item ring1 : RINGS) {
                    player = new Character(100, weapons.damage + ring1.damage, armor.armor + ring1.armor);
                    if (doesPlayerWin(player, boss)) {
                        minCost = Math.min(minCost, weapons.cost + armor.cost + ring1.cost);
                    }

                    // Weapon + armor + 2 rings
                    for (Item ring2 : RINGS) {
                        if (ring1 == ring2) {
                            continue;
                        }

                        player = new Character(100, weapons.damage + ring1.damage + ring2.damage,
                                               armor.armor + ring1.armor + ring2.armor);
                        if (doesPlayerWin(player, boss)) {
                            minCost = Math.min(minCost, weapons.cost + armor.cost + ring1.cost + ring2.cost);
                        }
                    }
                }
            }

            // Weapon + 1 ring
            for (Item ring1 : RINGS) {
                player = new Character(100, weapons.damage + ring1.damage, ring1.armor);
                if (doesPlayerWin(player, boss)) {
                    minCost = Math.min(minCost, weapons.cost + ring1.cost);
                }

                // Weapon + 2 rings
                for (Item ring2 : RINGS) {
                    if (ring1 == ring2) {
                        continue;
                    }

                    player = new Character(100, weapons.damage + ring1.damage + ring2.damage,
                                           ring1.armor + ring2.armor);
                    if (doesPlayerWin(player, boss)) {
                        minCost = Math.min(minCost, weapons.cost + ring1.cost + ring2.cost);
                    }
                }
            }
        }

        return minCost;
    }

    @Override
    public Integer getFirstStarSolution() {
        return 121;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        Character boss = readBoss(reader);

        int maxCost = -Integer.MAX_VALUE;

        for (Item weapons : WEAPONS) {
            // Only weapon
            Character player = new Character(100, weapons.damage, 0);
            if (!doesPlayerWin(player, boss)) {
                maxCost = Math.max(maxCost, weapons.cost);
            }

            // Weapon + armor
            for (Item armor : ARMORS) {
                player = new Character(100, weapons.damage, armor.armor);
                if (!doesPlayerWin(player, boss)) {
                    maxCost = Math.max(maxCost, weapons.cost + armor.cost);
                }

                // Weapon + armor + 1 ring
                for (Item ring1 : RINGS) {
                    player = new Character(100, weapons.damage + ring1.damage, armor.armor + ring1.armor);
                    if (!doesPlayerWin(player, boss)) {
                        maxCost = Math.max(maxCost, weapons.cost + armor.cost + ring1.cost);
                    }

                    // Weapon + armor + 2 rings
                    for (Item ring2 : RINGS) {
                        if (ring1 == ring2) {
                            continue;
                        }

                        player = new Character(100, weapons.damage + ring1.damage + ring2.damage,
                                               armor.armor + ring1.armor + ring2.armor);
                        if (!doesPlayerWin(player, boss)) {
                            maxCost = Math.max(maxCost, weapons.cost + armor.cost + ring1.cost + ring2.cost);
                        }
                    }
                }
            }

            // Weapon + 1 ring
            for (Item ring1 : RINGS) {
                player = new Character(100, weapons.damage + ring1.damage, ring1.armor);
                if (!doesPlayerWin(player, boss)) {
                    maxCost = Math.max(maxCost, weapons.cost + ring1.cost);
                }

                // Weapon + 2 rings
                for (Item ring2 : RINGS) {
                    if (ring1 == ring2) {
                        continue;
                    }

                    player = new Character(100, weapons.damage + ring1.damage + ring2.damage,
                                           ring1.armor + ring2.armor);
                    if (!doesPlayerWin(player, boss)) {
                        maxCost = Math.max(maxCost, weapons.cost + ring1.cost + ring2.cost);
                    }
                }
            }
        }

        return maxCost;
    }

    @Override
    public Integer getSecondStarSolution() {
        return 201;
    }

    private enum ItemType {
        WEAPON, ARMOR, RING
    }

    private record Item(ItemType type, int cost, int damage, int armor) {}

    private record Character(int hp, int damage, int armor) {}
}
