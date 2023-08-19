#!/usr/bin/env python3

import configparser
import json
import os
import platform
import shutil
import subprocess

# The next two lines should usually be modified for new assignments.
SUBMISSION_FILES = ["FavoritesIterator.java"]
SUBMISSION_PACKAGE = "student"

# Configuration file
CONFIG_PATH = "config.ini"
CONFIG_SUBMISSION_SECTION_NAME = "submission"
CONFIG_SECTIONS = [CONFIG_SUBMISSION_SECTION_NAME]
CONFIG_PACKAGE_KEY = "package"
CONFIG_FILES_KEY = "files"
CONFIG_KEYS = [CONFIG_PACKAGE_KEY, CONFIG_FILES_KEY]

# All directories are relative. On the server, they are relative to /autograder.
SUBMISSION_SUBDIR = "submission" + os.sep
GRADESCOPE_RESULTS_SUBDIR = "results" + os.sep
GRADESCOPE_RESULTS_PATH = GRADESCOPE_RESULTS_SUBDIR + "results.json"
SOURCE_SUBDIR = os.path.join("src", "main", "java") + os.sep
WORKING_SUBDIR = "working" + os.sep
WORKING_JAVA_SUBDIR = WORKING_SUBDIR + SOURCE_SUBDIR + os.sep
GRADLEW_WINDOWS_CMD = "gradlew.bat"
GRADLEW_UNIX_CMD = "./gradlew"
# This includes directories, which must end with a separator.
FILES_TO_COPY = ["build.gradle", "gradle" + os.sep, "gradlew", "gradlew.bat",
                 "src" + os.sep, "lib" + os.sep, "config" + os.sep]


def is_windows():
    """Checks whether the underlying OS is Windows."""
    return platform.system() == "Windows"


def is_local():
    """Check whether the file is running locally or on Gradescope."""
    return not os.getcwd().startswith("/autograder")


def init():
    """Initialize the environment before compilation can be done."""
    if not is_local():
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
            if os.path.isdir(file):
                shutil.copytree(file, WORKING_SUBDIR + file)
        else:
            shutil.copy(file, WORKING_SUBDIR)


def package_to_path(package: str):
    """Convert a package name into a relative path."""
    return package.replace(".", os.sep) + os.sep


def ensure_file_in_package(file_path: str, package: str):
    """Ensures that a file contains the expected package statement.

    :raise Exception: if the package student is not found.
    """
    pkg_stmt = f"package {package};"
    with open(file_path, 'r') as f:
        for line in f:
            if line.lstrip().startswith(pkg_stmt):
                return
    raise Exception(
        f"File {file_path} does not contain the expected package declaration: {pkg_stmt}")


def read_config_file() -> (str, list[str]):
    """Read the submission package and filenames from config file

    :raise Exception: if the config file cannot be found or has invalid content
    """

    # Make sure config file has required section and keys.
    config = configparser.ConfigParser()
    if not config.read(CONFIG_PATH):
        raise Exception(
            f"Unable to read configuration file {CONFIG_PATH}"
        )
    if CONFIG_SUBMISSION_SECTION_NAME not in config.sections():
        raise Exception(
            f"Did not find section '{CONFIG_SUBMISSION_SECTION_NAME}' in {CONFIG_PATH}"
        )
    if len(config.sections()) > len(CONFIG_SECTIONS):
        sections = config.sections()
        sections.remove(CONFIG_SUBMISSION_SECTION_NAME)
        raise Exception(
            f"Unexpected section(s) in {CONFIG_PATH}: {sections}"
        )
    section = config[CONFIG_SUBMISSION_SECTION_NAME]
    for key in CONFIG_KEYS:
        if key not in section:
            raise Exception(
                f"Did not find key '{key}' in section '{CONFIG_SUBMISSION_SECTION_NAME}' of {CONFIG_PATH}"
            )
    if len(section) > len(CONFIG_KEYS):
        keys = list(section.keys())
        for key in CONFIG_KEYS:
            keys.remove(key)
        raise Exception(
            f"Unexpected key(s) in {CONFIG_PATH}: {keys}"
        )

    # Extract configuration values.
    package = section[CONFIG_PACKAGE_KEY]
    files = section[CONFIG_FILES_KEY]
    if len(files) < 2 or files[0] != '[' or files[-1] != ']':
        raise Exception(
            f"Could not parse {CONFIG_FILES_KEY} value '{files}' in section '{CONFIG_SUBMISSION_SECTION_NAME}' of {CONFIG_PATH}")
    files_list = [file.strip() for file in files[1:-1].split(',')]
    return (package, files_list)


def copy_req_files():
    """Copy student-provided files into the appropriate server directory.

    :raise Exception: if a required file is not found

    """
    package, files = read_config_file()
    path = package_to_path(package)
    dest_path = WORKING_JAVA_SUBDIR + path
    os.makedirs(dest_path, exist_ok=True)

    for file in files:
        file_path = SUBMISSION_SUBDIR + file
        if os.path.exists(file_path):
            if file_path.endswith(".java"):
                ensure_file_in_package(file_path, package)
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
        [gradle_cmd, "clean", "run", "--quiet"],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE)
    if result.returncode != 0:
        raise Exception("Runtime error: " + result.stderr.decode("UTF-8"))
    os.chdir("..")
    output(result.stdout.decode())


def output(s):
    """Output to stdout and to a file if run on the Gradescope server. """
    if is_local():
        print(s)
    else:
        print(s)
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
