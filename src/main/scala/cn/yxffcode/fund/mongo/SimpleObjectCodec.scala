package cn.yxffcode.fund.mongo

import java.lang.reflect.Field
import java.util.Date

import cn.yxffcode.freetookit.jackson.TypeReference
import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.utils.Jsons._
import org.bson.codecs.{Codec, DecoderContext, EncoderContext}
import org.bson.{BsonReader, BsonWriter}

/**
  * @author gaohang on 9/1/16.
  */
class SimpleObjectCodec[A](clazz: Class[A]) extends Codec[A] {

  override def encode(writer: BsonWriter, value: A, encoderContext: EncoderContext): Unit = {
    if (value == null) {
      return
    }

    writer.writeStartDocument();
    value.getClass.getDeclaredFields.foreach(field => {
      field.setAccessible(true)
      val v = field.get(value)
      if (v != null) {
        v match {
          case x: BigDecimal => writer.writeDouble(field.getName, x.doubleValue())
          case x: String => writer.writeString(field.getName, x)
          case x: Date => writer.writeDateTime(field.getName, x.getTime)
        }
      }
    })
    writer.writeEndDocument()
  }

  override def getEncoderClass: Class[A] = clazz

  override def decode(reader: BsonReader, decoderContext: DecoderContext): A = {
    val value: A = clazz.newInstance()

    classOf[FundBrief].getDeclaredFields.foreach(field => {
      val clazz: Class[_] = field.getClass
      field.setAccessible(true)
      if (clazz eq classOf[String]) {
        field.set(value, reader.readString(field.getName))
      } else if (clazz eq classOf[BigDecimal]) {
        field.set(value, BigDecimal(reader.readDouble(field.getName)))
      } else if (clazz eq classOf[Date]) {
        field.set(value, new Date(reader.readDateTime(field.getName)))
      }
    })
    value
  }
}