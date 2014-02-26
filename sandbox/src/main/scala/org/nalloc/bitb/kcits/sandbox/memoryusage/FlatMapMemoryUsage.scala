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
import org.nalloc.bitb.kcits.optional._
import scala.annotation.switch

object FlatMapMemoryUsage extends MemoryUsageApp {
  protected def passes = 25
  private[this] val passes_ = passes
  private[this] val iterations = 1e7.toInt
  private val random = new Random
  private[this] val seedValues = (1 to iterations).map(_ => random.nextLong()).toArray
  private[this] val seedValuesReverse = seedValues.reverse

  private[this] val customOptionValues = new Array[Long](passes)

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

  println(customOptionValues.sum)
  dumpMemoryStats()

  private def touchCode() {
    println(s"Seed Values first entry is ${OptionalLong(seedValues(0)).value}")
    println(s"Seed Values Reverse first entry is ${OptionalLong(seedValuesReverse(0)).value}")

    if (OptionalLong(10).flatMap(x => OptionalLong(x + 5)).get != 15) sys.error("")
    if (customOptionValues(0) != 0) sys.error("")
    initMemory()
  }

  def runCustomOptionsTest(pass: Int) {
    var i = 0
    var sum = 0L
    val limit = iterations
    recordMemoryBefore(pass)
    while (i < limit) {
      val optional = (pass % 2: @switch) match {
        case 0 => seedValues(i)
        case 1 => seedValuesReverse(i)
      }

      (i % 3: @switch) match {
        case 0 => sum += OptionalLong(optional).flatMap(x => OptionalLong(x + i)).get
        case 1 => sum += OptionalLong(optional).flatMap(x => OptionalLong(x * i)).get
        case 2 => sum += OptionalLong(optional).flatMap(x => OptionalLong(x - i)).get
      }

      i += 1
    }
    customOptionValues(pass) = sum
    recordMemoryAfter(pass)
  }
}
