package cn.yxffcode.fund.dao

import cn.yxffcode.fund.model.FundBrief

/**
  * @author gaohang on 8/30/16.
  */
trait FundListDownloader {
  def download(page: Page): List[FundBrief]
}
