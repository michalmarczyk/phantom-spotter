(ns phantom-spotter.core
  (:import (java.lang.ref PhantomReference ReferenceQueue)))

(defn- default-callback [msg]
  (println msg))

(defn watch!
  "Spins up a background thread which will check whether obj has
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
  is reasonably robust for use for debugging purposes."
  ([obj]
     (watch! (str obj) obj))
  ([msg obj]
     (watch! default-callback msg obj))
  ([callback msg obj]
     (let [rq (ReferenceQueue.)
           pr (PhantomReference. obj rq)]
       (future
         (while (not (.poll rq))
           (Thread/sleep 100))
         (callback msg)))))
