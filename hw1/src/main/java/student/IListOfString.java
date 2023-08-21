package student;

/**
 * An immutable list of zero or more strings.
 */
public interface IListOfString {
    /**
     * Returns the number of strings in this list.
     *
     * @return the number of strings in this list
     */
    int size();

    /**
     * Returns the string at the specified index in this list.
     *
     * @param index the index of the string
     * @return the string at the specified position
     * @throws IllegalArgumentException  if index is less than zero
     * @throws IndexOutOfBoundsException if index is greater than or equal to
     *                                   the length of the list
     */
    String get(int index);

    /**
     * Checks whether every string in this list starts with the given
     * start letter.
     *
     * @param startLetter the start letter
     *
     * @return true if the list is empty or all lists in the string start
     * with the start letter, false otherwise
     */
    boolean allStartWith(char startLetter);

    /**
     * Checks whether the strings in this list start with consecutive letters
     * of the English alphabet (ignoring case). For example, the list with
     * elements "Hello", "I'm", "joyful" qualifies, while "amazing", "zebras"
     * does not. If any of the strings do not start with English letters, this
     * should return false.
     *
     * @return true if the strings start with consecutive letters of the English
     * alphabet, false otherwise.
     */
    boolean isAlphabeticallyConsecutive();
}