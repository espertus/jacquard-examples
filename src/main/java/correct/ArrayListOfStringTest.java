// These will be replaced on the server but are useful for local debugging.
package correct;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayListOfStringTest {
    ArrayListOfString listEmpty;
    ArrayListOfString listAB;

    private ArrayListOfString buildList(String... strings) {
        ArrayListOfString result = new ArrayListOfString(strings.length);
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
            assertEquals(0, buildList(s).countLetteryStrings(), s);
        }
    }

    @Test
    public void countLetteryStringsEmptyList() {
        assertEquals(0, buildList().countLetteryStrings());
    }

    @Test
    public void countLetteryStringsEmptyString() {
        testAllFalse("");
    }

    @Test
    public void countLetteryStringsCapitalLetters() {
        test1True("HEY", "HEY!!", "H!EY!", "!!HEY");
    }

    @Test
    public void countLetteryStringsLowerCaseLetters() {
        test1True("noway!!!!", "!no!way#");
    }

    @Test
    public void countLetteryStringsHalfLetters() {
        testAllFalse("@@ab", "@@AB", "ab#$%C");
    }

    @Test
    public void countLetteryStringsAllCharacters() {
        String s1 = "0123456789!@#$%^&*()";
        String s2 = "AAAAAAAAAAAAAAAAAAZZ";
        testAllFalse(s1 + s2);
        test1True(s1 + s2 + "B");
    }

    @Test
    public void countLetteryStringsRemovedStrings() {
        ArrayListOfString list = buildList("abc123", "abcd14");
        list.removeFirst("abcd14");
        assertEquals(0, list.countLetteryStrings());
    }
}
