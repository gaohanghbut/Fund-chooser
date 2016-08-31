package cn.yxffcode.fund.dao.impl

import java.util.Date

import cn.yxffcode.fund.dao.FundBriefDao
import cn.yxffcode.fund.dao.utils.Mongo
import cn.yxffcode.fund.model.FundBrief
import com.mongodb.client.{FindIterable, MongoCollection}
import org.bson.Document

/**
  * @author gaohang on 8/31/16.
  */
class FundBriefDaoImpl extends FundBriefDao {

  private val fundBriefCollectionName: String = "fund_brief"

  override def saveAll(fundBriefs: Iterable[FundBrief]) = {
    val fundBriefCollect: MongoCollection[FundBrief] = Mongo.fundBriefDatabase.getCollection(fundBriefCollectionName, classOf[FundBrief])
    fundBriefs.foreach(fundBrief => fundBriefCollect.insertOne(fundBrief))
  }

  override def queryByDate(date: Date): FindIterable[FundBrief] = Mongo.fundBriefDatabase.getCollection(fundBriefCollectionName).find(new Document("createDate", date), classOf[FundBrief])

}
