(ns app.renderer.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.dom :refer [render]]
            ["tim-js-sdk" :as TIM]))


(enable-console-print!)

(defonce TRTCCloud  (js/require "trtc-electron-sdk"))


(defonce rtc-cloud (new TRTCCloud))

(def options #js {:SDKAppID 0})

(defonce tim (.create TIM options))


(defonce state (atom 0))

(def !div (atom nil))

(defn root-component []

  [:div
   [:div.logos {:ref (fn [el]
                       (reset! !div el))}
    [:img.electron {:src "img/electron-logo.png"}]
    [:img.cljs {:src "img/cljs-logo.svg"}]
    [:img.reagent {:src "img/reagent-logo.png"}]]
   [:button
    {:on-click #(swap! state inc)}
    (str "Clicked " @state " times")]])

(defn start! []
  (render
   [root-component]
   (js/document.getElementById "app-container")))


(comment
  (.on tim (.. TIM -EVENT -SDK_READY) (fn [e] (js/alert "hahahaha") ))
  (.startLocalPreview rtc-cloud @!div)
  (.stopLocalPreview rtc-cloud)
  )
