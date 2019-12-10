(ns otm-notes.ui.cmd
  "The command line interface of the application."
  (:require [otm-notes.state :as os]
            [otm-notes.utility :refer [uuid]]
            [otm-notes.notes :as note]
            [clojure.string :refer [trim split]]
            [clojure.pprint :refer [pprint]]))

(def command-docs {:help "List the functions that can be performed."
                   :addn "Add a note to the state."
                   :addt "Add an active tag or tags to the set of active tags."
                   :remn "Remove a note from the state."
                   :remt "Remove a tag or tags from the set of active tags."
                   :delt "Clear the set of active tags."
                   :save "Save the notes to a file."
                   :list "Print notes with tagged by active tags."
                   :exit "Exit the program."
                   :state "Prints the state"})

(defn- prompt
  "Prints a prompt '>' preceeded by the string s and reads a line from standard input."
  [s]
  (print s "> ")
  (flush)
  (read-line))

(defn- prompt-command
  "Prompts the user for a command and reads it."
  []
  (keyword (trim (prompt "cmd "))))

(defn- prompt-tags
  "Prompts the user for a line of tags."
  []
  (into [] (comp (map trim)
                 (distinct)
                 (map keyword))
        (split (prompt "tags") #" ")))

(defn- read-note
  "Reads a note from the user."
  []
  {::note/title (prompt "title")
   ::note/body (prompt "body")
   ::note/tags (prompt-tags)
   ::note/id (if-let [id (uuid (prompt "id"))]
               id
               (uuid))})

(defn- add-note
  "Adds a note to the state."
  [state]
  (os/add-note state (read-note)))

(defn- remove-note
  "Removes a note by id."
  [state]
  (os/remove-note state (os/get-note-by-id state (uuid (prompt "uuid")))))

(defn- add-active-tags
  "Add tags to active tags."
  [state]
  (os/add-active-tags state (prompt-tags)))

(defn- remove-active-tags
  "Removes tags from active tags."
  [state]
  (os/remove-active-tags state (prompt-tags)))

(defn- clear-active-tags
  "Removes all active tags from the state."
  [state]
  (os/clear-active-tags state))

(defn- print-active-notes
  "Prints active notes to standard output."
  [state]
  (pprint (os/active-notes state)))

(defn- help
  "Prints the list of commands."
  [_state]
  (pprint (into {} (map (fn [[k v]] [(name k) v])) command-docs)))

(defn- save
  "Saves the state to the file `filename`."
  [state]
  (os/save state (prompt "filename")))

(defn- print-state
  "Prints the state."
  [state]
  (pprint state))

(defn command-unknown
  "Reports that the given command was invalid to std. output."
  []
  (println "Invalid command"))

(defn exit
  "Terminates the process."
  [_state]
  (System/exit 0))

(def command-map {:help help
                  :addn add-note
                  :addt add-active-tags
                  :remn remove-note
                  :remt remove-active-tags
                  :list print-active-notes
                  :clear clear-active-tags
                  :save save
                  :state print-state
                  :exit exit})


(defn main-loop
  "The main loop of the command-line interface."
  [state]
  (help state)
  (loop [cmd (prompt-command)]
    (when (some? cmd)
      (if (contains? command-map cmd)
        (do ((command-map cmd) state)
            (recur (prompt-command)))
        (do (command-unknown)
            (recur (prompt-command)))))))
