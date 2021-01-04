/*
 * Copyright © 2020 Marands Inc, Majid Hatami
 */

package io.marands.scala.json

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

object TestClasses {
  case class BasicJsonStringModel(
      itemString: String,
      itemLongString: String,
      itemInteger: Int,
      itemDouble: Double,
      weekDay: String
  )

  object Weekday extends Enumeration {
    type Type = Value
    val saturday: TestClasses.Weekday.Value = Value("saturday")
    val sunday: TestClasses.Weekday.Value   = Value("sunday")
  }
  class WeekdayType extends TypeReference[Weekday.type]

  case class EnumJsonStringModel(
      itemString: String,
      itemLongString: String,
      itemInteger: Int,
      itemDouble: Double,
      @JsonScalaEnumeration(classOf[WeekdayType]) weekDay: Weekday.Type
  )

}

class BasicFormatsSpec extends AnyWordSpec {
  import TestClasses._

  val basicJsonModelString1FileName: String = "json/basic_json_string_model1.json"
  val basicJsonModelString1: String =
    Source.fromResource(basicJsonModelString1FileName).getLines.mkString

  "Test" should {
    "Convert JsonString to Hash Map" in {
      val jsonMap = basicJsonModelString1.asMap
      //println(jsonMap)
      assert(jsonMap != null)
    }

    "Convert JsonString to Scala Case Class" in {
      val model = basicJsonModelString1.as[BasicJsonStringModel]
      //println(model)
      assert(model != null, "Parsed Json string to Scala Object must not be null.")
    }

    "Pretty Print Scala Object" in {
      val model = basicJsonModelString1.as[BasicJsonStringModel]
      val pp    = model.asPrettyString
      assert(pp != null, "Unable to pretty print Json Object")
    }

    "Convert JsonString to Scala Case class with Enum" in {
      val model = basicJsonModelString1.as[EnumJsonStringModel]
      println(model.asPrettyString)
      assert(model != null, "Parsed Json string to Scala Object must not be null.")

      assert(model.weekDay == Weekday.sunday, "Enum value is not equal to the expected value.")
    }
  }
}
