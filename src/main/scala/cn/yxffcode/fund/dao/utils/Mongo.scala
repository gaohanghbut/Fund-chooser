package cn.yxffcode.fund.dao.utils

import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import cn.yxffcode.fund.mongo.SimpleObjectCodec
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries

/**
  * @author gaohang on 8/31/16.
  */
object Mongo {
  val mongoClient: MongoClient = new MongoClient
  val funddb: MongoDatabase = {
    val database: MongoDatabase = mongoClient.getDatabase("funddb")
    val registry = CodecRegistries.fromRegistries(database.getCodecRegistry,
      CodecRegistries.fromCodecs(new SimpleObjectCodec[FundBrief](classOf[FundBrief]),
        new SimpleObjectCodec[FundDetail](classOf[FundDetail])))
    database.withCodecRegistry(registry)
  }

}
