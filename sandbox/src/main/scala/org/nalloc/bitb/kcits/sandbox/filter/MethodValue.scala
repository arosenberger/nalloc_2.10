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

package org.nalloc.bitb.kcits.sandbox.filter

import org.nalloc.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {
  private[this] val be = b.filter(isZero)
  private[this] val se = s.filter(isZero)
  private[this] val ie = i.filter(isZero)
  private[this] val le = l.filter(isZero)
  private[this] val fe = f.filter(isZero)
  private[this] val de = d.filter(isZero)
  private[this] val ste = st.filter(isEmpty)

  private def isZero[T: Numeric](t: T) = implicitly[Numeric[T]].zero == t

  private def isEmpty(s: String) = s == ""
}
