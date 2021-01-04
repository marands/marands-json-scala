/*
 * Copyright © 2020 Marands Inc, Majid Hatami
 */

package io.marands.scala

package object json {
  implicit def enrichJsonStringToObject(string: String): RichJsonString = new RichJsonString(string)

  implicit def enrichObjectsToString(value: AnyRef): ObjectMarshaller = new ObjectMarshaller(value)
}

package json {

  import scala.reflect.runtime.universe._

  object JSON {
    def apply(): JSON = new JSON()
  }

  class JSON {
    def asString(any: AnyRef)(implicit writer: JsonReaderWriter = JsonReaderWriter): String =
      writer.marshaller(any)

    def asPrettyString(any: AnyRef)(implicit writer: JsonReaderWriter = JsonReaderWriter): String =
      writer.marshallerPrettyPrint(any)
  }

  private[json] class ObjectMarshaller(any: AnyRef) {
    def asString(implicit writer: JsonReaderWriter = JsonReaderWriter): String =
      JSON().asString(any)

    def asPrettyString(implicit writer: JsonReaderWriter = JsonReaderWriter): String =
      JSON().asPrettyString(any)
  }

  private[json] class RichJsonString(string: String) {

    /**
      * Convert Json String to Json HashMap
      *
      * @return Map[String, Object]
      */
    def asMap: Map[String, Object] = convertTo[Map[String, Object]]

    /**
      * Convert Json String to Json Object
      *
      * @param ct Implicit TypeTag of Type A
      * @tparam A Return Type
      * @return Json Object of type 'A'
      */
    def as[A](implicit ct: TypeTag[A]): A =
      convertTo[A]

    private def convertTo[A](implicit
        writer: JsonReaderWriter = JsonReaderWriter,
        ct: TypeTag[A]
    ): A = {
      assert(typeTag[Nothing] != ct, "Caller of this method must specify the return type.")
      writer.unmarshaller(string)
    }
  }

}
