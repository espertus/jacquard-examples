package buggy;

import student.StudentBuggyTests;

import java.util.*;

public class ArrayListOfString extends correct.ArrayListOfString {
    public ArrayListOfString() {
        super();
    }

    public ArrayListOfString(int initialCapacity) {
        super(initialCapacity);
    }

    private String removeSymbols(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String removeRepeatedCharacters(String s) {
        Set<Character> chars = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            chars.add(s.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public int countLetteryStrings() {
        int version = StudentBuggyTests.getVersion();
        return countLetteryStrings(version);
    }

    public boolean isLettery(String s, int version) {
        return switch (version) {
            case 1 -> true;
            case 2 -> false;
            case 3 -> s.isEmpty() || isLettery(s);
            case 4 -> isLettery(removeRepeatedCharacters(s));
            case 5 -> s.length() % 2 == 1;
            case 6 -> s.length() % 2 == 0 && isLettery(s);
            case 7 -> s.length() % 2 == 1 && isLettery(s);
            case 8 -> isLettery(s.replaceAll("[A-Z]", ""));
            case 9 -> isLettery(s.replaceAll("[a-z]", ""));
            case 10 -> isLettery(removeSymbols(s));
            // ignore first character
            case 11 ->
                    isLettery(s.isEmpty() ? "" : s.substring(0, s.length() - 1));
            // ignore last character
            case 12 -> isLettery(s.isEmpty() ? "" : s.substring(1));
            // convert digits to letters
            case 13 -> isLettery(s.replaceAll("[0-9]", "A"));
            // ignore digits
            case 14 -> isLettery(s.replaceAll("[0-9]", ""));
            default -> isLettery(s);
        };
    }

    public int countLetteryStrings(int version) {
        int count = 0;
        switch (version) {
            case 15:
                // never return 0
                return Math.min(countLetteryStrings(), 1);
            case 16:
                // remove first string
                if (size > 0) {
                    removeFirst(get(0));
                }
                return countLetteryStrings();
            case 17:
                // remove last string
                if (size > 0) {
                    removeFirst(get(size - 1));
                }
                return countLetteryStrings();
            case 18:
                // consider only first string
                if (size > 0 && isLettery(get(0))) {
                    return 1;
                } else {
                    return 0;
                }
            case 19:
                // consider all strings in array, even if removed
                for (int i = 0; i < getCapacity(); i++) {
                    String s = get(i);
                    if (s != null && isLettery(s)) {
                        count++;
                    }
                }
                return count;
            case 20:
                // consider only removed strings
                for (int i = size(); i < getCapacity(); i++) {
                    String s = get(i);
                    if (s != null && isLettery(s)) {
                        count++;
                    }
                }
                return count;
            default:
                for (int i = 0; i < size(); i++) {
                    if (isLettery(get(i), version)) {
                        count++;
                    }
                }
                return count;
        }
    }
}
