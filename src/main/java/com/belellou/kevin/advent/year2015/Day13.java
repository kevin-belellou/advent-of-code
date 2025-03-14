package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.GraphUtil;

@SuppressWarnings("unused")
public class Day13 extends AbstractDaySolver<Integer, Integer> {

    private static final String GROUP_FIRST_GUEST = "firstGuest";
    private static final String GROUP_ACTION = "action";
    private static final String GROUP_AMOUNT = "amount";
    private static final String GROUP_SECOND_GUEST = "secondGuest";

    private static final Pattern PATTERN = Pattern.compile("^" +
                                                                   "(?<" + GROUP_FIRST_GUEST + ">\\w+)" +
                                                                   " would " +
                                                                   "(?<" + GROUP_ACTION + ">\\w+)" +
                                                                   " (?<" + GROUP_AMOUNT + ">\\d+)" +
                                                                   " happiness units by sitting next to " +
                                                                   "(?<" + GROUP_SECOND_GUEST + ">\\w+)." +
                                                                   "$");

    private static final String ACTION_GAIN = "gain";
    private static final String ME = "Me";

    private static void fillGraph(String line, Graph<String, DefaultWeightedEdge> graph) {
        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }

        String firstGuest = matcher.group(GROUP_FIRST_GUEST);
        boolean gainAction = matcher.group(GROUP_ACTION).equals(ACTION_GAIN);
        int amount = Integer.parseInt(matcher.group(GROUP_AMOUNT));
        String secondGuest = matcher.group(GROUP_SECOND_GUEST);

        graph.addVertex(firstGuest);
        graph.addVertex(secondGuest);
        DefaultWeightedEdge edge = graph.addEdge(firstGuest, secondGuest);
        graph.setEdgeWeight(edge, gainAction ? amount : -amount);
    }

    private static SimpleWeightedGraph<String, DefaultWeightedEdge> transformGraph(
            SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        SimpleWeightedGraph<String, DefaultWeightedEdge> undirectedGraph = new SimpleWeightedGraph<>(
                DefaultWeightedEdge.class);

        graph.vertexSet().forEach(undirectedGraph::addVertex);

        graph.edgeSet().forEach(edge -> {
            double edgeWeight = graph.getEdgeWeight(edge);

            String edgeSource = graph.getEdgeSource(edge);
            String edgeTarget = graph.getEdgeTarget(edge);

            if (undirectedGraph.containsEdge(edgeSource, edgeTarget)) {
                return;
            }

            DefaultWeightedEdge returnEdge = graph.getEdge(edgeTarget, edgeSource);
            double returnEdgeWeight = graph.getEdgeWeight(returnEdge);

            DefaultWeightedEdge undirectedEdge = undirectedGraph.addEdge(edgeSource, edgeTarget);
            undirectedGraph.setEdgeWeight(undirectedEdge, -(edgeWeight + returnEdgeWeight));
        });

        return undirectedGraph;
    }

    private static int findOptimalHamiltonianCycleDistance(
            SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> directedGraph) {
        SimpleWeightedGraph<String, DefaultWeightedEdge> undirectedGraph = transformGraph(directedGraph);

        GraphUtil<String, DefaultWeightedEdge> graphUtil = new GraphUtil<>();
        GraphPath<String, DefaultWeightedEdge> tour = graphUtil.getShortestHamiltonianCycle(undirectedGraph);

        return (int) -tour.getWeight();
    }

    private static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> createGraphFromReader(
            BufferedReader reader) {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> directedGraph = new SimpleDirectedWeightedGraph<>(
                DefaultWeightedEdge.class);

        reader.lines().forEach(line -> fillGraph(line, directedGraph));
        return directedGraph;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> directedGraph = createGraphFromReader(reader);

        return findOptimalHamiltonianCycleDistance(directedGraph);
    }

    @Override
    public Integer getFirstStarSolution() {
        return 664;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> directedGraph = createGraphFromReader(reader);

        directedGraph.addVertex(ME);

        for (String vertex : directedGraph.vertexSet()) {
            if (vertex.equals(ME)) {
                continue;
            }

            DefaultWeightedEdge edge = directedGraph.addEdge(ME, vertex);
            directedGraph.setEdgeWeight(edge, 0);

            edge = directedGraph.addEdge(vertex, ME);
            directedGraph.setEdgeWeight(edge, 0);
        }

        return findOptimalHamiltonianCycleDistance(directedGraph);
    }

    @Override
    public Integer getSecondStarSolution() {
        return 640;
    }
}
