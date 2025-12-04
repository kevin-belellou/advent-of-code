package com.belellou.kevin.advent.year2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.belellou.kevin.advent.generic.AbstractDaySolver;

@SuppressWarnings("unused")
public class Day8 extends AbstractDaySolver<Integer, Integer> {

    private static List<Node> parseNodesFromInput(BufferedReader reader) throws IOException {
        List<Integer> entries = Arrays.stream(reader.readLine()
                                                    .split(" "))
                                      .map(Integer::parseInt)
                                      .toList();

        List<Node> nodes = new ArrayList<>();
        int index = 0;

        while (index < entries.size() - 1) {
            index = readNode(nodes, entries, index, null);
        }
        return nodes;
    }

    private static int readNode(List<Node> nodes, List<Integer> entries, int index, Node parentNode) {
        int lastIndexUsed = index + 1;

        Node node = new Node(entries.get(index), entries.get(lastIndexUsed));
        nodes.add(node);

        if (parentNode != null) {
            parentNode.children.add(node);
        }

        for (int i = 0; i < node.childrenCount; i++) {
            lastIndexUsed = readNode(nodes, entries, lastIndexUsed + 1, node);
        }

        for (int i = 0; i < node.metadataCount; i++) {
            node.metadata.add(entries.get(++lastIndexUsed));
        }

        return lastIndexUsed;
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) throws IOException {
        List<Node> nodes = parseNodesFromInput(reader);

        return nodes.stream()
                    .mapToInt(Node::getMetadataSum)
                    .sum();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 49_180;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) throws IOException {
        List<Node> nodes = parseNodesFromInput(reader);

        return nodes.stream()
                    .findFirst()
                    .orElseThrow()
                    .getValue();
    }

    @Override
    public Integer getSecondStarSolution() {
        return 20_611;
    }

    private record Node(int childrenCount, int metadataCount, List<Node> children, List<Integer> metadata) {

        public Node(int childrenCount, int metadataCount) {
            this(childrenCount, metadataCount, new ArrayList<>(childrenCount), new ArrayList<>(metadataCount));
        }

        public int getMetadataSum() {
            return metadata.stream()
                           .mapToInt(Integer::intValue)
                           .sum();
        }

        public int getValue() {
            if (childrenCount == 0) {
                return getMetadataSum();
            }

            return metadata.stream()
                           .filter(metadata -> metadata > 0 && metadata <= children.size())
                           .map(metadata -> metadata - 1)
                           .map(children::get)
                           .mapToInt(Node::getValue)
                           .sum();

        }
    }
}
