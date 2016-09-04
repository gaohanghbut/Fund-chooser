package cn.yxffcode.fund.dao.impl

import java.util
import java.util.Date

import cn.yxffcode.fund.dao.FundDao
import cn.yxffcode.fund.dao.utils.Mongo
import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import cn.yxffcode.fund.utils.Types.{FundCode, Score}
import com.google.common.collect.Lists
import com.mongodb.client.FindIterable
import org.bson.Document

import scala.collection.JavaConversions.mapAsJavaMap

/**
  * @author gaohang on 8/31/16.
  */
class FundDaoImpl extends FundDao {

  private val fundBriefCollectionName: String = "fund_brief"
  private val fundDetailCollectionName: String = "fund_detail"
  private val singleFundManagerScoreCollectionName: String = "single_fund_manager_score"

  override def saveAll(fundBriefs: Iterable[FundBrief]) = {
    val fundBriefCollect = Mongo.funddb.getCollection(fundBriefCollectionName, classOf[FundBrief])

    var briefs: util.List[FundBrief] = Lists.newArrayList()

    fundBriefs.foreach(f => briefs add f)

    fundBriefCollect.insertMany(briefs)

  }

  override def queryFundBriefByDate(date: Date): FindIterable[FundBrief] = Mongo.funddb.getCollection(fundBriefCollectionName).find(new Document("createDate", date), classOf[FundBrief])

  override def saveDetail(fundDetail: FundDetail): Unit = {
    Mongo.funddb.getCollection(fundDetailCollectionName, classOf[FundDetail]).insertOne(fundDetail)
  }

  override def queryFundDetailByDate(date: Date): FindIterable[FundDetail] = Mongo.funddb.getCollection(fundDetailCollectionName).find(new Document("createDate", date), classOf[FundDetail])

  override def saveSingleFundManagerScore(fundCode: FundCode, score: Score, createDate: Date): Unit = {
    Mongo.funddb.getCollection(singleFundManagerScoreCollectionName)
      .insertOne(new Document(Map[String, Object](
        "fundCode" -> fundCode,
        "score" -> score,
        "createDate" -> createDate
      )))
  }
}

object FundDaoImpl {
  private val fundDao: FundDao = new FundDaoImpl

  def apply(): FundDao = fundDao
}

