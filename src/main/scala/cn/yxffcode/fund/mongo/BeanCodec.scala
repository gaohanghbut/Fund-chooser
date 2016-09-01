package cn.yxffcode.fund.mongo

import cn.yxffcode.freetookit.jackson.TypeReference
import cn.yxffcode.fund.utils.Jsons._
import org.bson.codecs.{Codec, DecoderContext, EncoderContext}
import org.bson.{BsonReader, BsonWriter}

/**
  * @author gaohang on 9/1/16.
  */
class BeanCodec[T](val clazz: Class[T]) extends Codec[T] {

  val encoderClass: Class[T] = clazz

  override def encode(writer: BsonWriter, value: T, encoderContext: EncoderContext): Unit = {
    if (value == null) {
      return
    }

    val content: Map[String, String] = value.toJson.mapJson(new TypeReference[Map[String, String]])
    writer.writeStartDocument();
    content.foreach(en => writer.writeString(en._1, en._2))
    writer.writeEndDocument();
  }

  override def getEncoderClass: Class[T] = encoderClass

  override def decode(reader: BsonReader, decoderContext: DecoderContext): T = ???
}
