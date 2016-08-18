(defproject x-announcer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.216"]
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
                                       :optimizations :simple
                                       :pretty-print  true}}]}

  :figwheel {;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"

             :css-dirs ["resources/public/css"]             ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "/Users/milanlempera/scripts/figwheel_intellij.sh"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             }

  :hooks [minify-assets.plugin/hooks]

  :aliases {"dev"   ["pdo" ["figwheel"] ["minify-assets" "watch"]]
            "build" ["pdo" "clean" ["cljsbuild" "once" "min"] ["minify-assets"]]})

