package student;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ILOSTest {
    final EmptyLOS emptyList = new EmptyLOS();
    final ILOS list1 = buildListOfString("one");
    final ILOS list2 = buildListOfString("one", "two");
    final ILOS list3 = buildListOfString("one", "two", "three");

    // helper method
    private ILOS buildListOfString(String... strings) {
        ILOS sofar = new EmptyLOS();
        for (int i = strings.length - 1; i >= 0; i--) {
            sofar = new NonEmptyLOS(strings[i], sofar);
        }
        return sofar;
    }

    // sample test provided to students
    @Test
    public void sizeEmptyListHasSizeZero() {
        assertEquals(0, emptyList.size());
    }
}
