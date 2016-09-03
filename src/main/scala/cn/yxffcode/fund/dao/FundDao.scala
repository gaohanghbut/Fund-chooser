package cn.yxffcode.fund.dao

import java.util.Date

import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import com.mongodb.client.FindIterable

/**
  * @author gaohang on 8/31/16.
  */
trait FundDao {
  def saveAll(fundBriefs: Iterable[FundBrief])

  def saveDetail(fundDetail: FundDetail)

  def queryFundBriefByDate(date: Date): FindIterable[FundBrief]
}
