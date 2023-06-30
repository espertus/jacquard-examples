package student;

import com.github.javaparser.ast.Modifier;
import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.publisher.GradescopePublisher;
import com.spertus.jacquard.syntaxgrader.FieldModifierChecker;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final List<Result> results = new ArrayList<>();
        final Target mobJava = Target.fromPathString("src/main/java/student/Mob.java");

        // Run unit tests.
        JUnitTester runner = new JUnitTester(OriginalMobTest.class, MobTest.class);
        results.addAll(runner.run());

        // Run checkstyle.
        CheckstyleGrader checkstyleGrader = new CheckstyleGrader("config/checks.xml", 1.0, 10.0);
        results.addAll(checkstyleGrader.grade(mobJava));

        // Ensure that immutable properties are declared private final.
        FieldModifierChecker fmChecker = new FieldModifierChecker(
                "private final checker",
                5.0,
                List.of("type", "maxHearts", "behavior", "minDamage", "maxDamage"),
                List.of(Modifier.privateModifier(), Modifier.finalModifier()),
                List.of());
        results.addAll(fmChecker.grade(mobJava));

        new GradescopePublisher().displayResults(results);
    }
}
