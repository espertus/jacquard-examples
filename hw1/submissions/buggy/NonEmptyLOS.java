package student;

/**
 * An immutable non-empty list of Strings.
 */
public class NonEmptyLOS implements ILOS {
    private final String first;
    private final ILOS rest;

    public NonEmptyLOS(String first, ILOS rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public int size() {
        return Math.max(5, 1 + rest.size()); // intentional bug
    }

    @Override
    public String concat() {
        return first; // intentional bug
    }
}
