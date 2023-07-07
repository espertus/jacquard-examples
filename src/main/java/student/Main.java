package student;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.publisher.GradescopePublisher;
import com.spertus.jacquard.syntaxgrader.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        final List<Result> results = new ArrayList<>();
        final Target mobJava = Target.fromPathString("src/main/java/student/Mob.java");

        // Run checkstyle.
        CheckstyleGrader checkstyleGrader = new CheckstyleGrader("config/checks.xml", 1.0, 5.0);
        results.addAll(checkstyleGrader.grade(mobJava));

        // Run unit tests.
        JUnitTester runner = new JUnitTester(OriginalMobTest.class, MobTest.class);
        List<Result> junitResults = runner.run();
        Result.changeVisibility(junitResults, Visibility.AFTER_DUE_DATE);
        results.addAll(junitResults);

        // Ensure that immutable properties are declared private final.
        FieldModifierChecker fmChecker = FieldModifierChecker.makeChecker(
                "private final checker",
                1.0,
                List.of("type", "maxHearts", "behavior", "minDamage", "maxDamage"),
                List.of(Modifier.privateModifier(), Modifier.finalModifier()),
                List.of(),
                true);
        results.addAll(fmChecker.grade(mobJava));

        // Ensure that instance variables have public getters.
        // This had been graded all or nothing. This grades more finely.
        // To avoid rounding weirdness, this doesn't check the 6th method,
        // getNumHearts().
        MethodModifierChecker mmChecker = MethodModifierChecker.makeChecker(
                "public getter checker",
                1.0,
                List.of("getType", "getMaxHearts", "getBehavior", "getMinDamage", "getMaxDamage"),
                List.of(Modifier.publicModifier()),
                List.of(Modifier.finalModifier()),
        true);
        results.addAll(mmChecker.grade(mobJava));

        // Ensure that toString() was implemented correctly.
        results.add(performToStringTests());

        // Display the results.
        new GradescopePublisher().displayResults(results);
    }
}
