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
        return 0; // for students to implement
    }

    @Override
    public String concat() {
        return ""; // for students to implement
    }
}
