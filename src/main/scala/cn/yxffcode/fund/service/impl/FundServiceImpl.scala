package cn.yxffcode.fund.service.impl

import java.util.Date

import cn.yxffcode.fund.dao.FundDao
import cn.yxffcode.fund.dao.impl.FundDaoImpl
import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.service.FundService

import scala.collection.JavaConversions._

/**
  * @author gaohang on 9/3/16.
  */
class FundServiceImpl(val fundDao: FundDao) extends FundService {
  override def getAllFunds: Iterable[FundBrief] = {
    val date: Date = new Date
    fundDao.queryFundBriefByDate(new Date(date.getYear, date.getMonth, date.getDay))
  }
}

object FundServiceImpl {
  private val fundService = new FundServiceImpl(FundDaoImpl())

  def apply(): FundServiceImpl = fundService
}