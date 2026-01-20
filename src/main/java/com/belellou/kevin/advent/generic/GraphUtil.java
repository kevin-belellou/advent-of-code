package com.belellou.kevin.advent.generic;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.stream.Collectors;

import com.pivovarit.function.ThrowingFunction;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.HamiltonianCycleAlgorithm;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * Utility class for graph operations, particularly for finding the shortest Hamiltonian cycle
 * in a graph using multiple algorithms from the JGraphT library.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class GraphUtil<V, E> {

    private static final String PACKAGE_ORG_JGRAPHT_ALG_TOUR = "org.jgrapht.alg.tour";

    private final Set<HamiltonianCycleAlgorithm<V, E>> implementations;

    /**
     * Constructs a GraphUtil instance by discovering and instantiating all available
     * HamiltonianCycleAlgorithm implementations from the JGraphT tour package.
     * Uses classpath scanning to dynamically load all algorithm implementations.
     */
    public GraphUtil() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(HamiltonianCycleAlgorithm.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(PACKAGE_ORG_JGRAPHT_ALG_TOUR);

        //noinspection unchecked
        implementations = components.stream()
                                    .map(BeanDefinition::getBeanClassName)
                                    .map(ThrowingFunction.unchecked(Class::forName))
                                    .map(clazz -> (Class<? extends HamiltonianCycleAlgorithm<V, E>>) clazz.asSubclass(
                                            HamiltonianCycleAlgorithm.class))
                                    .map(ThrowingFunction.unchecked(Class::getDeclaredConstructor))
                                    .map(ThrowingFunction.unchecked(Constructor::newInstance))
                                    .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Finds the shortest Hamiltonian cycle in the given graph by running all available
     * Hamiltonian cycle algorithms and selecting the one with minimum weight.
     *
     * @param graph the graph in which to find the shortest Hamiltonian cycle
     *
     * @return the shortest Hamiltonian cycle as a GraphPath
     *
     * @throws java.util.NoSuchElementException if no Hamiltonian cycle exists
     */
    public GraphPath<V, E> getShortestHamiltonianCycle(Graph<V, E> graph) {
        return implementations.stream()
                              .map(implementation -> implementation.getTour(graph))
                              .reduce((a, b) -> a.getWeight() < b.getWeight() ? a : b)
                              .orElseThrow();
    }
}
