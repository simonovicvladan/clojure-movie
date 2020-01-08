(ns movieapp.db
  (:require [clojure.java.jdbc :as sql]))

(def connection 
  {:classname "com.mysql.cj.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//127.0.0.1:3306/movie_v2"
   :user "root"
   :password "vlajce"
   })

(defn create-movie [title description director releaseYear]
  (sql/insert! connection :movie [:title :description :director :releaseYear] [title description director releaseYear]))

(defn delete-movie [id]
 (sql/delete! connection :movie
            ["id = ?" id]))

(defn get-all-movies []
  (into [] (sql/query connection ["select * from movie"])))

(defn get-movie-by-id [id]
    (into [] (sql/query connection ["select * from movie where id = ?" id])))

(defn update-movie [id title description director releaseYear]
  (sql/update! connection :movie {:id id :title title :description description :director director :releaseYear releaseYear} ["id = ?" id]))
