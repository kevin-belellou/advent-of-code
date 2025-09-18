package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day10 extends AbstractDaySolver<Integer, Integer> {

    private static final String BOT = "bot";
    private static final String VALUE = "value";
    private static final String SPACE = " ";

    private static List<Instruction> getInstructions(List<String> lines, List<Bot> bots) {
        List<Instruction> instructions = new ArrayList<>();

        for (String line : lines) {
            Bot currentBot;

            String[] split = line.split(SPACE);

            if (split[0].equals(VALUE)) {
                int chip = Integer.parseInt(split[1]);
                int botNumber = Integer.parseInt(split[5]);

                currentBot = getBot(bots, Type.BOT, botNumber);
                currentBot.receiveChip(chip);
            } else {
                int giverNumber = Integer.parseInt(split[1]);

                int lowChipReceiverNumber = Integer.parseInt(split[6]);
                int highChipReceiverNumber = Integer.parseInt(split[11]);

                currentBot = getBot(bots, Type.BOT, giverNumber);

                Bot lowChipReceiver = getBot(bots, split[5].equals(BOT) ? Type.BOT : Type.OUTPUT,
                                             lowChipReceiverNumber);
                Bot highChipReceiver = getBot(bots, split[10].equals(BOT) ? Type.BOT : Type.OUTPUT,
                                              highChipReceiverNumber);

                instructions.add(new Instruction(currentBot, lowChipReceiver, highChipReceiver));
            }
        }

        return instructions;
    }

    private static Bot getBot(List<Bot> bots, Type type, int number) {
        return bots.stream().filter(bot -> bot.type == type && bot.number == number).findFirst().orElseGet(() -> {
            bots.add(new Bot(type, number));
            return bots.getLast();
        });
    }

    private static int executeInstructions(List<Instruction> instructions, boolean searchForBot) {
        Optional<Instruction> optionalInstruction = findFirstExecutableInstruction(instructions);

        while (optionalInstruction.isPresent()) {
            Instruction instruction = optionalInstruction.orElseThrow();
            instructions.remove(instruction);

            if (searchForBot && isSearchedBot(instruction.giver())) {
                return instruction.giver().number;
            }

            executeInstruction(instruction);

            if (searchForBot && isSearchedBot(instruction.lowReceiver())) {
                return instruction.lowReceiver().number;
            }

            if (searchForBot && isSearchedBot(instruction.highReceiver())) {
                return instruction.highReceiver().number;
            }

            optionalInstruction = findFirstExecutableInstruction(instructions);
        }

        return 0;
    }

    private static Optional<Instruction> findFirstExecutableInstruction(List<Instruction> instructions) {
        return instructions.stream().filter(instruction -> instruction.giver().hasTwoChips()).findFirst();
    }

    private static void executeInstruction(Instruction instruction) {
        instruction.lowReceiver().receiveChip(instruction.giver().giveLowChip());
        instruction.highReceiver().receiveChip(instruction.giver().giveHighChip());
    }

    private static boolean isSearchedBot(Bot bot) {
        return bot.type == Type.BOT && bot.firstChip == 17 && bot.secondChip == 61 ||
                bot.firstChip == 61 && bot.secondChip == 17;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();
        List<Bot> bots = new ArrayList<>();

        List<Instruction> instructions = getInstructions(lines, bots);

        return executeInstructions(instructions, true);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 86;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();
        List<Bot> bots = new ArrayList<>();

        List<Instruction> instructions = getInstructions(lines, bots);

        executeInstructions(instructions, false);

        return bots.stream()
                   .filter(bot -> bot.type == Type.OUTPUT && bot.number >= 0 && bot.number <= 2)
                   .map(bot -> bot.firstChip)
                   .reduce(Math::multiplyExact)
                   .orElseThrow();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 22_847;
    }

    private enum Type {
        OUTPUT,
        BOT
    }

    private record Instruction(Bot giver, Bot lowReceiver, Bot highReceiver) {}

    private static class Bot {

        public final Type type;
        public final int number;

        private int firstChip = -1;
        private int secondChip = -1;

        private Bot(Type type, int number) {
            this.type = type;
            this.number = number;
        }

        public boolean hasTwoChips() {
            return firstChip != -1 && secondChip != -1;
        }

        public void receiveChip(int chip) {
            if (hasTwoChips()) {
                throw new IllegalStateException("Bot already has two chips");
            }

            if (firstChip == -1) {
                firstChip = chip;
            } else {
                secondChip = chip;
            }
        }

        public int giveLowChip() {
            return give(firstChip < secondChip);
        }

        public int giveHighChip() {
            return give(firstChip > secondChip);
        }

        private int give(boolean condition) {
            int returnValue;

            if (firstChip != -1 && condition) {
                returnValue = firstChip;
                firstChip = -1;
            } else {
                returnValue = secondChip;
                secondChip = -1;
            }

            return returnValue;
        }
    }
}
