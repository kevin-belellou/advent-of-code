package com.belellou.kevin.advent.year2017;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day12 extends AbstractDaySolver<Integer, Integer> {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)");

    private static Graph<Program, DefaultEdge> createGraph(BufferedReader reader) {
        Graph<Program, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        reader.lines()
              .map(PATTERN::matcher)
              .map(matcher -> matcher.results()
                                     .map(MatchResult::group)
                                     .toList())
              .map(strings -> strings.stream()
                                     .map(Integer::valueOf)
                                     .toList())
              .forEach(programList -> {
                  Program program = getOrCreateProgram(graph, programList.getFirst());

                  programList.subList(1, programList.size())
                             .forEach(programId -> graph.addEdge(program, getOrCreateProgram(graph, programId)));
              });

        return graph;
    }

    private static Program getOrCreateProgram(Graph<Program, DefaultEdge> graph, int id) {
        return graph.vertexSet()
                    .stream()
                    .filter(program -> program.id() == id)
                    .findFirst()
                    .orElseGet(() -> {
                        Program program = new Program(id);
                        graph.addVertex(program);
                        return program;
                    });
    }

    private static Set<Program> findProgramsInGroup(Graph<Program, DefaultEdge> graph, Program source) {
        Set<Program> programs = new HashSet<>();

        explore(graph, source, programs);

        return programs;
    }

    private static void explore(Graph<Program, DefaultEdge> graph, Program source, Set<Program> visited) {
        graph.outgoingEdgesOf(source)
             .stream()
             .map(graph::getEdgeTarget)
             .filter(visited::add)
             .forEach(program -> explore(graph, program, visited));
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        Graph<Program, DefaultEdge> graph = createGraph(reader);

        Program programZero = graph.vertexSet()
                                   .stream()
                                   .filter(program -> program.id() == 0)
                                   .findFirst()
                                   .orElseThrow();

        return findProgramsInGroup(graph, programZero).size();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 239;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        Graph<Program, DefaultEdge> graph = createGraph(reader);

        ModifiableInteger numberOfGroups = new ModifiableInteger(0);
        Set<Program> accountedPrograms = new HashSet<>();

        while (accountedPrograms.size() != graph.vertexSet().size()) {
            graph.vertexSet()
                 .stream()
                 .filter(program -> !accountedPrograms.contains(program))
                 .findFirst()
                 .ifPresent(program -> {
                     accountedPrograms.addAll(findProgramsInGroup(graph, program));
                     numberOfGroups.increment();
                 });
        }

        return numberOfGroups.getValue();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 215;
    }

    public record Program(int id) {}
}
