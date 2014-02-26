/*
 * Copyright 2014 Adam Rosenberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nalloc.bitb.kcits.sandbox.foreach

import org.nalloc.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {
  b.foreach(x => {
    println(x)
    println(x + 1)
  })
  s.foreach(x => {
    println(x)
    println(x + 1)
  })
  i.foreach(x => {
    println(x)
    println(x + 1)
  })
  l.foreach(x => {
    println(x)
    println(x + 1)
  })
  f.foreach(x => {
    println(x)
    println(x + 1)
  })
  d.foreach(x => {
    println(x)
    println(x + 1)
  })
  st.foreach(x => {
    println(x)
    println(x + 1)
  })
}
