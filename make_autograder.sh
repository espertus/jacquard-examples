#!/usr/bin/env bash

echo "Removing carriage returns from scripts..."
dos2unix setup.sh

echo "Building Autograder..."
zip -r autograder.zip lib/ config/ gradle/ src/ gradlew settings.gradle build.gradle run_autograder.py run_autograder setup.sh

echo "Build complete."
