package student;

import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.coverage.*;
import com.spertus.jacquard.pmdgrader.PmdGrader;
import com.spertus.jacquard.publisher.GradescopePublisher;

import java.util.*;

import static java.lang.System.exit;

public class Main {
    private static final String MAIN_DIR = "src/main/java/student/";
    private static final String EMPTY_FILE = "EmptyListOfString.java";
    private static final String NON_EMPTY_FILE = "NonEmptyListOfString.java";
    private static final String TEST_FILE = "IListOfStringTest.java";

    // Divide assignments of ten points between the two implementation classes.
    private static final double EMPTY_POINTS = 2.0; // EmptyListOfString.java
    private static final double NON_EMPTY_POINTS = 8.0; // NonEmptyListOfString.java

    public static void main(String[] args) {
        Autograder.init();

        final Target emptyLosTarget = Target.fromPathString(MAIN_DIR + EMPTY_FILE);
        final Target nonEmptyLosTarget = Target.fromPathString(MAIN_DIR + NON_EMPTY_FILE);
        final Target testTarget = Target.fromPathString(MAIN_DIR + TEST_FILE);

        // Create and run checkstyle graders on implementation files.
        CheckstyleGrader checkstyleGrader = new CheckstyleGrader(
                "config/checkstyle-rules.xml",
                1.0,
                10.0);
        List<Result> results = checkstyleGrader.grade(emptyLosTarget, nonEmptyLosTarget);

        // Create and run PMD grader on all targets, including tests.
        PmdGrader pmdGrader = PmdGrader.createFromRules(
                1.0,
                10.0,
                "category/java/bestpractices.xml");
        results.addAll(pmdGrader.grade(testTarget));

        // Measure code coverage on EmptyListOfString.
        CodeCoverageTester emptyCodeCoverageTester =
                new CodeCoverageTester(
                        new LinearLineScorer(EMPTY_POINTS),
                        EmptyListOfString.class,
                        IListOfStringTest.class);
        results.addAll(emptyCodeCoverageTester.run());

        // Measure code coverage on NonEmptyListOfString.
        CodeCoverageTester nonEmptyCodeCoverageTester =
                new CodeCoverageTester(
                        new LinearLineScorer(NON_EMPTY_POINTS),
                        NonEmptyListOfString.class,
                        IListOfStringTest.class);
        results.addAll(nonEmptyCodeCoverageTester.run());

        // Display the results.
        new GradescopePublisher().displayResults(results);

        // Explicitly exit (required by Gradle).
        exit(0);
    }
}
