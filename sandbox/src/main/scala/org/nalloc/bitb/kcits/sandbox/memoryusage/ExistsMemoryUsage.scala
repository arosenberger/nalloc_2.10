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

package org.nalloc.bitb.kcits.sandbox.memoryusage

import java.util.Random
import org.nalloc.bitb.kcits.optional.OptionalLong
import scala.annotation.switch

object ExistsMemoryUsage extends MemoryUsageApp {
  protected def passes = 25
  private[this] val passes_ = passes
  private[this] val iterations = 1e7.toInt
  private val random = new Random
  private[this] val seedValues = (1 to iterations).map(_ => random.nextLong()).toArray
  private[this] val seedValuesReverse = seedValues.reverse

  private[this] val customOptionValues = new Array[Boolean](passes)

  forceGc()
  touchCode()
  forceGc()
  Thread.sleep(1000)
  forceGc()
  private[this] var i = 0
  while (i < passes_) {
    forceGc()
    runCustomOptionsTest(i)
    i += 1
  }

  println(customOptionValues.count(_ == true))
  dumpMemoryStats()

  private def touchCode() {
    println(s"Seed Values first entry is ${OptionalLong(seedValues(0)).value}")
    println(s"Seed Values Reverse first entry is ${OptionalLong(seedValuesReverse(0)).value}")

    if (OptionalLong(10).exists(x => x != x)) sys.error("")
    if (customOptionValues(0)) sys.error("Custom Option Values Not Initialized Properly")
    initMemory()
  }

  def runCustomOptionsTest(pass: Int) {
    var i = 0
    var exists = true
    val limit = iterations
    recordMemoryBefore(pass)
    while (i < limit) {
      val optional = (pass % 2: @switch) match {
        case 0 => seedValues(i)
        case 1 => seedValuesReverse(i)
      }

      (i % 3: @switch) match {
        case 0 => exists = OptionalLong(optional).exists(_ + i % 2 == 0)
        case 1 => exists = OptionalLong(optional).exists(_ * i % 2 == 0)
        case 2 => exists = OptionalLong(optional).exists(_ - i % 2 == 0)
      }

      i += 1
    }
    customOptionValues(pass) = exists
    recordMemoryAfter(pass)
  }
}
