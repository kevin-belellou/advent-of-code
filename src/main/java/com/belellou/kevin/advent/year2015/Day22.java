package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day22 extends AbstractDaySolver<Integer, Integer> {

    public Day22() {
        super(Day22.class);
    }

    private static Character readBoss(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        return new Character(getNumberFromLine(lines.get(0)), getNumberFromLine(lines.get(1)), 0, 0);
    }

    private static int getNumberFromLine(String line) {
        return Integer.parseInt(line.split(": ")[1]);
    }

    private static boolean doesPlayerWin(Character player, Character boss) {
        boolean isPlayerTurn = true;
        Spell currentSpell = null;
        int currentTimer = 0;

        while (player.hp > 0 && boss.hp > 0) {
            if (currentTimer != 0) {
                currentSpell.effect.accept(player, boss);
                currentTimer--;

                if (currentTimer == 0) {
                    currentSpell = null;
                }

                if (player.hp <= 0 || boss.hp <= 0) {
                    break;
                }
            }

            if (isPlayerTurn) {
                if (player.mana < Spell.lowestManaCost()) {
                    return false;
                }

                Optional<Spell> optionalSpell = chooseSpell(player.mana, currentSpell);

                if (optionalSpell.isEmpty()) {
                    return false;
                }
                Spell newSpell = optionalSpell.get();

                if (newSpell.isInstant()) {
                    newSpell.effect.accept(player, boss);
                } else {
                    currentSpell = newSpell;
                    currentTimer = newSpell.timer;
                }
            } else {
                // Boss turn
                player.hp -= Math.max(1, boss.damage - (currentSpell == Spell.SHIELD
                                                        ? player.armor + 7
                                                        : player.armor));
            }

            isPlayerTurn = !isPlayerTurn;
        }

        return player.hp > 0;
    }

    private static Optional<Spell> chooseSpell(int mana, Spell currentSpell) {
        List<Spell> spells = Spell.listAvailableSpells(mana, currentSpell);
        if (spells.isEmpty()) {
            return Optional.empty();
        }

        Random random = new Random();
        return Optional.of(spells.get(random.nextInt(spells.size())));
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Character boss = readBoss(reader);
        Character player = new Character(50, 0, 0, 500);

        boolean b = doesPlayerWin(player, boss);

        return 0;
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 1;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }

    private enum Spell {

        MAGIC_MISSILE(53, 0, (player, boss) -> boss.hp -= 4),
        DRAIN(73, 0, (player, boss) -> {
            boss.hp -= 2;
            player.hp += 2;
        }),
        SHIELD(113, 6, (player, boss) -> {
        }),
        POISON(173, 6, (player, boss) -> boss.hp -= 3),
        RECHARGE(229, 5, (player, boss) -> player.mana += 101);

        public final int cost;
        public final int timer;
        public final BiConsumer<Character, Character> effect;

        Spell(int cost, int timer, BiConsumer<Character, Character> effect) {
            this.cost = cost;
            this.timer = timer;
            this.effect = effect;
        }

        public static int lowestManaCost() {
            return Arrays.stream(Spell.values())
                         .mapToInt(spell -> spell.cost)
                         .min()
                         .orElseThrow();
        }

        public static List<Spell> listAvailableSpells(int mana, Spell currentSpell) {
            return Arrays.stream(Spell.values())
                         .filter(spell -> spell.cost <= mana && spell != currentSpell)
                         .toList();
        }

        public boolean isInstant() {
            return timer == 0;
        }
    }

    private static final class Character {

        public int hp;
        public int damage;
        public int armor;
        public int mana;

        private Character(int hp, int damage, int armor, int mana) {
            this.hp = hp;
            this.damage = damage;
            this.mana = mana;
        }
    }
}
