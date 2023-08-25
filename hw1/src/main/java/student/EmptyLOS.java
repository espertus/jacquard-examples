// This is a placeholder for student code.

package student;

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
        return 0;
    }

    @Override
    public String concat() {
        // for students to implement
        return "";
    }
}
