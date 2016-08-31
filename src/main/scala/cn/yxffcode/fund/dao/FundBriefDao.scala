package cn.yxffcode.fund.dao

import java.util.Date

import cn.yxffcode.fund.model.FundBrief
import com.mongodb.client.FindIterable

/**
  * @author gaohang on 8/31/16.
  */
trait FundBriefDao {
  def saveAll(fundBriefs: Iterable[FundBrief])

  def queryByDate(date: Date): FindIterable[FundBrief]
}
