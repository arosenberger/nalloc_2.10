Allocation Free Options in Scala
======
This is an explicit backport of nalloc at https://github.com/arosenberger/nalloc to Scala 2.10

All functionality from that project exists here save for allocation free `unapply` methods via Name Based Extractors, which are a 2.11 only feature.

I failed to cross build because of the unapply problem and a difference in macro constructs between 2.10 and 2.11, so this is an entirely separate code base.

SBT Dependency
======

libraryDependencies += "org.nalloc" %% "optional" % "0.1.0-SNAPSHOT"

Getting Started
======

import org.nalloc.bitb.kcits.optional._
