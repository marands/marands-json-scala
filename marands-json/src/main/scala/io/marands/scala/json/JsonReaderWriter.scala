/*
 * Copyright © 2020 Marands Inc, Majid Hatami
 */

package io.marands.scala.json

import java.lang.reflect.{ ParameterizedType, Type => JType }
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.reflect.runtime.universe._

object JsonReaderWriter extends JsonReaderWriter {
  lazy val defaultObjectMapper: ObjectMapper = new ObjectMapper()
    .registerModule(DefaultScalaModule)
}

trait JsonReaderWriter {
  import JsonReaderWriter._

  def typeReference[T: TypeTag]: TypeReference[T] = {
    val __type = typeTag[T]
    val mirror = __type.mirror

    def mapType(_type: Type): JType =
      if (_type.typeArgs.isEmpty)
        mirror.runtimeClass(_type)
      else
        new ParameterizedType {
          def getRawType: RuntimeClass             = mirror.runtimeClass(_type)
          def getActualTypeArguments: Array[JType] = _type.typeArgs.map(mapType).toArray
          def getOwnerType                         = null
        }

    new TypeReference[T] {
      override def getType: JType = mapType(__type.tpe)
    }
  }

  implicit def marshaller(value: AnyRef)(implicit
      objectMapper: ObjectMapper = defaultObjectMapper
  ): String =
    objectMapper
      .writeValueAsString(value)

  implicit def marshallerPrettyPrint(value: AnyRef)(implicit
      objectMapper: ObjectMapper = defaultObjectMapper
  ): String =
    objectMapper
      //.setDefaultPrettyPrinter(new DefaultPrettyPrinter())
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(value)

  implicit def unmarshaller[A](
      data: String
  )(implicit objectMapper: ObjectMapper = defaultObjectMapper, ct: TypeTag[A]): A = {
    assert(typeTag[Nothing] != ct, "Caller of this method must specify the return type.")
    ct match {
      case t if t == typeTag[String] =>
        data.asInstanceOf[A]
      case _ =>
        val objType = typeReference[A]
        val res     = objectMapper.readValue(data, objType)
        res
    }
  }
}
