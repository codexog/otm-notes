# Notes

## Use

This is a simple note taking application. One will eventually be able to take, edit, and remove notes using it,
notes can be tagged and filtered by tag. For more see [design](../master/docs/design.md).

## Dev

For this part you will need to have clojure installed. See [Getting Started](https://clojure.org/guides/getting_started) for instructions on installing and getting started. For more information on the command line tools see [Deps and CLI guide](https://clojure.org/reference/deps_and_cli).

### Run the project
To run the project in the terminal

```shell
clj -A:main <filename>
```
  
Where filename is the file where notes are to be stored and are stored. Currently it only reads the edn file that is given as first argument, and prints the contents of it

### Making a jar
To create a runnable jar as well as a standalone uberjar:

``` shell
clj -A:uberjar
```
They can be found in the [target](../master/target) directory.

The standalone file can be run using

``` shell
java -jar target/otm-notes-1.0.0-SNAPSHOT-standalone.jar
```

## Documentation

For now this is limited to this document and the docstrings in the source.
