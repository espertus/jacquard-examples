package student;

import newgrader.common.Result;
import newgrader.junittester.JUnitTester;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        JUnitTester runner = new JUnitTester(OriginalMobTest.class);
        // JUnitTester runner = runner.runTestClasses(OriginalMobTest.class, MobTest.class);
        // JUnitTester = runner.runTestPackage("student");
        List<Result> results = runner.run();
        System.out.println(results);
    }
}
