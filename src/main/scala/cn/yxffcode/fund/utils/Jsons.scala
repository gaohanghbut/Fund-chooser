package cn.yxffcode.fund.utils

import cn.yxffcode.freetookit.jackson.TypeReference
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/**
  * @author gaohang on 8/30/16.
  */
object Jsons {

  private val objectMapper = initObjectMapper

  private def initObjectMapper: ObjectMapper = {
    val mapper: ObjectMapper = new ObjectMapper
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.registerModule(DefaultScalaModule)
  }

  implicit class JsonTransformer(private val self: AnyRef) extends AnyVal {
    @inline implicit def toJson: String = Jsons.objectMapper.writeValueAsString(self)
  }

  implicit class String2Json(private val self: String) extends AnyVal {
    @inline implicit def mapJson[T](clazz: Class[T]): T = Jsons.objectMapper.readValue(self, clazz)

    @inline implicit def mapJson[T](typeRef: TypeReference[T]): T = Jsons.objectMapper.readValue(self, typeRef)
  }

}
