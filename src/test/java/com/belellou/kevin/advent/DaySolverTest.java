package com.belellou.kevin.advent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntSupplier;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.belellou.kevin.advent.generic.DaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySolverTest {

    private static final String METHOD_GET_FIRST_STAR_SOLUTION = "getFirstStarSolution";
    private static final String METHOD_GET_SECOND_STAR_SOLUTION = "getSecondStarSolution";

    private static final String FIRST_SOLUTION = " - First solution";
    private static final String SECOND_SOLUTION = " - Second solution";

    private static final String MESSAGE_TEST_DISABLED = "Test disabled";

    private static void createDynamicTest(BeanDefinition component, Consumer<DynamicTest> consumer) {
        try {
            Class<?> clazz = Class.forName(component.getBeanClassName());
            DaySolver daySolver = (DaySolver) clazz.getDeclaredConstructor().newInstance();
            boolean firstTestEnabled = !clazz.getMethod(METHOD_GET_FIRST_STAR_SOLUTION)
                                             .isAnnotationPresent(DisableTest.class);
            boolean secondTestEnabled = !clazz.getMethod(METHOD_GET_SECOND_STAR_SOLUTION)
                                              .isAnnotationPresent(DisableTest.class);

            consumer.accept(dynamicTestOf(daySolver, true, firstTestEnabled));
            consumer.accept(dynamicTestOf(daySolver, false, secondTestEnabled));
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static DynamicTest dynamicTestOf(DaySolver daySolver, boolean firstSolution, boolean enabled) {
        return DynamicTest.dynamicTest(daySolver + (firstSolution ? FIRST_SOLUTION : SECOND_SOLUTION),
                                       firstSolution
                                       ? () -> doTest(enabled, daySolver::solveFirstStar,
                                                      daySolver::getFirstStarSolution)
                                       : () -> doTest(enabled, daySolver::solveSecondStar,
                                                      daySolver::getSecondStarSolution)

        );
    }

    private static void doTest(boolean enabled, IntSupplier method, IntSupplier result) {
        Assumptions.assumeTrue(enabled, MESSAGE_TEST_DISABLED);
        assertThat(method.getAsInt()).isEqualTo(result.getAsInt());
    }

    @TestFactory
    List<DynamicTest> testDaySolvers() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(DaySolver.class));

        Set<BeanDefinition> components = provider.findCandidateComponents(getClass().getPackageName());
        return components.stream().mapMulti(DaySolverTest::createDynamicTest).toList();
    }
}
