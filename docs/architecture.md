# Architecture
This document is meant to provide a short overview of the way that the code is structured.

## Structure
The code is separated into different namespaces each isolating a particular concern. The logic and consequently the data-model is contained in `otm-notes.notes`, `otm-notes.state` creates the stateful interface, `otm-notes.ui.cmd` contains the logic pertaining to the command-line interface, `otm-notes.core` reads the arguments given to the application and contains the main function of the application.

## User interface
All user interface namespaces are prefixed `otm-notes.ui`. At the moment there is only a basic command-line user interface the code for which is in `otm-notes.ui.cmd`.

## Logic and model
The logic and data model of the application is contained in the namespace `otm-notes.notes`.

## Persistent storage
The state of the application is stored by calling `otm-notes.state/save` on the state and specifying a filename, the specific form that this call takes depends on the user interface used. The file is saved in the edn format, for more on the format see [edn-format](https://github.com/edn-format/edn).
