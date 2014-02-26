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

package org.nalloc.bitb.kcits.optional

class OptionalSpec extends OptionalTypeSuite {

  property("The empty value maps to the empty value of its target type") {
    forAll(mapFunctionsFrom[String]) { functions =>
      import functions._

      Optional.empty[String].map(mapToByte) shouldBe OptionalByte.empty
      Optional.empty[String].map(mapToShort) shouldBe OptionalShort.empty
      Optional.empty[String].map(mapToInt) shouldBe OptionalInt.empty
      Optional.empty[String].map(mapToLong) shouldBe OptionalLong.empty
      Optional.empty[String].map(mapToFloat).isEmpty shouldBe true
      Optional.empty[String].map(mapToDouble).isEmpty shouldBe true
      Optional.empty[String].map(mapToString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values map using the passed in function") {
    forAll(strings, mapFunctionsFrom[String]) { (value, functions) =>
      import functions._

      Optional(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
      Optional(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
      Optional(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
      Optional(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
      Optional(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
      Optional(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
      Optional(value).map(mapToString) shouldBe Optional(mapToString(value))
    }
  }

  property("foreach on the empty value is a no-op") {
    Optional.empty[String].foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: String =>
      whenever(x != null) {
        var executed = false
        Optional(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }

  property("exists on the empty value always returns false") {
    Optional.empty[String].exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: String =>
      whenever(x != null) {
        Optional(x).exists(x => x == x) shouldBe true
        Optional(x).exists(x => x == x + "foo") shouldBe false
      }
    }
  }

  property("filter on the empty value always returns the empty value") {
    Optional.empty[String].filter(_ => false) shouldBe Optional.empty[String]
    Optional.empty[String].filter(_ => true) shouldBe Optional.empty[String]
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: String =>
      whenever(x != null) {
        Optional(x).filter(x => x == x) shouldBe Optional(x)
        Optional(x).filter(x => x == x + "foo") shouldBe Optional.empty[String]
      }
    }
  }

  property("orElse on the empty value returns the passed in alternative") {
    Optional.empty[String].orElse("foo") shouldBe "foo"
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: String =>
      whenever(x != null) {
        Optional(x).orElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[String]) { functions =>
      import functions._

      Optional.empty[String].flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      Optional.empty[String].flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      Optional.empty[String].flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      Optional.empty[String].flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      Optional.empty[String].flatMap(mapToOptionalFloat).isEmpty shouldBe true
      Optional.empty[String].flatMap(mapToOptionalDouble).isEmpty shouldBe true
      Optional.empty[String].flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(strings, flatMapFunctionsFrom[String]) { (value, functions) =>
      import functions._

      Optional(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
      Optional(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
      Optional(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
      Optional(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
      Optional(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
      Optional(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
      Optional(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
    }
  }

  property("The empty value always applies the ifEmpty portion of a fold") {
    forAll { (ifEmpty: String, ifNotEmpty: String) =>
      whenever(ifEmpty != ifNotEmpty) {
        Optional.empty[String].fold(ifEmpty)(_ => ifNotEmpty) shouldBe ifEmpty
      }
    }
  }

  property("Non empty values always apply the map portion of a fold") {
    forAll(strings, strings, mapFunctionsFrom[String]) { (ifEmpty, value, functions) =>
      whenever(value != null) {
        import functions._

        Optional(value).fold(ifEmpty)(mapToString) shouldBe mapToString(value)
      }
    }
  }
}
