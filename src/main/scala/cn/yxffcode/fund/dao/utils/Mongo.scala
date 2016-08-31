package cn.yxffcode.fund.dao.utils

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase

/**
  * @author gaohang on 8/31/16.
  */
object Mongo {
  val mongoClient: MongoClient = new MongoClient();
  val fundBriefDatabase: MongoDatabase = mongoClient.getDatabase("funddb")

}
