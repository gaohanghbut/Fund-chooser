package cn.yxffcode.fund.service.impl

import cn.yxffcode.fund.dao.FundDao
import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.service.AnalyzService

/**
  * @author gaohang on 9/3/16.
  */
class AnalyzServiceImpl(var fundDao: FundDao) extends AnalyzService {
  override def sort(): Seq[FundBrief] = {
    null
  }
}
