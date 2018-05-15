(ns re-frame-bootstrap-toggle.core
  (:require [reagent.core :as reagent]))

(defn
  toggle
  "Returns a bootstrap-toggle react component. Options are:
   :checked - re-frame subscription (or Reagent atom) to the state of the component
   :on-click - event handler to respond to on-change events
   :on - Label to display in on state
   :off - Label to display in off state
   :onstyle - Bootstrap color to use if the toggle is on (e.g. primary or warning)
   :offstyle - Like :onstyle but used if the toggle is off
   :disabled - whether the button is disabled"
  [options]
  (let [measured-height (reagent/atom nil)
        measured-width (reagent/atom nil)]
    (reagent/create-class
     {:reagent-render (fn [options]
                        (let [checked (:checked options)
                              on-class (str "btn-" (or (:onstyle options)
                                                       "primary"))
                              off-class (str "btn-" (or (:offstyle options)
                                                        "default"))
                              height (or (:height options)
                                         @measured-height)
                              width (or (:width options)
                                        @measured-width)
                              disabled-class (when (:disabled options) "disabled ")]
                          [:div.toggle.btn {:class (if checked
                                                     (str disabled-class on-class " on")
                                                     (str disabled-class off-class " off"))
                                            :style {:height (when height (str height "px"))
                                                    :width (when width (str width "px"))}
                                            :on-click (when-not (:disabled options)
                                                        (:on-click options))}
                           [:div.toggle-group
                            [:label.btn.toggle-on {:class (if checked
                                                            (str on-class " active")
                                                            on-class)}
                             (or (:on options)
                                 "On")]
                            [:label.btn.toggle-off {:class (if checked off-class
                                                               (str off-class " active"))}
                             (or (:off options)
                                 "Off")]
                            [:span.toggle-handle.btn.btn-default]]]))
      :component-did-mount (fn [element]
                             (let [r-element (reagent/dom-node element)
                                   on-label (.querySelector r-element ".toggle-on")
                                   off-label (.querySelector r-element ".toggle-off")
                                   handle (.querySelector r-element ".toggle-handle")]
                               (reset! measured-height (max (.-scrollHeight on-label)
                                                            (.-scrollHeight off-label)))
                               (reset! measured-width (+ (max (.-scrollWidth on-label)
                                                              (.-scrollWidth off-label))
                                                         (.-scrollWidth handle)))))
      :display-name (str "bootstrap-toggle_" (random-uuid))})))

