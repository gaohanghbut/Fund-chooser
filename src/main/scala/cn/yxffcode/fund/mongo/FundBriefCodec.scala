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
class FundBriefCodec extends Codec[FundBrief] {

  override def encode(writer: BsonWriter, value: FundBrief, encoderContext: EncoderContext): Unit = {
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

  override def getEncoderClass: Class[FundBrief] = classOf[FundBrief]

  override def decode(reader: BsonReader, decoderContext: DecoderContext): FundBrief = {
    val brief: FundBrief = new FundBrief
    classOf[FundBrief].getDeclaredFields.foreach(field => {
      val clazz: Class[_] = field.getClass
      field.setAccessible(true)
      if (clazz eq classOf[String]) {
        field.set(brief, reader.readString(field.getName))
      } else if (clazz eq classOf[BigDecimal]) {
        field.set(brief, BigDecimal(reader.readDouble(field.getName)))
      } else if (clazz eq classOf[Date]) {
        field.set(brief, new Date(reader.readDateTime(field.getName)))
      }
    })
    brief
  }
}
