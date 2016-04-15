# snake

A snake using ClojureScript and reagent, made during a Got.λ meetup.

http://www.meetup.com/got-lambda/events/224394073/

## Setup

To get an interactive development environment run:

    lein figwheel dev devcards

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To access the devcards, visit
[localhost:3449/devcards.html](http://localhost:3449/devcards.html).

To clean all compiled files:

    lein clean

To create a production build run:

    lein cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
