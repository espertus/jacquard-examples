package student;

import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.pmdgrader.PmdGrader;
import com.spertus.jacquard.publisher.GradescopePublisher;
import com.spertus.jacquard.syntaxgrader.SyntaxConditionGrader;

import java.util.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        final List<Result> results = new ArrayList<>();
        Autograder.init();
        final Target target = Target.fromPathString("src/main/java/student/FavoritesIterator.java");

        // Run checkstyle.
        CheckstyleGrader checkstyleGrader = new CheckstyleGrader(
                "config/checkstyle-rules.xml",
                1.0,
                5.0);
        results.addAll(checkstyleGrader.grade(target));

        // Run PMD just to check for missing override annotations.
        PmdGrader pmdGrader = PmdGrader.createFromRules(
                1.0,
                5.0,
                "category/java/bestpractices.xml",
                "MissingOverride");
        results.addAll(pmdGrader.grade(target));

        // Make sure students did not use enhanced for loops.
        SyntaxConditionGrader forGrader = new SyntaxConditionGrader(
                0,
                "enhanced for-loop",
                5.0,
                node -> (node instanceof ForEachStmt)
        );
        results.addAll(forGrader.grade(target));

        // Make sure students did not call other implementations of
        // hasNext() or next()
        List<String> methodNames = List.of("hasNext", "next");
        SyntaxConditionGrader nextGrader = new SyntaxConditionGrader(
                0,
                "calls of other class's iterator methods",
                5.0,
                node -> {
                    // Check if method call is to next() or hasNext().
                    if (node instanceof MethodCallExpr methodCallExpr &&
                            methodNames.contains(methodCallExpr.getNameAsString())) {
                        // If so, flag if scope is explicit and not "this".
                        return methodCallExpr.getScope().isPresent() &&
                                !(methodCallExpr.getScope().get() instanceof ThisExpr);
                    }
                    return false;
                });
        results.addAll(nextGrader.grade(target));

        // Run unit tests.
        JUnitTester runner = new JUnitTester(HiddenFavoriteIteratorsTest.class, ProvidedFavoritesIteratorTest.class);
        List<Result> junitResults = runner.run();
        results.addAll(junitResults);

        // Display the results.
        new GradescopePublisher().displayResults(results);

        exit(0); // required by gradle
    }
}
