(def version (or (System/getenv "VERSION") "MANUAL"))

(defproject schnick-schnack-schnuck version

  :uberjar-name ~(str "schnick-schnack-schnuck-" version "-standalone.jar")
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [compojure "1.6.2"]
                 [http-kit "2.5.3"]
                 [org.clojure/data.json "2.3.1"]]
  :repl-options {:init-ns schnick-schnack-schnuck.core}
  :main schnick-schnack-schnuck.core

  :profiles {:uberjar {:main schnick-schnack-schnuck.core
                       :aot  :all
                       :resource-paths ["resources"]}}
  )
