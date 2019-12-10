
# Table of Contents

1.  [Use](#org89d332d)
2.  [Dev](#org705b327)
    1.  [Running the project](#org1335a6e)
    2.  [Make a jar and uberjar](#org90ce7f4)
    3.  [Start a pretty REPL](#org880ec84)
    4.  [Check your style](#org1511e17)
    5.  [Run tests](#org6e1746e)
    6.  [Test coverage report](#orgb385e8d)
3.  [Releases](#orgcd8db9f)
4.  [Bugs](#orgec94799)
5.  [Misc](#org279b522)



<a id="org89d332d"></a>

# Use

This is a simple note taking application. One can take, edit, and remove notes using it,
notes can be tagged and filtered by tag. For more see [specification](docs/specification.org). The application has a graphical as well as a command-line interface. The command line interface and the graphical interface are synchronised. In other words they modify the same global state. Terminating either of the active user interfaces will terminate the entire process. The program takes the following options

<table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">


<colgroup>
<col  class="org-left" />

<col  class="org-left" />
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left">prefix</th>
<th scope="col" class="org-left">function</th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">-f</td>
<td class="org-left">the subsequent string is taken as the default file location, the application will attempt to read from the file at launch, and unless changed will write to it when saving the notes.</td>
</tr>


<tr>
<td class="org-left">-u</td>
<td class="org-left">if the subsequent string is "gui" only the graphical user interface will be launched, in case it is "cmd" it launches the command-line interface without the graphical interface, in case the following string is anything else the application terminates immediately</td>
</tr>
</tbody>
</table>

Any invalid arguments given to the application are ignored.

The notes are stored in an edn file, for more on the format see [edn-format](https://github.com/edn-format/edn). **Be aware that the application does not write the notes to a file on exit, saving is performed only when the user explicitly does so.**

To run the application simply download the jar file associated with the latest release, and run the command

    java -jar <otm-note jar file> <options>

For more explicit instructions see [manual](docs/manual.org).


<a id="org705b327"></a>

# Dev

To use the development tools you will need to have clojure installed. See [GettingStarted](https://clojure.org/guides/getting_started) for instructions on installing and getting started. For more information on the command line tools see [Deps and CLI guide](https://clojure.org/reference/deps_and_cli). For a brief overview of the code structure see [architecture](docs/architecture.org). Note also that while this project makes very light use of [specs](https://clojure.org/about/spec) they document the implicit data model, and the tests rely on them being up to date.


<a id="org1335a6e"></a>

## Running the project

    clj -A:main

this command takes the same command line options as the program, for details see the [Use](https://github.com/codexog/otm-notes#Use) section of this document or the [Manual](docs/manual.org).


<a id="org90ce7f4"></a>

## Make a jar and uberjar

Using [cambada](https://github.com/luchiniatwork/cambada)

    clj -A:uberjar

The files are found in the [target](../target) directory.

The standalone can be run using the command

    java -jar target/otm-notes-1.0.0-SNAPSHOT-standalone.jar "filename"

Here again using an actual filename.


<a id="org880ec84"></a>

## Start a pretty REPL

A repl enhanced by [rebel-readline](https://github.com/bhauman/rebel-readline).

    clj -A:repl


<a id="org1511e17"></a>

## Check your style

Check the project using [clj-kondo](https://github.com/borkdude/clj-kondo)

    clj -A:style


<a id="org6e1746e"></a>

## Run tests

Run the test suite using [kaocha](https://github.com/lambdaisland/kaocha)

    clj -A:test


<a id="orgb385e8d"></a>

## Test coverage report

Using [cloverage](https://github.com/cloverage/cloverage/tree/master/cloverage/sample/cloverage/sample)

    clj -A:cov


<a id="orgcd8db9f"></a>

# Releases

-   [10.12.2019-final](https://github.com/codexog/otm-notes/releases/tag/10.12.2019-final)
-   [9.12.2019-alpha](https://github.com/codexog/otm-notes/releases/tag/9.12.2019-alpha)
-   [5.12.2019-alpha](https://github.com/codexog/otm-notes/releases/tag/5.12.2019-alpha)


<a id="orgec94799"></a>

# Bugs

Fixed the ones I found&#x2026;


<a id="org279b522"></a>

# Misc

For the timelog of the project [timelog](docs/timelog.org).
Pertaining to the tests [tests](docs/tests.org)

# Possible improvements

* Export option: csv
* Style the GUI

