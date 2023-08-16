# This fixes carriage returns in run_autograder
cd /autograder
dos2unix run_autograder source/run_autograder.py

# This installs checkstyle, which is often used by Gradescope.
cd /autograder
mkdir -p lib
wget -P lib https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.12.1/checkstyle-10.12.1-all.jar

# This moves files from /autograder/source to /autograder.
mv source/config source/gradle source/src /autograder
mv source/gradlew source/settings.gradle source/build.gradle /autograder
mv source/run_autograder.py /autograder/run_autograder
chmod a+x run_autograder gradlew

# This runs Gradle once, to download any needed files.
./gradlew clean
