(ns otm-notes.ui.jfx
  "The graphical user interface of the application (JavaFX based / cljfx)."
  (:require [cljfx.api :as fx]
            [otm-notes.notes :as n]
            [otm-notes.state :as os]))

(defn note [ui-state {::n/keys [title body tags id] :as note}]
  {:fx/type :v-box
   :padding 5
   :children [{:fx/type :label
               :text "Title"}
              {:fx/type :text-field
               :prompt-text "Note title"
               :on-text-changed {:event/type ::edit-note ::n/id id ::attribute ::n/title}
               :text title}
              {:fx/type :label
               :text "Body"}
              {:fx/type :text-area
               :prompt-text "Note body"
               :on-text-changed {:event/type ::edit-note ::n/id id ::attribute ::n/body}
               :text body}
              {:fx/type :label
               :text "Tags"}
              {:fx/type :text-field
               :prompt-text "Note tags"
               :on-text-changed {:event/type ::edit-note-tags ::n/id id}
               :text (if-let [field-state (get-in @ui-state [::n/id ::n/tags])]
                       (if (not= (n/string->tags field-state) tags)
                         (n/tags->string tags)
                         field-state)
                       (n/tags->string tags))} 
              {:fx/type :button
               :text "Remove note"
               :on-action {:event/type ::remove-note ::note note}}]})

(defn active-tags-input [ui-state active-tags]
  {:fx/type :h-box
   :padding 10
   :spacing 10
   :children [{:fx/type :label
               :text "Active tags: "}
              {:fx/type :text-field
               :prompt-text "Active tags here"
               :text (let [field-string (get-in @ui-state [::n/active-tags])]
                       (if (and field-string
                                (= active-tags (n/string->tags field-string)))
                         field-string
                         (n/tags->string active-tags)))
               :on-text-changed {:event/type ::active-tag-update}}
              {:fx/type :button
               :text "clear"
               :on-action {:event/type ::clear-active-tags}}]})

(defn add-new-note []
  {:fx/type :button
   :text "Add new note"
   :on-action {:event/type ::add-new-note}})

(defn save-notes [filename]
  [{:fx/type :text-field
    :prompt-text "file name"
    :on-text-changed {:event/type ::filename-update}
    :text filename}
   {:fx/type :button
    :text "Save notes"
    :on-action {:event/type ::save}}])

(defn root [ui-state {{::n/keys [active-tags] :as n-state} ::os/notes filename ::os/filename}]
  {:fx/type :stage
   :title "OTM-Notes"
   :on-close-request (fn [_] (System/exit 0)) 
   :showing true
   :scene {:fx/type :scene
           :root {:fx/type :v-box
                  :padding 5
                  :children [(active-tags-input ui-state active-tags)
                             {:fx/type :scroll-pane
                              :v-box/vgrow :always
                              :fit-to-width true
                              :content {:fx/type :v-box
                                        :children
                                        (mapv (partial note ui-state)
                                              (sort-by ::n/id (vals (n/active-notes n-state))))}}
                             {:fx/type :h-box
                              :padding 5
                              :spacing 5
                              :children (into [(add-new-note)]
                                              (save-notes filename))}]}}})

(defn map-event-handler [ui-state state event]
  (case (:event/type event)
    ::edit-note
    (os/update-note state {::n/id (::n/id event)
                           (::attribute event) (:fx/event event)})
    ::edit-note-tags
    (let [field-string (:fx/event event)
          new-tags (n/string->tags field-string)
          note-id (::n/id event)]
      (swap! ui-state assoc-in [::n/id ::n/tags] field-string)
      (os/update-note state {::n/id note-id ::n/tags new-tags}))
    ::add-new-note
    (os/add-note state (n/add-id-to-note {::n/title "" ::n/body ""
                                          ::n/tags (os/active-tags state)}))
    ::active-tag-update
    (let [input-tags (n/string->tags (:fx/event event))]
      (swap! ui-state assoc ::n/active-tags (:fx/event event))
      (os/set-active-tags state input-tags))
    ::remove-note
    (os/remove-note state (::note event))
    ::clear-active-tags
    (os/clear-active-tags state)
    ::save
    (os/save state)
    ::filename-update
    (os/set-filename state (:fx/event event))
    nil))

(defn start [state]
  (let [ui-state (atom {})
        renderer (fx/create-renderer :middleware (fx/wrap-map-desc assoc :fx/type (partial root ui-state))
                                     :opts {:fx.opt/map-event-handler
                                            (partial map-event-handler ui-state state)})]
    (add-watch state [`mount renderer] #(renderer %4))
    (renderer @state)
    (println "Started GUI")))
