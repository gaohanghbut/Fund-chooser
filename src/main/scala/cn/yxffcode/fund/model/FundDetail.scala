package cn.yxffcode.fund.model

import java.util.Date

import akka.util.Collections.EmptyImmutableSeq
import cn.yxffcode.fund.utils.Types.FundCode

/**
  * @author gaohang on 9/1/16.
  */
class FundDetail {

  var fundCode: FundCode = _
  var managerId: String = _
  var managerName: String = _
  var workTime: String = _
  var selfProfit: BigDecimal = 0
  var categoryAvgProfit: BigDecimal = 0
  var stockProfit: BigDecimal = 0
  /**
    * 基金经理独立管理此基金期间基金的收益
    */
  var managerIndependentProfit: BigDecimal = 0
  /**
    * 基金经理独立管理此基金的天数
    */
  var managerIndependentDays: Int = 0
  var createDate: Date = new Date()
  /**
    * 重仓股
    */
  var stocks: Seq[String] = EmptyImmutableSeq

  override def toString = s"FundDetail(fundCode=$fundCode, managerId=$managerId, managerName=$managerName, workTime=$workTime, selfProfit=$selfProfit, categoryAvgProfit=$categoryAvgProfit, stockProfit=$stockProfit, managerIndependentProfit=$managerIndependentProfit, managerIndependentDays=$managerIndependentDays, createDate=$createDate, stocks=$stocks)"
}
