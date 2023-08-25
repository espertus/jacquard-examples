package student;

import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.coverage.*;
import com.spertus.jacquard.crosstester.CrossTester;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.pmdgrader.PmdGrader;
import com.spertus.jacquard.publisher.GradescopePublisher;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Autograder.init();

        final Target emptyLosTarget = Target.fromClass(EmptyLOS.class);
        final Target nonEmptyLosTarget = Target.fromClass(NonEmptyLOS.class);
        final Target testTarget = Target.fromClass(ILOSTest.class);

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
        PmdGrader pmdGrader = PmdGrader.createFromRules(
                1.0,
                10.0,
                "category/java/bestpractices.xml");
        // As with checkstyle, calling this once gives a maximum score of 10,
        // not 30. For a maximum score of 30, call it 3 times, each with one
        // target.
        results.addAll(pmdGrader.grade(List.of(emptyLosTarget, nonEmptyLosTarget, testTarget)));

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
