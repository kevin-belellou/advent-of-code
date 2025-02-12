package com.belellou.kevin.advent.year2016;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.lang3.tuple.Pair;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings({"unused"})
public class Day22 extends AbstractDaySolver<Integer, Integer> {

    private static final String SPACE_DELIMITER_REGEX = "\\s+";
    private static final String DASH_DELIMITER = "-";
    private static final String EMPTY_STRING = "";
    private static final String X_CHAR = "x";
    private static final String Y_CHAR = "y";
    private static final String T_CHAR = "T";

    private static List<Node> getInitialNodes(BufferedReader reader) {
        List<String> lines = new ArrayList<>(reader.lines().toList());
        lines.removeFirst();
        lines.removeFirst();

        List<Node> nodes = new ArrayList<>(lines.stream().map(Day22::parseNode).toList());

        Node target = nodes.stream().filter(node -> node.y == 0).toList().getLast();
        nodes.set(nodes.indexOf(target),
                  new Node(target.x, target.y, target.size, target.used, target.available, true));

        return nodes;
    }

    private static Node parseNode(String line) {
        String[] splitBySpace = line.split(SPACE_DELIMITER_REGEX);
        String[] splitByDash = splitBySpace[0].split(DASH_DELIMITER);

        return new Node(getIntValue(splitByDash[1], X_CHAR), getIntValue(splitByDash[2], Y_CHAR),
                        getIntValue(splitBySpace[1], T_CHAR), getIntValue(splitBySpace[2], T_CHAR),
                        getIntValue(splitBySpace[3], T_CHAR), false);
    }

    private static int getIntValue(String value, String replaceChar) {
        return Integer.parseInt(value.replace(replaceChar, EMPTY_STRING));
    }

    private static BiConsumer<Node, Consumer<Pair<Node, Node>>> getAllPossiblePairs(List<Node> nodes) {
        return (node, consumer) -> {
            for (Node otherNode : nodes) {
                if (node != otherNode && node.used != 0 && node.used <= otherNode.available) {
                    consumer.accept(Pair.of(node, otherNode));
                }
            }
        };
    }

    private static BiConsumer<Node, Consumer<Pair<Node, Node>>> getAllValidPairs(List<Node> nodes,
                                                                                 Pair<Node, Node> lastOperation) {
        return (node, consumer) -> {
//            Node targetNode = nodes.stream().filter(Node::isTarget).findFirst().orElseThrow();
//            if (!node.isCloseTo(targetNode)) {
//                return;
//            }

            for (Node otherNode : nodes) {
                if (lastOperation != null && node.isSameNodeAs(lastOperation.getRight()) &&
                        otherNode.isSameNodeAs(lastOperation.getLeft())) {
                    continue;
                }

                if (node.isAdjacentTo(otherNode) && node.used != 0 && node.used <= otherNode.available) {
                    consumer.accept(Pair.of(node, otherNode));
                }
            }
        };
    }

    private static Integer explorePaths(List<Node> nodes, Integer numberOfSteps, Pair<Node, Node> lastOperation)
            throws InterruptedException, ExecutionException {
        if (numberOfSteps > 50) {
            return Integer.MAX_VALUE;
        }

        if (nodes.stream().anyMatch(node -> node.isTarget && node.isExit())) {
            return numberOfSteps;
        }

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            List<Subtask<Integer>> subtasks = new ArrayList<>();

            nodes.stream().mapMulti(getAllValidPairs(nodes, lastOperation)).forEach(pair -> {
                List<Node> newNodes = new ArrayList<>(nodes);

                Node source = pair.getLeft();
                Node newSource = new Node(source.x, source.y, source.size, 0, source.size, false);
                newNodes.set(newNodes.indexOf(source), newSource);

                Node target = pair.getRight();
                Node newTarget = new Node(target.x, target.y, target.size, target.used + source.used,
                                          target.available - source.used, source.isTarget);
                newNodes.set(newNodes.indexOf(target), newTarget);

                subtasks.add(scope.fork(() -> explorePaths(newNodes, numberOfSteps + 1, pair)));
            });

            scope.join().throwIfFailed();

            return subtasks.stream()
                           .mapToInt(Subtask::get)
                           .filter(steps -> steps != Integer.MAX_VALUE)
                           .min()
                           .orElse(Integer.MAX_VALUE);
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<Node> nodes = getInitialNodes(reader);

        return Math.toIntExact(nodes.stream().mapMulti(getAllPossiblePairs(nodes)).count());
    }

    @Override
    public Integer getFirstStarSolution() {
        return 1_007;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<Node> nodes = getInitialNodes(reader);

        try {
            return explorePaths(nodes, 0, null);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }

    private record Node(int x, int y, int size, int used, int available, boolean isTarget) {

        private static final int CLOSE_DISTANCE = 25;

        public boolean isExit() {
            return x == 0 && y == 0;
        }

        public boolean isAdjacentTo(Node otherNode) {
            if (otherNode == null || this == otherNode) {
                return false;
            }

            return Math.abs(x - otherNode.x) == 1 && y == otherNode.y ||
                    x == otherNode.x && Math.abs(y - otherNode.y) == 1;
        }

        public boolean isSameNodeAs(Node otherNode) {
            if (otherNode == null) {
                return false;
            } else if (this == otherNode) {
                return true;
            } else {
                return x == otherNode.x && y == otherNode.y;
            }
        }

        public boolean isCloseTo(Node otherNode) {
            if (otherNode == null) {
                return false;
            }

            return Math.abs(x - otherNode.x) <= CLOSE_DISTANCE && Math.abs(y - otherNode.y) <= CLOSE_DISTANCE;
        }
    }
}
