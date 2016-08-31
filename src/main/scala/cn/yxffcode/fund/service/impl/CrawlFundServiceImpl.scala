package cn.yxffcode.fund.service.impl

import java.util

import cn.yxffcode.freetookit.tools.PageResolver
import cn.yxffcode.fund.http.SyncHttpExecutor
import cn.yxffcode.fund.io.impl.FundListDownloaderImpl
import cn.yxffcode.fund.io.{FundListDownloader, Page}
import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.service.CrawlFundService

import scala.collection.JavaConversions._

/**
  * @author gaohang on 8/30/16.
  */
class CrawlFundServiceImpl(val fld: FundListDownloader) extends CrawlFundService {

  private val fundListDownloader: FundListDownloader = fld

  override def doCrawl: Unit = {
    val fundBriefs = new PageResolver[FundBrief](100) {
      override def nextPage(i: Int): util.List[FundBrief] = fundListDownloader.download(new Page(i / 100 + 1, 100))
    }.getAll

    for (fundBrief <- fundBriefs) {
      println(fundBrief)
    }
  }
}
