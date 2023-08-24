package buggy;

import student.ILOS;

/**
 * An immutable empty list of Strings.
 */
public class EmptyLOS implements ILOS {
    /**
     * Constructs an empty list of strings.
     */
    public EmptyLOS() {
    }

    @Override
    public int size() {
        return Integer.MIN_VALUE; // intentional bug
    }

    @Override
    public String concat() {
        return null; // intentional bug
    }
}
