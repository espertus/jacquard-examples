#!/usr/bin/env bash

echo "Removing carriage returns from scripts..."
dos2unix setup.sh

echo "Building Autograder..."
zip -r autograder.zip lib/ config/ gradle/ src/ gradlew build.gradle gradlew.bat run_autograder run_autograder.py setup.sh config.ini

echo "Build complete."
