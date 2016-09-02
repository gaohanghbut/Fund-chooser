package cn.yxffcode.fund.service

/**
  * @author gaohang on 8/30/16.
  */
trait CrawlFundService {
  def doCrawlList

  def doCrawDetail(fundCode: String)
}
