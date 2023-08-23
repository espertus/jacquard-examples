package student;

/**
 * An immutable empty list of Strings.
 */
public class EmptyListOfString implements IListOfString {
    /**
     * Constructs an empty list of strings.
     */
    public EmptyListOfString() {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String get(int n) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("Negative index to get() not permitted");
        } else {
            throw new IndexOutOfBoundsException("Index " + n + " is out of bounds");
        }
    }

    @Override
    public boolean allStartWith(char startLetter) {
        // for students to implement
        return false;
    }

    @Override
    public boolean isAlphabeticallyConsecutive() {
        // for students to implement
        return false;
    }
}
