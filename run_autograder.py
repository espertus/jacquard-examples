#!/usr/bin/env python3

import json
import os
import platform
import shutil
import subprocess

SUBMISSION_FILES = ["Mob.java"]
SUBMISSION_PACKAGE = "student"

# All directories are relative. On the server, they are relative to /autograder.
SUBMISSION_SUBDIR = "submission" + os.sep
GRADESCOPE_RESULTS_SUBDIR = "results" + os.sep
GRADESCOPE_RESULTS_PATH = GRADESCOPE_RESULTS_SUBDIR + "results.json"
SOURCE_SUBDIR = os.path.join("src", "main", "java") + os.sep
WORKING_SUBDIR = "working" + os.sep
WORKING_JAVA_SUBDIR = WORKING_SUBDIR + SOURCE_SUBDIR + os.sep
GRADLEW_WINDOWS_CMD = "gradlew.bat"
GRADLEW_UNIX_CMD = "gradlew"
LIB_PATH = os.path.join("..", "lib") + os.sep
# This includes directories, which must end with a separator.
FILES_TO_COPY = ["build.gradle", "gradle" + os.sep, "gradlew", "gradlew.bat",
                 "settings.gradle", "src" + os.sep]


def is_windows():
    """Checks whether the underlying OS is Windows."""
    return platform.system() == "Windows"


def is_local():
    """Check whether the file is running locally or on Gradescope."""
    return not os.getcwd().startswith("/autograder")


def init():
    """Initialize the environment before compilation can be done."""
    if is_local():
        # TODO: If submission directory isn't present, create it and copy file.
        pass
    else:
        os.makedirs(GRADESCOPE_RESULTS_SUBDIR, exist_ok=True)

    create_working_dir()
    copy_source_files()
    copy_req_files()


def create_working_dir():
    """Create the working directory for compilation/execution."""
    shutil.rmtree(WORKING_SUBDIR, ignore_errors=True)
    os.mkdir(WORKING_SUBDIR)


def copy_source_files():
    """Copy all source code and gradle files to working directory."""
    for file in FILES_TO_COPY:
        if file.endswith(os.sep):
            shutil.copytree(file, WORKING_SUBDIR + file)
        else:
            shutil.copy(file, WORKING_SUBDIR)


def package_to_path(package: str):
    """Convert a package name into a relative path."""
    return package.replace(".", os.sep) + os.sep


def copy_req_files():
    """Copy student-provided files into the appropriate server directory.

    :raise Exception: if a required file is not found

    """
    path = package_to_path(SUBMISSION_PACKAGE)
    dest_path = WORKING_JAVA_SUBDIR + path
    os.makedirs(dest_path, exist_ok=True)

    for file in SUBMISSION_FILES:
        file_path = SUBMISSION_SUBDIR + file
        if os.path.exists(file_path):
            shutil.copy(file_path, dest_path)
        else:
            raise Exception(f"File {file_path} not found.")


def run():
    """Run the compiled project, outputting the results.

    Results are written to stdout if run locally, to a file if run on the server.

    :raise Exception: if the result of the run is not 0.
    """
    os.chdir(WORKING_SUBDIR)
    gradle_cmd = GRADLEW_WINDOWS_CMD if is_windows() else GRADLEW_UNIX_CMD

    result = subprocess.run(
        [gradle_cmd, "-PlibPath=" + LIB_PATH, "clean", "run", "--quiet"],
        shell=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE)
    if result.returncode != 0:
        raise Exception("Runtime error: " + result.stderr.decode("UTF-8"))
    os.chdir("..")
    output(result.stdout.decode())


def output(s):
    """Output to stdout if run locally or a file if run on the Gradescope server. """
    if is_local():
        print(s)
    else:
        with open(GRADESCOPE_RESULTS_PATH, "w") as text_file:
            text_file.write(s)


def output_error(e):
    """Output an error."""
    data = {"score": 0, "output": str(e)}
    output(json.dumps(data))


def main():
    try:
        init()
        run()
    except Exception as e:
        output_error(e)


if __name__ == "__main__":
    main()
