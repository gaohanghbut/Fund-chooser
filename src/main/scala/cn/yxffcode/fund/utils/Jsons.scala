package cn.yxffcode.fund.utils

import cn.yxffcode.freetookit.jackson.TypeReference
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
  * @author gaohang on 8/30/16.
  */
object Jsons {

  private val objectMapper = {
    val mapper: ObjectMapper = new ObjectMapper
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.registerModule(DefaultScalaModule)
    mapper
  }

  implicit class JsonTransformer(private val self: Any) extends AnyVal {
    @inline implicit def toJson: String = Jsons.objectMapper.writeValueAsString(self)
  }

  implicit class String2Json(private val self: String) extends AnyVal {
    @inline implicit def mapJson[T](clazz: Class[T]): T = Jsons.objectMapper.readValue(self, clazz)

    @inline implicit def mapJson[T](typeRef: TypeReference[T]): T = Jsons.objectMapper.readValue(self, typeRef)
  }

}
