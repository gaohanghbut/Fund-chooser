package cn.yxffcode.fund.dao.utils

import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.mongo.FundBriefCodec
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries

/**
  * @author gaohang on 8/31/16.
  */
object Mongo {
  val mongoClient: MongoClient = new MongoClient
  val fundBriefDatabase: MongoDatabase = {
    val database: MongoDatabase = mongoClient.getDatabase("funddb")
    val registry = CodecRegistries.fromRegistries(database.getCodecRegistry,
      CodecRegistries.fromCodecs(new FundBriefCodec))
    database.withCodecRegistry(registry)
  }

}
