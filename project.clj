(defproject x-announcer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.225"]
                 [lucuma "0.5.1"]
                 [crate "0.2.4"]
                 [matchbox "0.0.9"]]
  :plugins [[lein-pdo "0.1.1"]
            [lein-figwheel "0.5.4-7"]
            [lein-cljsbuild "1.1.3"]
            [lein-asset-minifier "0.3.0"]]

  :minify-assets {:assets
                  {"resources/public/css/x-announcer.css" "resources/css"}}

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src"]

                        ;; If no code is to be run, set :figwheel true for continued automagical reloading
                        :figwheel     {:on-jsload "x-announcer.dev/on-js-reload"}

                        :compiler     {:main                 x-announcer.core
                                       :asset-path           "js/compiled/out"
                                       :output-to            "resources/public/js/compiled/x-announcer.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :source-map-timestamp true}}

                       ;; This next build is an compressed minified build for
                       ;; production. You can build this with:
                       ;; lein cljsbuild once min
                       {:id           "min"
                        :source-paths ["src"]
                        :compiler     {:output-to     "resources/public/js/compiled/x-announcer.js"
                                       :main          x-announcer.core
                                       :optimizations :advanced
                                       :pretty-print  false}}]}

  :figwheel {:css-dirs ["resources/public/css"]}

  :hooks [minify-assets.plugin/hooks]

  :aliases {"dev"   ["pdo" ["figwheel"] ["minify-assets" "watch"]]
            "build" ["pdo" "clean" ["cljsbuild" "once" "min"] ["minify-assets"]]})

