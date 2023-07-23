package student;

import com.spertus.jacquard.checkstylegrader.CheckstyleGrader;
import com.spertus.jacquard.common.*;
import com.spertus.jacquard.junittester.JUnitTester;
import com.spertus.jacquard.publisher.GradescopePublisher;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final List<Result> results = new ArrayList<>();
        Autograder.init();

        // Run checkstyle.
        CheckstyleGrader checkstyleGrader = new CheckstyleGrader("config/checkstyle-rules.xml", 1.0, 5.0);
        final Target target = Target.fromPathString("src/main/java/student/FavoritesIterator.java");
        results.addAll(checkstyleGrader.grade(target));

        // Run unit tests.
        JUnitTester runner = new JUnitTester(HiddenFavoriteIteratorsTest.class, ProvidedFavoritesIteratorTest.class);
        List<Result> junitResults = runner.run();
        results.addAll(junitResults);

        // Display the results.
        new GradescopePublisher().displayResults(results);
    }
}
