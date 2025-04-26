package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day7 extends AbstractDaySolver<String, Integer> {

    private static SimpleDirectedGraph<Program, DefaultEdge> createGraph(BufferedReader reader) {
        List<Program> programs = reader.lines().map(Program::of).toList();

        SimpleDirectedGraph<Program, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);

        programs.forEach(program -> {
            graph.addVertex(program);

            Arrays.stream(program.children)
                  .map(name -> programs.stream()
                                       .filter(p -> p.name.equals(name))
                                       .findFirst()
                                       .orElseThrow())
                  .forEach(child -> {
                      graph.addVertex(child);
                      graph.addEdge(program, child);
                  });
        });

        return graph;
    }

    private static Program findBottomProgram(SimpleDirectedGraph<Program, DefaultEdge> graph) {
        return graph.vertexSet()
                    .stream()
                    .filter(program -> graph.inDegreeOf(program) == 0)
                    .findFirst()
                    .orElseThrow();
    }

    private static int fillTotalWeight(SimpleDirectedGraph<Program, DefaultEdge> graph, Program program) {
        Integer discWeight = graph.outgoingEdgesOf(program)
                                  .stream()
                                  .map(graph::getEdgeTarget)
                                  .map(child -> fillTotalWeight(graph, child))
                                  .reduce(Integer::sum)
                                  .orElse(0);

        program.addDiscWeight(discWeight);

        return program.totalWeight().getValue();
    }

    private static Program findOutlier(SimpleDirectedGraph<Program, DefaultEdge> graph, Program program) {
        List<Program> children = graph.outgoingEdgesOf(program)
                                      .stream()
                                      .map(graph::getEdgeTarget)
                                      .toList();
        Map<Integer, Integer> map = new HashMap<>();

        children.stream()
                .mapToInt(child -> child.totalWeight().getValue())
                .forEach(totalWeight -> {
                    if (map.containsKey(totalWeight)) {
                        map.put(totalWeight, map.get(totalWeight) + 1);
                    } else {
                        map.put(totalWeight, 1);
                    }
                });

        Integer wrongWeight = map.entrySet()
                                 .stream()
                                 .filter(entry -> entry.getValue() == 1)
                                 .findFirst()
                                 .orElse(new HashMap.SimpleEntry<>(0, 0))
                                 .getKey();

        if (wrongWeight == 0) {
            return program;
        }

        Program outlier = children.stream()
                                  .filter(child -> child.totalWeight().getValue() == wrongWeight)
                                  .findFirst()
                                  .orElseThrow();

        return findOutlier(graph, outlier);
    }

    @Override
    protected String doSolveFirstStar(BufferedReader reader) {
        SimpleDirectedGraph<Program, DefaultEdge> graph = createGraph(reader);

        return findBottomProgram(graph).name;
    }

    @Override
    public String getFirstStarSolution() {
        //noinspection SpellCheckingInspection
        return "veboyvy";
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        SimpleDirectedGraph<Program, DefaultEdge> graph = createGraph(reader);

        Program bottomProgram = findBottomProgram(graph);

        fillTotalWeight(graph, bottomProgram);

        Program outlier = findOutlier(graph, bottomProgram);

        DefaultEdge incomingEdge = graph.incomingEdgesOf(outlier)
                                        .stream()
                                        .findFirst()
                                        .orElseThrow();
        Program parent = graph.getEdgeSource(incomingEdge);

        Program sibling = graph.outgoingEdgesOf(parent)
                               .stream()
                               .map(graph::getEdgeTarget)
                               .filter(child -> child != outlier)
                               .findFirst()
                               .orElseThrow();

        return outlier.weight() - Math.abs(sibling.totalWeight().getValue() - outlier.totalWeight().getValue());
    }

    @Override
    public Integer getSecondStarSolution() {
        return 749;
    }

    private record Program(String name, int weight, String[] children, ModifiableInteger totalWeight) {

        private static final Pattern PATTERN = Pattern.compile(
                "^(\\w+)\\s+\\((\\w+)\\)(?:\\s+->\\s+(\\w+(?:,\\s+\\w+)*))?$");

        public static Program of(String line) {
            Matcher matcher = PATTERN.matcher(line);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid program format " + line);
            }

            return new Program(matcher.group(1), Integer.parseInt(matcher.group(2)),
                               matcher.group(3) == null ? new String[0] : matcher.group(3).split(",\\s+"),
                               new ModifiableInteger(0));
        }

        public void addDiscWeight(int discWeight) {
            totalWeight.setValue(weight + discWeight);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Program program)) {
                return false;
            }
            return Objects.equals(name, program.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }
    }
}
