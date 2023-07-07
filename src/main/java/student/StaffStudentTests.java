package student;

import com.spertus.jacquard.common.Visibility;
import com.spertus.jacquard.junittester.GradedTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffStudentTests {
    AbstractArrayListOfString listEmpty;
    AbstractArrayListOfString listAB;

    private AbstractArrayListOfString buildList(String... strings) {
        AbstractArrayListOfString result = new ArrayListOfString(strings.length);
        for (int i = 0; i < strings.length; i++) {
            result.add(strings[i]);
        }
        return result;
    }

    @BeforeEach
    public void setup() {
        listEmpty = buildList();
        listAB = buildList("A", "B");
    }

    private void test1True(String... strings) {
        for (String s : strings) {
            assertEquals(1, buildList(s).countLetteryStrings());
        }
    }

    private void testAllFalse(String... strings) {
        for (String s : strings) {
            assertEquals(0, buildList(s).countLetteryStrings());
        }
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsLowerCaseLetters()", points = 3.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsLowerCaseLetters() {
        test1True("noway!!!!", "!no!way#");
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsEmptyList()", points = 2.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsEmptyList() {
        assertEquals(0, buildList().countLetteryStrings());
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsEmptyString()", points = 2.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsEmptyString() {
        testAllFalse("");
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsCapitalLetters()", points = 3.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsCapitalLetters() {
        test1True("HEY", "HEY!!", "H!EY!", "!!HEY");
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsHalfLetters()", points = 3.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsHalfLetters() {
        testAllFalse("@@ab", "@@AB", "ab#$%C");
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsRemovedStrings()", points = 3.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsAllCharacters() {
        String s1 = "0123456789!@#$%^&*()";
        String s2 = "AAAAAAAAAAAAAAAAAAZZ";
        testAllFalse(s1 + s2);
        test1True(s1 + s2 + "B");
    }

    @Test
    @GradedTest(name = "Student code passes autograder test countLetteryStringsRemovedStrings()", points = 4.0, visibility = Visibility.AFTER_PUBLISHED)
    public void countLetteryStringsRemovedStrings() {
        ArrayListOfString list = buildList("abc123", "abcd14");
        list.removeFirst("abcd14");
        assertEquals(0, list.countLetteryStrings());
    }
}
