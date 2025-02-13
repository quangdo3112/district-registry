(ns district-registry.server.utils
  (:require
   [cljs-ipfs-api.files :as ifiles]
   [cljs-web3.eth :as web3-eth]
   [cljs.nodejs :as nodejs]
   [cljs.reader :refer [read-string]]
   [district.server.config :refer [config]]
   [district.server.web3 :as web3]
   [taoensso.timbre :as log]))

(defonce fs (nodejs/require "fs"))


(defn get-ipfs-meta [conn meta-hash]
  (js/Promise.
    (fn [resolve reject]
      (log/info (str "Downloading: " "/ipfs/" meta-hash) ::get-ipfs-meta)
      (ifiles/fget (str "/ipfs/" meta-hash)
                   {:req-opts {:compress false}}
                   (fn [err content]
                     (cond
                       err
                       (let [err-txt "Error when retrieving metadata from ipfs"]
                         (log/error err-txt (merge {:meta-hash meta-hash
                                                    :connection conn
                                                    :error err})
                                    ::get-ipfs-meta)
                         (reject (str err-txt " : " err)))

                       (empty? content)
                       (let [err-txt "Empty ipfs content"]
                         (log/error err-txt {:meta-hash meta-hash
                                             :connection conn} ::get-ipfs-meta)
                         (reject err-txt))

                       :else (-> (re-find #".+(\{.+\})" content)
                               second
                               js/JSON.parse
                               (js->clj :keywordize-keys true)
                               resolve)))))))

(defn block-timestamp* []
  (->> (web3-eth/block-number @web3/web3) (web3-eth/get-block @web3/web3) :timestamp))

(def block-timestamp
  (memoize block-timestamp*))

(defn now-in-seconds []
  ;; if we are in dev we use blockchain timestamp so we can
  ;; increment it by hand, and also so we don't need block mining
  ;; in order to keep js time and blockchain time close
  (if (= :blockchain (:time-source @config))
    (block-timestamp)
    (quot (.getTime (js/Date.)) 1000)))

(defn load-edn-file [file]
  (try
    (-> (.readFileSync fs file)
      .toString
      read-string)
    (catch js/Error e
      (log/warn (str "Couldn't load edn file " file) ::load-edn-file)
      nil)))

(defn save-to-edn-file [content file]
  (.writeFileSync fs file (pr-str content)))
