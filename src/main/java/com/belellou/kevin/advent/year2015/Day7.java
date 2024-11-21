package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.Day;
import com.belellou.kevin.advent.generic.Year;

@SuppressWarnings("unused")
public class Day7 extends AbstractDaySolver {

    private static final String GROUP_FIRST_OPERAND = "firstOperand";
    private static final String GROUP_SECOND_OPERAND = "secondOperand";
    private static final String GROUP_OPERATION = "operation";
    private static final String GROUP_VALUE = "value";
    private static final String GROUP_OUT = "out";

    private static final Pattern PROVIDE_PATTERN = Pattern.compile("^" +
                                                                           "(?<" + GROUP_VALUE + ">\\w+)" +
                                                                           " -> " +
                                                                           "(?<" + GROUP_OUT + ">[a-z]+)" +
                                                                           "$");
    private static final Pattern NOT_PATTERN = Pattern.compile("^" +
                                                                       "NOT " +
                                                                       "(?<" + GROUP_FIRST_OPERAND + ">[a-z]+)" +
                                                                       " -> " +
                                                                       "(?<" + GROUP_OUT + ">[a-z]+)" +
                                                                       "$");
    private static final Pattern OP_PATTERN = Pattern.compile("^" +
                                                                      "(?<" + GROUP_FIRST_OPERAND + ">[a-z]+) " +
                                                                      "(?<" + GROUP_OPERATION + ">[A-Z]+) " +
                                                                      "(?<" + GROUP_SECOND_OPERAND + ">[a-z]+)" +
                                                                      " -> " +
                                                                      "(?<" + GROUP_OUT + ">[a-z]+)" +
                                                                      "$");
    private static final Pattern SHIFT_PATTERN = Pattern.compile("^" +
                                                                         "(?<" + GROUP_FIRST_OPERAND + ">[a-z]+) " +
                                                                         "(?<" + GROUP_OPERATION + ">[A-Z]+) " +
                                                                         "(?<" + GROUP_VALUE + ">\\d+)" +
                                                                         " -> " +
                                                                         "(?<" + GROUP_OUT + ">[a-z]+)" +
                                                                         "$");
    private static final Pattern WTF_PATTERN = Pattern.compile("^" +
                                                                       "(?<" + GROUP_VALUE + ">\\d+) " +
                                                                       "(?<" + GROUP_OPERATION + ">[A-Z]+) " +
                                                                       "(?<" + GROUP_SECOND_OPERAND + ">[a-z]+)" +
                                                                       " -> " +
                                                                       "(?<" + GROUP_OUT + ">[a-z]+)" +
                                                                       "$");

    public Day7() {
        super(Year.YEAR_2015, Day.DAY_7);
    }

    @SuppressWarnings("DuplicatedCode")
    private static void fillGraph(Graph<String, CustomEdge> graph, String line) {
        Matcher matcher;

        matcher = PROVIDE_PATTERN.matcher(line);
        if (matcher.matches()) {
            String value = matcher.group(GROUP_VALUE);
            String out = matcher.group(GROUP_OUT);

            graph.addVertex(value);
            graph.addVertex(out);

            graph.addEdge(value, out, CustomEdge.of(Operation.PROVIDE, value));

            return;
        }

        matcher = NOT_PATTERN.matcher(line);
        if (matcher.matches()) {
            String operand = matcher.group(GROUP_FIRST_OPERAND);
            String out = matcher.group(GROUP_OUT);

            graph.addVertex(operand);
            graph.addVertex(out);

            graph.addEdge(operand, out, CustomEdge.of(Operation.NOT));

            return;
        }

        matcher = OP_PATTERN.matcher(line);
        if (matcher.matches()) {
            String firstOperand = matcher.group(GROUP_FIRST_OPERAND);
            String operation = matcher.group(GROUP_OPERATION);
            String secondOperand = matcher.group(GROUP_SECOND_OPERAND);
            String out = matcher.group(GROUP_OUT);

            graph.addVertex(firstOperand);
            graph.addVertex(secondOperand);
            graph.addVertex(out);

            graph.addEdge(firstOperand, out, CustomEdge.of(operation));
            graph.addEdge(secondOperand, out, CustomEdge.of(operation));

            return;
        }

        matcher = SHIFT_PATTERN.matcher(line);
        if (matcher.matches()) {
            String firstOperand = matcher.group(GROUP_FIRST_OPERAND);
            String operation = matcher.group(GROUP_OPERATION);
            String value = matcher.group(GROUP_VALUE);
            String out = matcher.group(GROUP_OUT);

            graph.addVertex(firstOperand);
            graph.addVertex(out);

            graph.addEdge(firstOperand, out, CustomEdge.of(operation, value));

            return;
        }

        matcher = WTF_PATTERN.matcher(line);
        if (matcher.matches()) {
            String value = matcher.group(GROUP_VALUE);
            String operation = matcher.group(GROUP_OPERATION);
            String secondOperand = matcher.group(GROUP_SECOND_OPERAND);
            String out = matcher.group(GROUP_OUT);

            graph.addVertex(value);
            graph.addVertex(secondOperand);
            graph.addVertex(out);

            graph.addEdge(value, out, CustomEdge.of(operation));
            graph.addEdge(secondOperand, out, CustomEdge.of(operation));

            return;
        }

        throw new IllegalArgumentException(line);
    }

    private static void computeGraph(SimpleDirectedGraph<String, CustomEdge> graph, Map<String, Integer> map) {
        TopologicalOrderIterator<String, CustomEdge> topologicalOrderIterator = new TopologicalOrderIterator<>(graph);

        while (topologicalOrderIterator.hasNext()) {
            String vertex = topologicalOrderIterator.next();

            try {
                map.put(vertex, Integer.parseInt(vertex));
                continue;
            } catch (NumberFormatException ignored) {
            }

            Set<CustomEdge> customEdges = graph.incomingEdgesOf(vertex);

            if (customEdges.isEmpty()) {
                throw new IllegalStateException("Empty edges for " + vertex);
            } else if (customEdges.size() == 1) {
                CustomEdge customEdge = customEdges.stream().findFirst().get();
                String edgeSource = graph.getEdgeSource(customEdge);

                switch (customEdge.operation) {
                    case PROVIDE -> map.put(vertex, map.get(edgeSource));
                    case NOT -> map.put(vertex, ~map.get(edgeSource));
                    case LSHIFT -> map.put(vertex, map.get(edgeSource) << customEdge.value);
                    case RSHIFT -> map.put(vertex, map.get(edgeSource) >> customEdge.value);
                    default -> throw new IllegalStateException(
                            "Operation not supported: " + customEdge.operation + " for vertex " + vertex);
                }
            } else if (customEdges.size() == 2) {
                List<String> listEdgeSources = customEdges.stream().map(graph::getEdgeSource).toList();
                String firstEdgeSource = listEdgeSources.get(0);
                String secondEdgeSource = listEdgeSources.get(1);

                Operation operation = customEdges.stream().findFirst().orElseThrow().operation;
                switch (operation) {
                    case AND -> map.put(vertex, map.get(firstEdgeSource) & map.get(secondEdgeSource));
                    case OR -> map.put(vertex, map.get(firstEdgeSource) | map.get(secondEdgeSource));
                    default -> throw new IllegalStateException(
                            "Operation not supported: " + operation + " for vertex " + vertex);
                }
            } else {
                throw new IllegalStateException(
                        "Edges for " + vertex + " are not supported (size " + customEdges.size() + ")");
            }
        }
    }

    @Override
    protected int doSolveFirstStar(BufferedReader reader) {
        SimpleDirectedGraph<String, CustomEdge> graph = new SimpleDirectedGraph<>(CustomEdge.class);

        reader.lines().forEach(line -> fillGraph(graph, line));

        Map<String, Integer> map = new HashMap<>(graph.vertexSet().size());

        computeGraph(graph, map);

        return map.get("a");
    }

    @Override
    public int getFirstStarSolution() {
        return 16_076;
    }

    @Override
    protected int doSolveSecondStar(BufferedReader reader) {
        SimpleDirectedGraph<String, CustomEdge> graph = new SimpleDirectedGraph<>(CustomEdge.class);

        reader.lines().forEach(line -> fillGraph(graph, line));

        CustomEdge edgeToB = graph.incomingEdgesOf("b").stream().findFirst().orElseThrow();
        String edgeSource = graph.getEdgeSource(edgeToB);
        graph.removeVertex(edgeSource);

        String newValue = String.valueOf(getFirstStarSolution());
        graph.addVertex(newValue);
        graph.addEdge(newValue, "b", CustomEdge.of(Operation.PROVIDE, newValue));

        Map<String, Integer> map = new HashMap<>(graph.vertexSet().size());

        computeGraph(graph, map);

        return map.get("a");
    }

    @Override
    public int getSecondStarSolution() {
        return 2_797;
    }

    private enum Operation {
        PROVIDE,
        AND,
        OR,
        NOT,
        LSHIFT,
        RSHIFT
    }

    private static class CustomEdge extends DefaultEdge {

        private final Operation operation;
        private final int value;

        private CustomEdge(Operation operation) {
            this(operation, "");
        }

        private CustomEdge(Operation operation, String value) {
            this.operation = operation;
            this.value = getValueFromString(value);
        }

        public static CustomEdge of(Operation operation) {
            return new CustomEdge(operation);
        }

        public static CustomEdge of(String operation) {
            return new CustomEdge(Operation.valueOf(operation));
        }

        public static CustomEdge of(Operation operation, String value) {
            return new CustomEdge(operation, value);
        }

        public static CustomEdge of(String operation, String value) {
            return new CustomEdge(Operation.valueOf(operation), value);
        }

        private static int getValueFromString(String value) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return Integer.MAX_VALUE;
            }
        }
    }
}
