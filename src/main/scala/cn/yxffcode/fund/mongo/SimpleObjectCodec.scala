package cn.yxffcode.fund.mongo

import java.util.Date

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
          case x: Date => {
            val date: Date = new Date(x.getYear, x.getMonth, x.getDay)
            writer.writeDateTime(field.getName, date.getTime)
          }
          case x: Any => {
            if (x.isInstanceOf[Int]) {
              writer.writeInt32(field.getName, x.asInstanceOf[Int])
            }
          }
        }
      }
    })
    writer.writeEndDocument()
  }

  override def getEncoderClass: Class[A] = clazz

  override def decode(reader: BsonReader, decoderContext: DecoderContext): A = {
    val value: A = clazz.newInstance()
    reader.readStartDocument
    try {
      reader.readObjectId
      clazz.getDeclaredFields.foreach(field => {
        val c: Class[_] = field.getType
        field.setAccessible(true)
        if (c eq classOf[String]) {
          field.set(value, reader.readString(field.getName))
        } else if (c eq classOf[BigDecimal]) {
          field.set(value, BigDecimal(reader.readDouble(field.getName)))
        } else if (c eq classOf[Date]) {
          field.set(value, new Date(reader.readDateTime(field.getName)))
        } else if (c eq classOf[Int]) {
          field.set(value, reader.readInt32())
        }
      })
    } finally {
      reader.readEndDocument
    }
    value
  }
}
