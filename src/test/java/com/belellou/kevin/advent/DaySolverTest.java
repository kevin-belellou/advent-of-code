package com.belellou.kevin.advent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.pivovarit.function.ThrowingFunction;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.belellou.kevin.advent.generic.DaySolver;
import com.belellou.kevin.advent.generic.DisableTest;
import com.belellou.kevin.advent.generic.NaturalOrderComparator;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySolverTest {

    private static final String METHOD_GET_FIRST_STAR_SOLUTION = "getFirstStarSolution";
    private static final String METHOD_GET_SECOND_STAR_SOLUTION = "getSecondStarSolution";

    private static final String FIRST_SOLUTION = " - First solution";
    private static final String SECOND_SOLUTION = " - Second solution";

    private static final String MESSAGE_TEST_DISABLED = "Test disabled";

    private static void createDynamicTest(Class<?> clazz, Consumer<DynamicTest> consumer) {
        try {
            DaySolver<?> daySolver = clazz.asSubclass(DaySolver.class).getDeclaredConstructor().newInstance();
            boolean firstTestEnabled = !clazz.getMethod(METHOD_GET_FIRST_STAR_SOLUTION)
                                             .isAnnotationPresent(DisableTest.class);
            boolean secondTestEnabled = !clazz.getMethod(METHOD_GET_SECOND_STAR_SOLUTION)
                                              .isAnnotationPresent(DisableTest.class);

            consumer.accept(dynamicTestOf(daySolver, true, firstTestEnabled));
            consumer.accept(dynamicTestOf(daySolver, false, secondTestEnabled));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static DynamicTest dynamicTestOf(DaySolver<?> daySolver, boolean firstSolution, boolean enabled) {
        return DynamicTest.dynamicTest(daySolver + (firstSolution ? FIRST_SOLUTION : SECOND_SOLUTION),
                                       firstSolution ? () -> doTest(enabled, daySolver::solveFirstStar,
                                                                    daySolver::getFirstStarSolution)
                                                     : () -> doTest(enabled, daySolver::solveSecondStar,
                                                                    daySolver::getSecondStarSolution)

        );
    }

    private static void doTest(boolean enabled, Supplier<?> method, Supplier<?> result) {
        Assumptions.assumeTrue(enabled, MESSAGE_TEST_DISABLED);
        assertThat(method.get()).isEqualTo(result.get());
    }

    private static Predicate<Class<?>> getYearFilter() {
        String enableYear = System.getenv("ENABLE_ONLY_YEAR");

        return enableYear == null ? (aClass -> true) : (aClass -> aClass.getPackageName().endsWith(enableYear));
    }

    @TestFactory
    List<DynamicTest> testDaySolvers() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(DaySolver.class));

        Set<BeanDefinition> components = provider.findCandidateComponents(getClass().getPackageName());

        return components.stream()
                         .map(BeanDefinition::getBeanClassName)
                         .map(ThrowingFunction.unchecked(Class::forName))
                         .filter(getYearFilter())
                         .sorted(new NaturalOrderComparator())
                         .mapMulti(DaySolverTest::createDynamicTest)
                         .toList();
    }
}
