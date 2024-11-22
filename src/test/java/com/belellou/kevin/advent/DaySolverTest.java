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

    private static final String FIRST_SOLUTION = " - First solution";
    private static final String SECOND_SOLUTION = " - Second solution";

    private static final String MESSAGE_TEST_DISABLED = "Test disabled";

    private static void createDynamicTest(BeanDefinition component, Consumer<DynamicTest> consumer) {
        try {
            Class<?> clazz = Class.forName(component.getBeanClassName());
            DaySolver daySolver = (DaySolver) clazz.getDeclaredConstructor().newInstance();
            DisableTest disableTestAnnotation = clazz.getAnnotation(DisableTest.class);

            consumer.accept(dynamicTestOf(daySolver, true, disableTestAnnotation != null));
            consumer.accept(dynamicTestOf(daySolver, false, disableTestAnnotation != null));
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static DynamicTest dynamicTestOf(DaySolver daySolver, boolean firstSolution, boolean disabled) {
        return DynamicTest.dynamicTest(daySolver + (firstSolution ? FIRST_SOLUTION : SECOND_SOLUTION),
                                       firstSolution
                                       ? () -> doTest(disabled, daySolver::solveFirstStar,
                                                      daySolver::getFirstStarSolution)
                                       : () -> doTest(disabled, daySolver::solveSecondStar,
                                                      daySolver::getSecondStarSolution)

        );
    }

    private static void doTest(boolean disabled, IntSupplier method, IntSupplier result) {
        Assumptions.assumeFalse(disabled, MESSAGE_TEST_DISABLED);
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
