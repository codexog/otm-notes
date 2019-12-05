
# Table of Contents

1.  [Use](#org23f976c)
2.  [Dev](#org9cf1255)
    1.  [Running the project](#org919f86a)
    2.  [Make a jar and uberjar](#orgf69bf47)
    3.  [Start a pretty REPL](#orgc9f911e)
    4.  [Check your style](#org62f8e9a)
    5.  [Run tests](#org08ae51d)
    6.  [Test coverage report](#org8b21ff2)
3.  [Releases](#org090bbbb)
4.  [Bugs](#orgbb1463c)
5.  [Misc](#org223f10f)



<a id="org23f976c"></a>

# Use

This is a simple note taking application. One will eventually be able to take, edit, and remove notes using it,
notes can be tagged and filtered by tag. For more see [specification](docs/specification.org). The application has a graphical as well as a command-line interface. The command line interface and the graphical interface are synchronised. In other words they modify the same global state. The graphical and command line interfaces can be terminated separately, they are, however, the same process. At the moment the application must be killed to terminate. The notes are stored in an edn file, for more on the format see [edn-format](https://github.com/edn-format/edn).


<a id="org9cf1255"></a>

# Dev

To use the development tools you will need to have clojure installed. See [GettingStarted](https://clojure.org/guides/getting_started) for instructions on installing and getting started. For more information on the command line tools see [Deps and CLI guide](https://clojure.org/reference/deps_and_cli). For a brief overview of the code structure see [architecture](docs/architecture.org).


<a id="org919f86a"></a>

## Running the project

    clj -A:main "filename"

using a desired filename.


<a id="orgf69bf47"></a>

## Make a jar and uberjar

Using [cambada](https://github.com/luchiniatwork/cambada)

    clj -A:uberjar

The files are found in the [target](target/) directory.

The standalone can be run using the command

    java -jar target/otm-notes-1.0.0-SNAPSHOT-standalone.jar "filename"

Here again using an actual filename.


<a id="orgc9f911e"></a>

## Start a pretty REPL

A repl enhanced by [rebel-readline](https://github.com/bhauman/rebel-readline)

    clj -A:repl


<a id="org62f8e9a"></a>

## Check your style

Check the project using [clj-kondo](https://github.com/borkdude/clj-kondo)

    clj -A:style


<a id="org08ae51d"></a>

## Run tests

Run the test suite using [kaocha](https://github.com/lambdaisland/kaocha)

    clj -A:test


<a id="org8b21ff2"></a>

## Test coverage report

Using [cloverage](https://github.com/cloverage/cloverage/tree/master/cloverage/sample/cloverage/sample)

    clj -A:cov


<a id="org090bbbb"></a>

# Releases

-   [5.12.2019-alpha](https://github.com/codexog/otm-notes/releases/tag/5.12.2019-alpha)


<a id="orgbb1463c"></a>

# Bugs

-   The cursor jumps around in the gui text-fields for tags.


<a id="org223f10f"></a>

# Misc

For the timelog of the project [timelog](docs/timelog.org).

