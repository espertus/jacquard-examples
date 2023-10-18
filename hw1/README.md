# Jacquard Homework 1 Example

This is an example of a [Jacquard](https://github.com/espertus/jacquard)
autograder for a homework assignment. It demonstrates the use of:

* Checkstyle
* PMD
* Unit tests
* Code coverage
* Cross-testing, i.e., running student tests against
    * student code
    * correct code
    * buggy code
 
This is more advanced and less thoroughly documented than [Quiz 1](../quiz1/README.md), which you should
view first.

## Teacher Instructions

We recommend walking through the [Quiz 1 example](../quiz1/README.md) and the top-level
videos before trying out this example.

### Execution

Unlike Quiz 1, this must be run from the command line. It will not run within the IDE unless you remove the lines that use the `CrossTester`:
```
CrossTester crossTester = new CrossTester(
    student.ILOSTest.class,
    "student-tests.csv");
results.addAll(crossTester.run());
```
The way to run it from the command line is with:
```
./test_autograder.sh
```

### Videos

1. [Opening Homework 1](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=b6ecb9d2-9a9b-4485-b394-b06a01300872) [0:21]
2. [Taking a first look at Homework 1](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=ba36573d-dd4a-493d-8b3d-b06a0181c9ff) [8:07]
3. [Cross-testing in Homework 1](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=165ca9fa-98eb-4f0f-8841-b069013430c5) [3:15]
4. [Running Homework 1 autograder locally](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=2f5efb35-fdc7-4bfa-a531-b09f015d8f06) [0:38]

### Files

These directories and files have code specific to the assignment:

* `config/checkstyle-rules.xml` holds the checkstyle rules file
* `src/main/java/student` contains
    * `Main.java`, which has the `main` method that controls the autograder
    * `ILOS.java`, which provides a "list of string" interface
    * `EmptyLOS.java` and `NonEmptyLOS.java`, placeholders for the student
      implementations of the interface
    * `ILOSTest.java`, a placeholder for student tests
    * `HiddenILOSTest.java`, which contain hidden JUnit 5 tests of student code
* `src/main/java/buggy` contains a deliberately buggy implementation of the
  required code, on which student tests should report errors
* `src/main/java/correct` contains a correct implementation of the required
  code, on which student tests should not report errors
* `src/main/resources/student-tests.csv` contains a CSV file for [cross-testing](https://github.com/espertus/jacquard-examples/blob/main/README.md#what-is-cross-testing)
* `submission` holds a sample submission (required if you want to run
  `test_autograder.py` locally)
* `submissions` (which is not required) holds sample submissions to manually
  test the grader on Gradescope

Any of the above files could have different names or packages.

#### config.ini

The submission package and files are specified in the `[submission]` section of
`config.ini` and should be edited if you change the package name or required
files. Currently, package names must have only a single part (e.g., "student",
not "edu.myschool.student").

```
[submission]
package = student
files = [EmptyLOS.java, NonEmptyLOS.java, ILOSTest.java]

[crosstests]
tests = [ILOSTest.java]
packages = [correct, buggy]
```

The `[crosstests]` sections indicates that the test `ILOSTest.java` in the
primary (`student`) package should also run on the instructor-provided
implements in the `correct` and `buggy` packages. For more information, see
[What is cross-testing?](https://github.com/espertus/jacquard-examples/blob/main/README.md#what-is-cross-testing).

**The remainder of the Teacher Instructions are the same as
for [Quiz 1](../quiz1).**

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

* `int size()`
* `String concat()`
  The methods are described in the javadoc.

### Tests

You are encouraged to practice test-driven development (TDD) and write
your tests before implementing the methods under test. To earn credit,
your tests must:

* be in `ILOSTest.java`
* have the `@Test` annotation
* have the `public` visibility modifier
* start with the name of the method under test (such
  as `sizeWorksForLength0`)
  Use the existing tests as a model.

You should modify and submit these files:

* `ILOSTest.java`
* `EmptyLOS.java`
* `NonEmptyLOS.java`

All of your tests must be annotated with `@Test` and have names that start
with the name of the method being tested, with the exact same capitalization.**
Use the provided test as a model for names.

### Grading

Grading will be based on:

* checkstyle with configuration
  file [config/checkstyle-rules.xml](config/checkstyle-rules.xml) [10 points]
* PMD
  with [java/best_practices.xml](https://docs.pmd-code.org/latest/pmd_rules_java_bestpractices.html) [10 points]
* Unit testing
    * Your implementation passes your tests [20 points]
    * Our hidden correct implementation passes your tests [15 points]
    * Our hidden buggy implementations fail your tests [15 points]
    * Your implementation passes our hidden tests [20 points]
* Code coverage [10 points]
    * `EmptyLOS` [2 points]
    * `NonEmptyLOS` [8 points]

Your code coverage score will be proportional to your line coverage.
For example, if you achieve 85% line coverage for both classes, you will earn
8.5 out of 10 points.
