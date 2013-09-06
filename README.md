# phantom-spotter

A simple aid for monitoring GC eligibility of objects.

## Usage

At this time there is only one public namespace,
`phantom-spotter.core`, with a single function of interest: `watch!`.
Thus, the relevant documentation fits into the docstring of `watch!`,
reproduced below:

    phantom-spotter.core/watch!
    ([obj] [msg obj] [callback msg obj])
      Spins up a background thread which will check whether obj has
      become eligible for GC every 100 ms. Once the check comes back
      positive, callback is called with the given msg. msg defaults
      to (str obj), while callback defaults to a function which calls
      println on msg. The background thread terminates after callback
      returns.

      Note a GC run must occur for an object's GC eligibility to be
      discovered; use (System/gc) to ask the JVM to perform one. Even
      with (System/gc), both GC runs and the mechanism used by watch! to
      monitor GC eligibility (PhantomReferences and ReferenceQueues)
      operate in a nondeterministic manner, so strictly speaking there is
      no guarantee of the callback ever being called. Nevertheless, watch!
      is reasonably robust for use for debugging purposes.

An example:

    (require '[phantom-spotter.core :as ps])
    (ps/watch! [1 2])
    ;; printed from background thread:
    ;;=> [1 2]

## Dependency information

phantom-spotter is available from [Clojars](https://clojars.org):

    [phantom-spotter "${version}"]

See [the artefact's page](https://clojars.org/phantom-spotter) on
Clojars or the latest tag in the repository for the current version.

## Licence

Copyright © 2013 Michał Marczyk

Distributed under the
[Eclipse Public License version 1.0](http://www.eclipse.org/legal/epl-v10.html).
