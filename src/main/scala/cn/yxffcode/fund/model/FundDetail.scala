package cn.yxffcode.fund.model

import java.util.Date

import cn.yxffcode.fund.utils.Types.FundCode

/**
  * @author gaohang on 9/1/16.
  */
class FundDetail {

  var fundCode: FundCode = _
  var managerId: String = _
  var managerName: String = _
  var workTime: String = _
  var selfProfit: BigDecimal = _
  var categoryAvgProfit: BigDecimal = _
  var stockProfit: BigDecimal = _
  var createDate: Date = new Date()

  override def toString = s"FundDetail(fundCode=$fundCode, managerId=$managerId, managerName=$managerName, workTime=$workTime, selfProfit=$selfProfit, categoryAvg=$categoryAvgProfit, stock=$stockProfit)"
}
