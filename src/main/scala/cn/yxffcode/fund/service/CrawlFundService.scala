package cn.yxffcode.fund.service

import cn.yxffcode.fund.dao.Page

/**
  * @author gaohang on 8/30/16.
  */
trait CrawlFundService {
  def doCrawlList(page: Page)

  def doCrawDetail(fundCode: String)
}
