package cn.yxffcode.fund.service.impl

import cn.yxffcode.freetookit.tools.PageResolver
import cn.yxffcode.fund.dao.impl.{FundBriefDaoImpl, FundListDownloaderImpl}
import cn.yxffcode.fund.dao.{FundBriefDao, FundListDownloader, Page}
import cn.yxffcode.fund.http.SyncHttpExecutor
import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.service.CrawlFundService

import scala.collection.JavaConversions._

/**
  * @author gaohang on 8/30/16.
  */
class CrawlFundServiceImpl(val fld: FundListDownloader, val fbdao: FundBriefDao) extends CrawlFundService {

  private val fundListDownloader: FundListDownloader = fld
  private val fundBriefDao: FundBriefDao = fbdao

  private val pageSize: Int = 100

  override def doCrawl = fundBriefDao.saveAll(new PageResolver[FundBrief](pageSize) {
    override def nextPage(i: Int) = fundListDownloader.download(new Page(i / pageSize + 1, pageSize))
  }.getAll)
}

object CrawlFundServiceImpl {
  def main(args: Array[String]) {
    val executor = new SyncHttpExecutor
    val downloader = new FundListDownloaderImpl(executor);
    val fundBriefDao = new FundBriefDaoImpl

    val service: CrawlFundServiceImpl = new CrawlFundServiceImpl(downloader, fundBriefDao)

    service.doCrawl

    executor.destroy
  }
}
