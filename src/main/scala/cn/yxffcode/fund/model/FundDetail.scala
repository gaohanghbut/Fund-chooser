package cn.yxffcode.fund.model

import java.util.Date

/**
  * @author gaohang on 9/1/16.
  */
class FundDetail {

  var fundCode: String = _
  var managerId: String = _
  var managerName: String = _
  var workTime: String = _
  var selfProfit: Double = _
  var categoryAvgProfit: Double = _
  var stockProfit: Double = _

  override def toString = s"FundDetail(fundCode=$fundCode, managerId=$managerId, managerName=$managerName, workTime=$workTime, selfProfit=$selfProfit, categoryAvg=$categoryAvgProfit, stock=$stockProfit)"
}
