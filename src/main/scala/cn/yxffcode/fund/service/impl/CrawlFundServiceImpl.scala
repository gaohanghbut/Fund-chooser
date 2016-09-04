package cn.yxffcode.fund.service.impl

import java.util.Date

import cn.yxffcode.freetookit.tools.PageResolver
import cn.yxffcode.fund.dao.impl.{FundDaoImpl, FundDownloaderImpl}
import cn.yxffcode.fund.dao.utils.Mongo
import cn.yxffcode.fund.dao.{FundDao, FundDownloader, Page}
import cn.yxffcode.fund.http.SyncHttpExecutor
import cn.yxffcode.fund.model.{FundBrief, FundDetail}
import cn.yxffcode.fund.service.CrawlFundService

import scala.collection.JavaConversions._

/**
  * @author gaohang on 8/30/16.
  */
class CrawlFundServiceImpl(val downloader: FundDownloader, val fundDao: FundDao) extends CrawlFundService {

  private val pageSize: Int = 100

  override def doCrawlList(page: Page) = fundDao.saveAll(downloader.downloadList(page))

  override def doCrawDetail(fundCode: String): Unit = {
    val detailOption: Option[FundDetail] = downloader.downloadDetail(fundCode)
    if (detailOption.isEmpty) {
      return
    }
    fundDao saveDetail detailOption.get
  }
}

object CrawlFundServiceImpl {

  private val httpExecutor: SyncHttpExecutor = new SyncHttpExecutor

  private val crawlFundService: CrawlFundService = new CrawlFundServiceImpl(new FundDownloaderImpl(httpExecutor), FundDaoImpl())

  def apply(): CrawlFundService = crawlFundService

  def destroy = httpExecutor.destroy
}

