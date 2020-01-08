(ns movieapp.view 
  (:use hiccup.page hiccup.element)
  (:require 
    [hiccup.core :refer [h]]
    [hiccup.form :as form]
    [clojure.string :as str]
    [movieapp.layout :as layout]
    [ring.util.anti-forgery :as anti-forgery]))


(defn display-all-movies [movies]
  [:div {:class "card text-center"}
   [:h1 "Show all movies"]
   [:p "Top Rated movies in 2020"]
   [:br]
   [:table  {:class "table table-bordered"}
    [:th "id"]
    [:th "Title"]
    [:th "Description"]
    [:th "Director"]
;   [:th "Release Year"]
    [:th "Delete"]
    [:th "Update"]
    (map 
      (fn [movie]
        [:tr 
         [:td (h (:id movie))]
         [:td (h (:title movie))]
         [:td (h (:description movie))]
         [:td (h (:director movie))]
;         [:td (h (:releaseYear movie))]
         [:td [:a {:href (str "/delete/" (h (:id movie)))} "delete"]]
         [:td [:a {:href (str "/update/" (h (:id movie)))} "update"]]
         ]) movies)]])

(defn add-movie-form []
  [:div {:class "form-group card"} 
   [:h1 {:class "text-center"} "Add Movie"]
   (form/form-to [:post "/"]
                 (anti-forgery/anti-forgery-field)
                 [:div {:class "form-group"}
                  (form/label "title" "Title ")
                  (form/text-field {:class "form-control"} "title")]
                 [:div {:class "form-group"}                  
                  (form/label "description" "Description ")                  
                  (form/text-field {:class "form-control"}  "description")]
                 [:div {:class "form-group"}                  
                  (form/label "director" "Director ")                  
                  (form/text-field {:class "form-control"}  "director")]
                 [:div {:class "form-group"}                  
                  (form/label "releaseYear" "Release Year ")                  
                  (form/text-field {:class "form-control"}  "releaseYear")]
                 (form/submit-button {:class "btn btn-primary btn-lg btn-block"}  "Add Movie")
                 [:br])])

(defn index-page [movies] 
  (layout/common-layout ""
                        [:div {:class "col-lg-1"}]
                        [:div {:class "col-lg-10"}
                         (display-all-movies movies)                        
                         (add-movie-form)]
                        [:div {:class "col-lg-1"}]   
                        ))


(defn update-movie-form [movie]
   (layout/common-layout "" 
  [:div {:class "form-group card"} 
   [:h1 {:class "text-center"} "Update Movie"]
    (map 
      (fn [movie]
     (form/form-to [:post "/update-movie"]
                 (anti-forgery/anti-forgery-field)
                 (form/hidden-field "id" (:id movie))
                 [:div {:class "form-group"}
                  (form/label "title" "Title ")
                  (form/text-field {:class "form-control"} "title" (:title movie))]
                 [:div {:class "form-group"}                  
                  (form/label "description" "Description ")                  
                  (form/text-field {:class "form-control"}  "description" (:description movie))]
                 [:div {:class "form-group"}                  
                  (form/label "director" "Director ")                  
                  (form/text-field {:class "form-control"}  "director" (:director movie))]
                 [:div {:class "form-group"}                  
                  (form/label "releaseYear" "Release Year ")                  
                  (form/text-field {:class "form-control"}  "releaseYear" (:releaseYear movie))]
                 (form/submit-button {:class "btn btn-primary btn-lg btn-block"}  "Update Movie")
                 [:br])) movie)]
    
))


