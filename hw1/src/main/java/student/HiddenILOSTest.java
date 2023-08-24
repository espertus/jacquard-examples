// These are the hidden tests of student code.

package student;

import com.spertus.jacquard.junittester.GradedTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class HiddenILOSTest {
    final EmptyLOS emptyList = new EmptyLOS();
    final ILOS list1 = buildListOfString("A");
    final ILOS list2 = buildListOfString("a", "b");
    final ILOS list3 = buildListOfString("A", "BB", "CC");
    ILOS list10;

    private ILOS buildListOfString(String... strings) {
        ILOS sofar = new EmptyLOS();
        for (int i = strings.length - 1; i >= 0; i--) {
            sofar = new NonEmptyLOS(strings[i], sofar);
        }
        return sofar;
    }

    @BeforeEach
    public void setup() {
        // Build up list of length 10.
        list10 = new EmptyLOS();
        for (int i = 9; i >= 0; i--) {
            list10 = new NonEmptyLOS(String.valueOf(i), list10);
        }
    }

    @Test
    @GradedTest(points = 1.0)
    public void sizeEmptyListHasSizeZero() {
        assertEquals(0, emptyList.size());
    }

    @Test
    @GradedTest // points can be omitted for default value 1.0
    public void sizeWorksOnLength1List() {
        assertEquals(1, list1.size());
    }

    @Test
    @GradedTest(points = 2.0)
    public void sizeWorksOnLength2List() {
        assertEquals(2, list2.size());
    }

    @Test
    @GradedTest(points = 3.0)
    public void sizeWorksOnLength3List() {
        assertEquals(3, list3.size());
    }

    @Test
    @GradedTest(points = 3.0)
    public void sizeWorksOnLongList() {
        assertEquals(3, list3.size());
    }

    @Test
    @GradedTest
    public void concatWorksWhenLength0() {
        assertEquals("", emptyList.concat());
    }

    @Test
    @GradedTest
    public void concatWorksWhenLength1() {
        assertEquals("one", list1.concat());
    }

    @Test
    @GradedTest(points = 2.0)
    public void concatWorksWhenLength2() {
        assertEquals("onetwo", list2.concat());
    }

    @Test
    @GradedTest(points = 3.0)
    public void concatWorksWhenLength3() {
        assertEquals("onetwothree", list3.concat());
    }

    @Test
    @GradedTest(points = 3.0)
    public void concatWorksWhenLengthLong() {
        assertEquals("0123456789", list10.concat());
    }
}
