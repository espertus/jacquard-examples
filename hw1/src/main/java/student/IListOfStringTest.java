package student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IListOfStringTest {
    EmptyListOfString emptyList = new EmptyListOfString();
    NonEmptyListOfString list1 = new NonEmptyListOfString("one", emptyList);
    NonEmptyListOfString list2 = new NonEmptyListOfString("two", list1);
    NonEmptyListOfString list3 = new NonEmptyListOfString("three", list2);

    @Test
    public void sizeEmptyListHasSizeZero() {
        assertEquals(0, emptyList.size());
    }

    @Test
    public void sizeNonEmptyListSizeReturnsSize() {
        assertEquals(1, list1.size());
        assertEquals(2, list2.size());
        assertEquals(3, list3.size());
    }

    @Test
    public void getRejectsNegativeIndex() {
        assertThrows(IllegalArgumentException.class,
                () -> emptyList.get(-1));
        assertThrows(IllegalArgumentException.class,
                () -> list1.get(-5));
    }

    @Test
    public void getRejectsOutOfBoundsIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> emptyList.get(0));
        assertThrows(IndexOutOfBoundsException.class,
                () -> list1.get(1));
        assertThrows(IndexOutOfBoundsException.class,
                () -> list3.get(10));
    }

    @Test
    public void getReturnsRightString() {
        assertEquals("one", list1.get(0));
        assertEquals("two", list2.get(0));
        assertEquals("one", list3.get(2));
        assertEquals("two", list3.get(1));
        assertEquals("three", list3.get(0));
    }
}
