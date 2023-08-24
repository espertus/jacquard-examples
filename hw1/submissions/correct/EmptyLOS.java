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
        return "";
    }
}
