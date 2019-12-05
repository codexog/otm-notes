
# Table of Contents

1.  [Use](#orgb0837b1)
2.  [Dev](#orgfc48756)
    1.  [Running the project](#org43181ec)
    2.  [Make a jar and uberjar](#org91c6914)
    3.  [Start a pretty REPL](#org18802dc)
    4.  [Check your style](#orgb9f5b99)
    5.  [Run tests](#org7140f0f)
    6.  [Test coverage report](#org8e79446)
3.  [Bugs](#org63388bf)
4.  [Misc](#org26220c0)



<a id="orgb0837b1"></a>

# Use

This is a simple note taking application. One will eventually be able to take, edit, and remove notes using it,
notes can be tagged and filtered by tag. For more see [design](./../master/docs/design.md). The application has a graphical as well as a command-line interface. The command line interface and the graphical interface are synchronised. In other words they modify the same global state. The graphical and command line interfaces can be terminated separately, they are, however, the same process. At the moment the application must be killed to terminate. The notes are stored in an edn file, for more on the format see [edn-format](https://github.com/edn-format/edn).


<a id="orgfc48756"></a>

# Dev

To use the development tools you will need to have clojure installed. See [GettingStarted](https://clojure.org/guides/getting_started) for instructions on installing and getting started. For more information on the command line tools see [Deps and CLI guide](https://clojure.org/reference/deps_and_cli). For a brief overview of the code structure see [architecture](./../master/docs/architecture.md).


<a id="org43181ec"></a>

## Running the project

    clj -A:main "filename"

using a desired filename.


<a id="org91c6914"></a>

## Make a jar and uberjar

Using [cambada](https://github.com/luchiniatwork/cambada)

    clj -A:uberjar

The files are found in the [target](./../master/target) directory.

The standalone can be run using the command

    java -jar target/otm-notes-1.0.0-SNAPSHOT-standalone.jar "filename"

Here again using an actual filename.


<a id="org18802dc"></a>

## Start a pretty REPL

A repl enhanced by [rebel-readline](https://github.com/bhauman/rebel-readline)

    clj -A:repl


<a id="orgb9f5b99"></a>

## Check your style

Check the project using [clj-kondo](https://github.com/borkdude/clj-kondo)

    clj -A:style


<a id="org7140f0f"></a>

## Run tests

Run the test suite using [kaocha](https://github.com/lambdaisland/kaocha)

    clj -A:test


<a id="org8e79446"></a>

## Test coverage report

Using [cloverage](https://github.com/cloverage/cloverage/tree/master/cloverage/sample/cloverage/sample)

    clj -A:cov


<a id="org63388bf"></a>

# Bugs

-   The cursor jumps around in the gui text-fields for tags.


<a id="org26220c0"></a>

# Misc

For the timelog of the project [timelog](./../master/docs/timelog.md).

