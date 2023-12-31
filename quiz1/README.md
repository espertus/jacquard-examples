# Jacquard Quiz 1 Example

This is an example of a [Jacquard](https://github.com/espertus/jacquard)
autograder for a quiz. See the [parent directory](../README.md) for a
video overview and instructions.

## Teacher Instructions

### Software Requirements

* Gradle 8.0 or higher
* Python 3 if you want to test locally by executing `test_autograder.py`
  (optional)
* bash (included on OS X and Linux)

For bash on Windows, we use and
recommend [Git for Windows](https://gitforwindows.org/) 2.41 or higher.

### Files

These directories and files have code specific to the assignment:

* `config/checkstyle-rules.xml` holds the checkstyle rules file
* `src/main/java/student` contains
    * `Main.java`, which has the `main` method that controls the autograder
    * `FavoritesIterator.java`, placeholder for student code for testing
      the autograder locally
    * `HiddenFavoriteIteratorsTest.java` and `ProvidedFavoritesIteratorTest`,
      which contain JUnit 5 tests of student code
* `submission` holds a sample submission (required if you want to run
  `test_autograder.py` locally)
* `submissions` (which is not required) holds sample submissions to manually
  test the grader on Gradescope

Any of the above files could have different names or packages.

#### config.ini

The submission package and files are specified in `config.ini` and should be
edited if you change the package name or required files. Currently, package
names must have only a single part (e.g., "student", not "edu.myschool.student").

```
[submission]
package = student
files = [FavoritesIterator.java]
```
The list of files is comma-separated, with optional whitespace.

#### build.gradle

The main class of the autograder is specified in `build.gradle`:

```groovy
ext {
    javaMainClass = "student.Main"
}
```

You will need to change it if you use a different package/class name for
your main autograder class.

You are free to make other additions to `build.gradle`, such as adding
dependencies.

### Shell scripts

#### test_autograder.sh

This tests the autograder on the file(s) in the `submission` subdirectory.
It requires a Python 3 interpreter.

#### make_autograder.sh

This creates the zip file for you to upload to Gradescope.

### Uploading to Gradescope

#### Zip file

To create a zip file, run `./make_autograder.sh` from the command line.

To configure the autograder on Gradescope:

1. Click on "Configure Autograder" in the left sidebar.
2. Select "Zip file upload".
3. Click on "Replace Autograder (.zip)".
4. Select:
    * Base Image OS: Ubuntu
    * Base Image Version: 22.04
    * Base Image Variant: JDK 17
5. Click on "Update Autograder". (You may have to wait up to a minute for
   anything to happen. The button will go gray when the build begins.)
6. Wait for the "Built as of" time to be updated.

![screenshot showing Zip file upload of autograder.zip with Ubuntu 22.04 and
JDK 17 selected](../images/configure-autograder.png)

## Student Instructions

### Setup

Create a new IntelliJ project with the below code,
and configure the Checkstyle plugin to use
[checkstyle-rules.xml](config/checkstyle-rules.xml).

### Requirements

You will need to implement the `Iterator<T>` interface
in the class `FavoritesIterator`.
You may not make use of any existing implementations of
`Iterator<T>` or `Iterable<T>` while doing so. Specifically,
you may not call any other implementations of `next()` or
`hasNext()` (just your own) and you may not use enhanced
for-loops (also called for-each loops), which call iterators
behind the scenes.

### Grading

Grading will be based on:

* the provided unit tests (20 points)
* hidden unit tests (40 points)
* checkstyle (5 points)
* PMD (5 points)
* meeting the requirements (30 points)

### Submission

When done, you should upload only the source file
`FavoritesIterator.java`.

### FavoritesIterator.java

```java
package student;

import java.util.*;

/**
 * Creates an iterator over a group of favorite items. The first item is
 * returned from the iterator 1 time, the second item is returned 2 times, etc.
 * with the nth item returned n times. The items may be returned in any order.
 *
 * @param <T> the type of item
 */
public class FavoritesIterator<T> implements Iterator<T> {

    /**
     * Constructs an iterator over the provided items. The iterator is not
     * affected by changes to the list after the constructor call so never
     * throws {@link ConcurrentModificationException}.
     *
     * @param items the items to iterate over
     */
    public FavoritesIterator(List<T> items) {
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }
}
```

### FavoritesIteratorTest

```java
package student;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FavoritesIteratorTest {
    List<String> favoriteHotSauces0 = new ArrayList<>(); // I don't like hot sauce.
    List<Integer> favoriteYears1 = List.of(2010); // my daughter's birth
    List<String> favoriteCaliforniaCities2 =
            List.of("Oakland", "San Francisco");

    @Test
    public void iteratorOverEmptyList() {
        FavoritesIterator<String> iterator = new FavoritesIterator<>(favoriteHotSauces0);

        // No items should be returned.
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    public void iteratorOverOneElementList() {
        FavoritesIterator<Integer> iterator = new FavoritesIterator<>(favoriteYears1);

        // The one item in the list should be returned once.
        assertTrue(iterator.hasNext());
        assertEquals(2010, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iteratorOverTwoElementList() {
        FavoritesIterator<String> iterator = new FavoritesIterator<>(favoriteCaliforniaCities2);
        List<String> results = new LinkedList<>();
        while (iterator.hasNext()) {
            results.add(iterator.next());
        }

        // Three items should have been returned:
        // Oakland, San Francisco, San Francisco (in any order).
        assertEquals(3, results.size());

        // This tries to remove "Oakland" and returns true if successful.
        assertTrue(results.remove("Oakland"));
        assertTrue(results.remove("San Francisco"));
        assertTrue(results.remove("San Francisco"));
        assertEquals(0, results.size()); // redundant but harmless
    }
}
```
