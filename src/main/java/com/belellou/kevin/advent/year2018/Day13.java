package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherers;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day13 extends AbstractDaySolver<String, Integer> {

    private static void readInput(List<String> lines, Piece[][] track, List<Cart> carts) {
        for (int y = 0; y < lines.size(); y++) {
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                Piece piece = Piece.fromSymbol(chars[x]);
                track[y][x] = switch (piece) {
                    case UP_CART, DOWN_CART -> {
                        carts.add(new Cart(x, y, piece));
                        yield Piece.VERTICAL;
                    }
                    case RIGHT_CART, LEFT_CART -> {
                        carts.add(new Cart(x, y, piece));
                        yield Piece.HORIZONTAL;
                    }
                    default -> piece;
                };
            }
        }
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        List<String> lines = reader.lines().toList();

        Piece[][] track = new Piece[lines.size()][lines.getFirst().length()];
        List<Cart> carts = new ArrayList<>();

        readInput(lines, track, carts);

        while (true) {
            carts.stream()
                 .sorted()
                 .forEach(cart -> {
                     switch (cart.piece) {
                         case UP_CART -> {
                             switch (track[--cart.y][cart.x]) {
                                 case VERTICAL -> {}
                                 case CURVE_LEFT -> cart.piece = Piece.RIGHT_CART;
                                 case CURVE_RIGHT -> cart.piece = Piece.LEFT_CART;
                                 case INTERSECTION -> {
                                     switch (cart.getNextDirection()) {
                                         case LEFT -> cart.piece = Piece.LEFT_CART;
                                         case RIGHT -> cart.piece = Piece.RIGHT_CART;
                                         case STRAIGHT -> {}
                                     }
                                 }
                                 default -> throw new IllegalStateException("Illegal state");
                             }
                         }
                         case DOWN_CART -> {
                             switch (track[++cart.y][cart.x]) {
                                 case VERTICAL -> {}
                                 case CURVE_LEFT -> cart.piece = Piece.LEFT_CART;
                                 case CURVE_RIGHT -> cart.piece = Piece.RIGHT_CART;
                                 case INTERSECTION -> {
                                     switch (cart.getNextDirection()) {
                                         case LEFT -> cart.piece = Piece.RIGHT_CART;
                                         case RIGHT -> cart.piece = Piece.LEFT_CART;
                                         case STRAIGHT -> {}
                                     }
                                 }
                                 default -> throw new IllegalStateException("Illegal state");
                             }
                         }
                         case LEFT_CART -> {
                             switch (track[cart.y][--cart.x]) {
                                 case HORIZONTAL -> {}
                                 case CURVE_LEFT -> cart.piece = Piece.DOWN_CART;
                                 case CURVE_RIGHT -> cart.piece = Piece.UP_CART;
                                 case INTERSECTION -> {
                                     switch (cart.getNextDirection()) {
                                         case LEFT -> cart.piece = Piece.DOWN_CART;
                                         case RIGHT -> cart.piece = Piece.UP_CART;
                                         case STRAIGHT -> {}
                                     }
                                 }
                                 default -> throw new IllegalStateException("Illegal state");
                             }
                         }
                         case RIGHT_CART -> {
                             switch (track[cart.y][++cart.x]) {
                                 case HORIZONTAL -> {}
                                 case CURVE_LEFT -> cart.piece = Piece.UP_CART;
                                 case CURVE_RIGHT -> cart.piece = Piece.DOWN_CART;
                                 case INTERSECTION -> {
                                     switch (cart.getNextDirection()) {
                                         case LEFT -> cart.piece = Piece.UP_CART;
                                         case RIGHT -> cart.piece = Piece.DOWN_CART;
                                         case STRAIGHT -> {}
                                     }
                                 }
                                 default -> throw new IllegalStateException("Illegal state");
                             }
                         }
                     }
                 });

            List<Cart> first = carts.stream()
                                    .sorted()
                                    .gather(Gatherers.windowFixed(2))
                                    .filter(pair -> {
                                        if (pair.size() < 2) {
                                            return false;
                                        }
                                        return pair.getFirst().compareTo(pair.getLast()) == 0;
                                    })
                                    .findFirst()
                                    .orElse(List.of());

            if (!first.isEmpty()) {
                Cart firstCollision = first.getFirst();
                return firstCollision.x + "," + firstCollision.y;
            }
        }
    }

    @DisableTest
    @Override
    public String getFirstStarSolution() {
        return "1";
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

    private enum Piece {
        HORIZONTAL('-'),
        VERTICAL('|'),
        INTERSECTION('+'),
        CURVE_LEFT('/'),
        CURVE_RIGHT('\\'),
        EMPTY(' '),
        UP_CART('^'),
        DOWN_CART('v'),
        LEFT_CART('<'),
        RIGHT_CART('>');

        public final char symbol;

        Piece(char symbol) {
            this.symbol = symbol;
        }

        public static Piece fromSymbol(char symbol) {
            for (Piece piece : values()) {
                if (piece.symbol == symbol) {
                    return piece;
                }
            }
            throw new IllegalArgumentException("Invalid symbol for Piece: " + symbol);
        }
    }

    private enum Direction {
        LEFT,
        STRAIGHT,
        RIGHT;

        public static Direction getNextDirection(Direction current) {
            return switch (current) {
                case LEFT -> STRAIGHT;
                case STRAIGHT -> RIGHT;
                case RIGHT -> LEFT;
            };
        }
    }

    private static final class Cart implements Comparable<Cart> {

        public int x;
        public int y;
        public Piece piece;

        private Direction nextDirection = Direction.LEFT;

        private Cart(int x, int y, Piece piece) {
            this.x = x;
            this.y = y;
            this.piece = piece;
        }

        public Direction getNextDirection() {
            Direction nextDirection = this.nextDirection;

            this.nextDirection = Direction.getNextDirection(nextDirection);

            return nextDirection;
        }

        @Override
        public int compareTo(Cart o) {
            return this.y != o.y ? Integer.compare(this.y, o.y) : Integer.compare(this.x, o.x);
        }

        @Override
        public String toString() {
            return "Cart at (%d, %d) with piece %s".formatted(x, y, piece);
        }
    }
}
