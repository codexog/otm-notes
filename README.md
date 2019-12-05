# Notes

## Use

This is a simple note taking application. One will eventually be able to take, edit, and remove notes using it,
notes can be tagged and filtered by tag. For more see [specification](../master/docs/specification.org). The application has a graphical as well as a command-line interface. At the moment the application must be killed to terminate.

## Dev

For this part you will need to have clojure installed. See [GettingStarted](https://clojure.org/guides/getting_started) for instructions on installing and getting started. For more information on the command line tools see [Deps and CLI guide](https://clojure.org/reference/deps_and_cli). For a brief overview of the code structure see [architecture](../master/docs/architecture.org).

### Run the project
To run the project in the terminal type

```shell
clj -A:main <filename>
```
  
Where filename specifies the file that one wishes to read.

### Making a jar
To create a runnable jar as well as a standalone uberjar:

``` shell
clj -A:uberjar
```
They can be found in the [target](../master/target) directory.

The standalone file can be run using

``` shell
java -jar target/otm-notes-1.0.0-SNAPSHOT-standalone.jar <filename>
```

Where filename specifies the file that one wishes to read.

### Running a repl
Run a pretty repl

``` shell
clj -A:repl
```

### Check your style
Reports style errors, dead arguments, etc.

``` shell
clj -A:style
```

### Run tests
Run the test suite.

``` shell
clj -A:test
```

### Test coverage
Generate test coverage report.

``` shell
clj -A:cov
```

## Bugs

* The cursor jumps around in the text-fields for tags.

## Misc
For the timelog of the project see [timelog](../master/docs/timelog.org)
