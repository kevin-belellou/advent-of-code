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

/**
 * Test class for dynamically testing all DaySolver implementations.
 * This class scans the classpath for all classes implementing the DaySolver interface
 * and creates dynamic tests for both the first and second star solutions.
 * Tests can be filtered by year and day using environment variables.
 */
public class DaySolverTest {

    private static final String METHOD_GET_FIRST_STAR_SOLUTION = "getFirstStarSolution";
    private static final String METHOD_GET_SECOND_STAR_SOLUTION = "getSecondStarSolution";

    private static final String FIRST_SOLUTION = " - First solution";
    private static final String SECOND_SOLUTION = " - Second solution";

    private static final String MESSAGE_TEST_DISABLED = "Test disabled";

    private static final String FLAG_ENABLE_ONLY_YEAR = "ENABLE_ONLY_YEAR";
    private static final String FLAG_ENABLE_ONLY_DAY = "ENABLE_ONLY_DAY";

    private static final String PREFIX_YEAR = "year";
    private static final String PREFIX_DAY = "Day";

    /**
     * Creates dynamic tests for a DaySolver class.
     * Instantiates the solver and creates two dynamic tests: one for the first star and one for the second star.
     * Tests are only created if they are not disabled via the {@link DisableTest} annotation.
     *
     * @param clazz    the DaySolver class to create tests for
     * @param consumer the consumer that receives the created DynamicTest instances
     */
    private static void createDynamicTest(Class<?> clazz, Consumer<DynamicTest> consumer) {
        try {
            DaySolver<?, ?> daySolver = clazz.asSubclass(DaySolver.class).getDeclaredConstructor().newInstance();
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

    /**
     * Creates a single dynamic test for a specific star solution.
     *
     * @param daySolver     the DaySolver instance to test
     * @param firstSolution true for the first star solution, false for the second star solution
     * @param enabled       whether the test is enabled or should be skipped
     *
     * @return a DynamicTest instance that will verify the solver's output matches the expected solution
     */
    private static DynamicTest dynamicTestOf(DaySolver<?, ?> daySolver, boolean firstSolution, boolean enabled) {
        return DynamicTest.dynamicTest(daySolver + (firstSolution ? FIRST_SOLUTION : SECOND_SOLUTION),
                                       firstSolution ? () -> doTest(enabled, daySolver::solveFirstStar,
                                                                    daySolver::getFirstStarSolution)
                                                     : () -> doTest(enabled, daySolver::solveSecondStar,
                                                                    daySolver::getSecondStarSolution)

        );
    }

    /**
     * Executes a test by comparing the solver's output with the expected result.
     * The test is skipped if not enabled.
     *
     * @param enabled whether the test should be executed
     * @param method  supplier that executes the solver method
     * @param result  supplier that provides the expected solution
     */
    private static void doTest(boolean enabled, Supplier<?> method, Supplier<?> result) {
        Assumptions.assumeTrue(enabled, MESSAGE_TEST_DISABLED);
        assertThat(method.get()).isEqualTo(result.get());
    }

    /**
     * Creates a filter predicate for year-based test filtering.
     * If the ENABLE_ONLY_YEAR environment variable is set, only solvers from that year will be tested.
     *
     * @return a predicate that filters classes based on the year in their package name
     */
    private static Predicate<Class<?>> getYearFilter() {
        String year = System.getenv(FLAG_ENABLE_ONLY_YEAR);

        return year == null ? _ -> true : clazz -> clazz.getPackageName().endsWith(PREFIX_YEAR + year);
    }

    /**
     * Creates a filter predicate for day-based test filtering.
     * If the ENABLE_ONLY_DAY environment variable is set, only solvers for that day will be tested.
     *
     * @return a predicate that filters classes based on the day in their simple name
     */
    private static Predicate<Class<?>> getDayFilter() {
        String day = System.getenv(FLAG_ENABLE_ONLY_DAY);

        return day == null ? _ -> true : clazz -> clazz.getSimpleName().equals(PREFIX_DAY + day);
    }

    /**
     * Creates dynamic tests for all DaySolver implementations found in the classpath.
     * Scans for all classes implementing DaySolver, filters them based on environment variables,
     * and creates dynamic tests for both star solutions of each solver.
     *
     * @return a list of DynamicTest instances for all discovered and filtered solvers
     */
    @TestFactory
    List<DynamicTest> testDaySolvers() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(DaySolver.class));

        Set<BeanDefinition> components = provider.findCandidateComponents(getClass().getPackageName());

        return components.stream()
                         .map(BeanDefinition::getBeanClassName)
                         .map(ThrowingFunction.unchecked(Class::forName))
                         .filter(getYearFilter())
                         .filter(getDayFilter())
                         .sorted(new NaturalOrderComparator())
                         .mapMulti(DaySolverTest::createDynamicTest)
                         .toList();
    }
}
