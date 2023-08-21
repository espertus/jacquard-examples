package student;

/**
 * An immutable non-empty list of Strings.
 */
public class NonEmptyListOfString implements IListOfString {
    private final String first;
    private final IListOfString rest;

    public NonEmptyListOfString(String first, IListOfString rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public int size() {
        return 1 + rest.size();
    }

    @Override
    public String get(int index) {
        if (index == 0) {
            return first;
        } else {
            return rest.get(index - 1);
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