package student;

import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.publisher.GradescopePublisher;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        JUnitTester runner = new JUnitTester(OriginalMobTest.class);
        // JUnitTester runner = runner.runTestClasses(OriginalMobTest.class, MobTest.class);
        // JUnitTester = runner.runTestPackage("student");
        List<Result> results = runner.run();
        CheckstyleGrader grader = new CheckstyleGrader("config/checks.xml", 1.0, 10.0);
        results.addAll(grader.grade(Target.fromPathString("Mob.java")));
        new GradescopePublisher().displayResults(results);
    }
}
