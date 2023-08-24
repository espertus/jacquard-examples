package student;

/**
 * An immutable list of zero or more strings.
 */
public interface ILOS {
    /**
     * Returns the number of strings in this list.
     *
     * @return the number of strings in this list
     */
    int size();

    /**
     * Concatenates the strings in this list. If the list is empty,
     * the empty string is returned.
     *
     * @return a concatenation of the strings in this list
     */
    String concat();
}
