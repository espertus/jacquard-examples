# docker build -t hw1 .
# winpty docker run -it hw1 bash

FROM gradescope/autograder-base:ubuntu-22.04-jdk17

COPY . /autograder
RUN ln /autograder/run_autograder.py /autograder/run_autograder
COPY submission/Mob.java /autograder/submission

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends maven checkstyle && \
    apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Ensure that scripts are Unix-friendly and executable.
RUN dos2unix /autograder/run_autograder
RUN chmod +x /autograder/run_autograder

# Force gradle download and start daemon.
WORKDIR /autograder
RUN ./gradlew clean
