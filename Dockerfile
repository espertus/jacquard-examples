# docker build -t yourname/quiz1-autograder .

FROM gradescope/autograder-base:ubuntu-22.04-jdk17

COPY config /autograder/config
COPY gradle /autograder/gradle
COPY src /autograder/src
COPY gradlew.bat gradlew settings.gradle build.gradle run_autograder /autograder
COPY run_autograder.py /autograder/run_autograder

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends maven checkstyle && \
    apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Ensure that scripts are Unix-friendly and executable.
RUN dos2unix /autograder/run_autograder
RUN chmod +x /autograder/run_autograder

# Force gradle download and start daemon.
WORKDIR /autograder
RUN ./gradlew clean

# Download checkstyle library
RUN mkdir -p lib
WORKDIR lib
RUN wget https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.12.1/checkstyle-10.12.1-all.jar
WORKDIR /autograder
