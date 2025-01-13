package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jgrapht.GraphPath;
import org.jgrapht.GraphTests;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.tour.PalmerHamiltonianCycle;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day9 extends AbstractDaySolver<Integer, Integer> {

    private static final String GROUP_FIRST_LOCATION = "firstLocation";
    private static final String GROUP_SECOND_LOCATION = "secondLocation";
    private static final String GROUP_DISTANCE = "distance";

    private static final Pattern PATTERN = Pattern.compile("^" +
                                                                   "(?<" + GROUP_FIRST_LOCATION + ">\\w+)" +
                                                                   " to " +
                                                                   "(?<" + GROUP_SECOND_LOCATION + ">\\w+)" +
                                                                   " = " +
                                                                   "(?<" + GROUP_DISTANCE + ">\\d+)" +
                                                                   "$");

    public Day9() {
        super(Day9.class);
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        reader.lines().forEach(line -> {
            Matcher matcher = PATTERN.matcher(line);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }

            String firstLocation = matcher.group(GROUP_FIRST_LOCATION);
            String secondLocation = matcher.group(GROUP_SECOND_LOCATION);
            int distance = Integer.parseInt(matcher.group(GROUP_DISTANCE));

            graph.addVertex(firstLocation);
            graph.addVertex(secondLocation);

            DefaultWeightedEdge edge = graph.addEdge(firstLocation, secondLocation);
            graph.setEdgeWeight(edge, distance);
        });

        AStarShortestPath<String, DefaultWeightedEdge> aStarShortestPath = new AStarShortestPath<>(graph,
                                                                                                   new ALTAdmissibleHeuristic<>(
                                                                                                           graph,
                                                                                                           graph.vertexSet()));

        GraphPath<String, DefaultWeightedEdge> path = aStarShortestPath.getPath("Faerun", "Tambi");
        System.out.println(path);
        System.out.println(path.getWeight());

        System.out.println(GraphTests.isComplete(graph));
        System.out.println(GraphTests.hasOreProperty(graph));

        GraphPath<String, DefaultWeightedEdge> tour = new PalmerHamiltonianCycle<String, DefaultWeightedEdge>().getTour(
                graph);

        System.out.println(tour);
        System.out.println(tour.getWeight());

        DefaultWeightedEdge lastEdge = tour.getEdgeList().getLast();
        System.out.println(lastEdge);

        double lastEdgeWeight = graph.getEdgeWeight(lastEdge);
        System.out.println(lastEdgeWeight);

        return (int) (tour.getWeight() - lastEdgeWeight);
    }

    @DisableTest
    @Override
    public Integer getFirstStarSolution() {
        return 0;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 0;
    }
}
