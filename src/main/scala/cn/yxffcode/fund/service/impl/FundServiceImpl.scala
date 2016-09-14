package cn.yxffcode.fund.service.impl

import java.util.Date

import cn.yxffcode.fund.dao.FundDao
import cn.yxffcode.fund.dao.impl.FundDaoImpl
import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import cn.yxffcode.fund.service.FundService
import cn.yxffcode.fund.utils.AvgProfit

import scala.collection.JavaConversions._

/**
  * @author gaohang on 9/3/16.
  */
class FundServiceImpl(val fundDao: FundDao) extends FundService {
  override def getAllFundBriefs: Iterable[FundBrief] = {
    fundDao.queryFundBriefByDate(today)
  }

  override def scoreManagerForFund(fundDetail: FundDetail): Unit = {

    val days: Int = fundDetail.managerIndependentDays

    fundDao.saveSingleFundManagerScore(fundDetail.fundCode, AvgProfit(days, fundDetail.managerIndependentProfit.doubleValue()), today, days)
  }

  override def getAllFundDetails: Iterable[FundDetail] = fundDao.queryFundDetailByDate(today)

  private def today: Date = {
    val date: Date = new Date
    new Date(date.getYear, date.getMonth, date.getDay)
  }
}

object FundServiceImpl {
  private val fundService = new FundServiceImpl(FundDaoImpl())

  def apply(): FundServiceImpl = fundService
}
