package cn.yxffcode.fund.dao

import cn.yxffcode.fund.model.{FundBrief, FundDetail}

/**
  * @author gaohang on 8/30/16.
  */
trait FundDownloader {
  def downloadList(page: Page): List[FundBrief]

  def downloadDetail(fundCode: String): Option[FundDetail]
}
