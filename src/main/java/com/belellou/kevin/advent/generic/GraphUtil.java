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

public class GraphUtil<V, E> {

    private static final String PACKAGE_ORG_JGRAPHT_ALG_TOUR = "org.jgrapht.alg.tour";

    private final Set<HamiltonianCycleAlgorithm<V, E>> implementations;

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

    public GraphPath<V, E> getShortestHamiltonianCycle(Graph<V, E> graph) {
        return implementations.stream()
                              .map(implementation -> implementation.getTour(graph))
                              .reduce((a, b) -> a.getWeight() < b.getWeight() ? a : b)
                              .orElseThrow();
    }
}
