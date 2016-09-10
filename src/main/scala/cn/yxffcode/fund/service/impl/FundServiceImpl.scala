package cn.yxffcode.fund.service.impl

import java.util.Date

import cn.yxffcode.fund.dao.FundDao
import cn.yxffcode.fund.dao.impl.FundDaoImpl
import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import cn.yxffcode.fund.service.FundService
import cn.yxffcode.fund.utils.Consts
import cn.yxffcode.fund.utils.Maths._

import scala.collection.JavaConversions._

/**
  * @author gaohang on 9/3/16.
  */
class FundServiceImpl(val fundDao: FundDao) extends FundService {
  override def getAllFundBriefs: Iterable[FundBrief] = {
    fundDao.queryFundBriefByDate(today)
  }

  override def scoreManagerForFund(fundDetail: FundDetail): Unit = {

    val days: Int = manageDays(fundDetail)

    fundDao.saveSingleFundManagerScore(fundDetail.fundCode, (fundDetail.selfProfit / days).doubleValue(), today, days)
  }

  override def getAllFundDetails: Iterable[FundDetail] = fundDao.queryFundDetailByDate(today)

  private def today: Date = {
    val date: Date = new Date
    new Date(date.getYear, date.getMonth, date.getDay)
  }

  private def manageDays(fundDetail: FundDetail): Int = {
    val workTimeDesc: String = fundDetail.workTime
    val yearOff: Int = workTimeDesc.indexOf('年')
    var days: Int = 0
    if (yearOff > 0) {
      days = days + Integer.parseInt(workTimeDesc.substring(0, yearOff)) * 365
    }
    val dayOff: Int = workTimeDesc.indexOf('天')
    if (dayOff > 0) {
      val beforeDayStart: Int = workTimeDesc.indexOf('又')
      if (beforeDayStart >= 0) {
        days = days + Integer.parseInt(workTimeDesc.substring(beforeDayStart + 1, dayOff))
      } else {
        days = days + Integer.parseInt(workTimeDesc.substring(0, dayOff))
      }
    }
    days
  }
}

object FundServiceImpl {
  private val fundService = new FundServiceImpl(FundDaoImpl())

  def apply(): FundServiceImpl = fundService
}