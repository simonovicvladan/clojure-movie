(ns movieapp.core
  (:use compojure.core
        ring.util.json-response 
        ring.adapter.jetty)
  (:require [compojure.core :refer [defroutes GET POST]]
            [clojure.string :as str]     
            [ring.util.response :as ring]   
            [movieapp.view :as view]
            [movieapp.db :as db]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            ))

(defn display-all-movies []
  (view/index-page (db/get-all-movies)))

(defn create-movie [title description director releaseYear]
  (when-not (str/blank? title)
    (db/create-movie title description director releaseYear))
  (ring/redirect "/"))

(defn delete-movie [id]
  (when-not (str/blank? id)
    (db/delete-movie id))
  (ring/redirect "/"))

(defn show-update-view [id]
 (view/update-movie-form (db/get-movie-by-id id)))

(defn update-movie [id title description director releaseYear]
    (when-not (str/blank? id)
    (db/update-movie id title description director releaseYear))
   (view/index-page (db/get-all-movies)))

(defroutes my_routes
  (GET "/" [] (display-all-movies))
  (POST "/" [title description director releaseYear] (create-movie title description director releaseYear))
  (POST "/update-movie"  [id title description director releaseYear] (update-movie id title description director releaseYear))
  (GET "/update/:id" [id] (show-update-view id))  
  (GET "/delete/:id" [id]  (delete-movie id))
  (GET "/api/all" [] (json-response (db/get-all-movies))))
 

(def app
  (wrap-defaults my_routes site-defaults))