package cn.yxffcode.fund.dao.impl

import java.util
import java.util.Date

import cn.yxffcode.fund.dao.FundDao
import cn.yxffcode.fund.dao.utils.Mongo
import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import com.google.common.collect.{Iterables, Lists}
import com.mongodb.client.{FindIterable, MongoCollection}
import org.bson.Document

/**
  * @author gaohang on 8/31/16.
  */
class FundDaoImpl extends FundDao {

  private val fundBriefCollectionName: String = "fund_brief"
  private val fundDetailCollectionName: String = "fund_detail"

  override def saveAll(fundBriefs: Iterable[FundBrief]) = {
    val fundBriefCollect = Mongo.funddb.getCollection(fundBriefCollectionName, classOf[FundBrief])

    var briefs: util.List[FundBrief] = Lists.newArrayList()

    fundBriefs.foreach(f => briefs add f)

    fundBriefCollect.insertMany(briefs)

  }

  override def queryFundBriefByDate(date: Date): FindIterable[FundBrief] = Mongo.funddb.getCollection(fundBriefCollectionName).find(new Document("createDate", date), classOf[FundBrief])

  override def saveDetail(fundDetail: FundDetail): Unit = {
    val collection = Mongo.funddb.getCollection(fundDetailCollectionName, classOf[FundDetail])
    collection.insertOne(fundDetail)
  }
}
