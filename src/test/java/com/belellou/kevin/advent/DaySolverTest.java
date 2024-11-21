package com.belellou.kevin.advent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.belellou.kevin.advent.generic.DaySolver;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySolverTest {

    private static final String FIRST_SOLUTION = " - First solution";
    private static final String SECOND_SOLUTION = " - Second solution";

    private static void createDynamicTest(BeanDefinition component, Consumer<DynamicTest> consumer) {
        try {
            Class<?> clazz = Class.forName(component.getBeanClassName());
            DaySolver daySolver = (DaySolver) clazz.getDeclaredConstructor().newInstance();

            consumer.accept(dynamicTestOf(daySolver, true));
            consumer.accept(dynamicTestOf(daySolver, false));
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static DynamicTest dynamicTestOf(DaySolver daySolver, boolean firstSolution) {
        return DynamicTest.dynamicTest(daySolver + (firstSolution ? FIRST_SOLUTION : SECOND_SOLUTION),
                                       firstSolution
                                       ? () -> assertThat(daySolver.solveFirstStar())
                                               .isEqualTo(daySolver.getFirstStarSolution())
                                       : () -> assertThat(daySolver.solveSecondStar())
                                               .isEqualTo(daySolver.getSecondStarSolution())
        );
    }

    @TestFactory
    List<DynamicTest> testDaySolvers() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(DaySolver.class));

        Set<BeanDefinition> components = provider.findCandidateComponents(getClass().getPackageName());
        return components.stream().mapMulti(DaySolverTest::createDynamicTest).toList();
    }
}
