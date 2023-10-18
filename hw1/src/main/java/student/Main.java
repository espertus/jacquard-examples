package student;

import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.coverage.*;
import com.spertus.jacquard.crosstester.CrossTester;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.pmdgrader.PmdGrader;
import com.spertus.jacquard.publisher.GradescopePublisher;

import java.util.*;

import static java.lang.System.exit;

// You need to run this with the ./test_autograder.sh script instead of from
// within the IDE, unless you remove the use of the CrossTester.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        // Change default configuration.
        //Autograder.init();
        Autograder.Builder.getInstance()
                // Tests should timeout after 5,000 ms (5 sec), not 10,000 ms.
                .timeout(5000L)
                // Parse student code as Java 11, not 17.
                .javaLevel(11)
                // Hide test results until the due date.
                .visibility(Visibility.AFTER_DUE_DATE)
                .build();

        final Target emptyLosTarget = Target.fromClass(EmptyLOS.class);
        final Target nonEmptyLosTarget = Target.fromClass(NonEmptyLOS.class);
        final Target testTarget = Target.fromClass(ILOSTest.class);
        final List<Target> allTargets =
                List.of(emptyLosTarget, nonEmptyLosTarget, testTarget);

        // Create and run checkstyle graders on implementation files (10 points).
        CheckstyleGrader checkstyleGrader = new CheckstyleGrader(
                "config/checkstyle-rules.xml",
                1.0,
                10.0);
        // Each time checkstyleGrader.grade() is called, the maximum score is 10,
        // even if it runs on multiple files. If you want each file to have a
        // maximum score of 10, call grade() twice, once for each target.
        List<Result> results = checkstyleGrader.grade(emptyLosTarget, nonEmptyLosTarget);

        // Create and run PMD grader on all targets, including tests (10 points).
        // Make the results immediately visible.
        PmdGrader pmdGrader = PmdGrader.createFromRules(
                1.0,
                10.0,
                "category/java/bestpractices.xml");
        List<Result> pmdResults = pmdGrader.grade(allTargets);
        Result.changeVisibility(pmdResults, Visibility.VISIBLE);
        results.addAll(pmdResults);

        // Measure code coverage on EmptyLOS (2 points).
        // The score will be P * 2.0, where P is the percent of lines covered by
        // the test, because LinearLineScorer was used.
        CodeCoverageTester emptyCodeCoverageTester =
                new CodeCoverageTester(
                        new LinearLineScorer(2.0),
                        EmptyLOS.class,
                        ILOSTest.class);
        results.addAll(emptyCodeCoverageTester.run());

        // Measure code coverage on NonEmptyLOS (8 points).
        CodeCoverageTester nonEmptyCodeCoverageTester =
                new CodeCoverageTester(
                        new LinearLineScorer(8.0),
                        NonEmptyLOS.class,
                        ILOSTest.class);
        results.addAll(nonEmptyCodeCoverageTester.run());

        // Run hidden tests on student code (20 points).
        // Visibility is specified through the @GradedTest annotation.
        JUnitTester studentCodeTester = new JUnitTester(HiddenILOSTest.class);
        results.addAll(studentCodeTester.run());

        // Create CrossTester to run student tests on:
        // * student code (20 points)
        // * hidden correct implementation (15 points)
        // * hidden buggy implementation (15 points)
        // Grading detail is in student-tests.csv.
        CrossTester crossTester = new CrossTester(
                student.ILOSTest.class,
                "student-tests.csv");
        results.addAll(crossTester.run());

        // Display the results.
        new GradescopePublisher().displayResults(results);

        // Explicitly exit (required by Gradle).
        exit(0);
    }
}
