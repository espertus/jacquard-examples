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

You are provided with an interface `IListOfString` and need to complete and
test the concrete classes `EmptyListOfString` and `NonEmptyListOfString`.

Specifically, you need to implement and test the methods:
* `boolean allStartWith(char startLetter)`
* `boolean isAlphabeticallyConsecutive()`
The methods are described in the javadoc.

### Tests

You are encouraged to practice test-driven development (TDD) and write
your tests before implementing the methods under test. To earn credit,
your tests must:
* be in `IListOfStringTest.java`
* have the `@Test` annotation
* have the `public` visibility modifier
* start with the name of the method under test (such as `isAlphabeticallyConsecutiveWorksForLowercase`)
Use the existing tests as a model.

You should modify and submit these files:
* `IListOfString.java`
* `EmptyListOfString.java`
* `NonEmptyListOfString.java`

All of your tests must be annotated with `@Test` and have names that start
with the name of the method being tested, with the exact same capitalization.**
Use the provided tests as a model for names.

### Implementation

Replace the stubs of `allStartWith()` and `isAlphabeticallyConsecutive()`
in `EmptyListOfString.java` and `NonEmptyListOfString.java` with implementations.


### Grading 
Grading will be based on:
* checkstyle with configuration file [config/checkstyle-rules.xml](config/checkstyle-rules.xml) [10 points]
* PMD with [java/best_practices.xml](https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html) [10 points]
* Unit testing
  * Your implementation passes your tests [20 points]
  * Our hidden correct implementation passes your tests [10 points]
  * Our hidden buggy implementations fail your tests [20 points]
  * Your implementation passes our hidden tests [20 points]
* Code coverage [10 points]

Your code coverage score will be proportional to your line coverage.
For example, if you achieve 85% line coverage, you will earn 8.5 out of
10 points.