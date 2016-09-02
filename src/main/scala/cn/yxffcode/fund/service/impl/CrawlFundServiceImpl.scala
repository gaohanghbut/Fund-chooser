package cn.yxffcode.fund.service.impl

import java.util.Date

import cn.yxffcode.freetookit.tools.PageResolver
import cn.yxffcode.fund.dao.impl.{FundBriefDaoImpl, FundDownloaderImpl}
import cn.yxffcode.fund.dao.utils.Mongo
import cn.yxffcode.fund.dao.{FundBriefDao, FundDownloader, Page}
import cn.yxffcode.fund.http.SyncHttpExecutor
import cn.yxffcode.fund.model.FundBrief
import cn.yxffcode.fund.service.CrawlFundService

import scala.collection.JavaConversions._

/**
  * @author gaohang on 8/30/16.
  */
class CrawlFundServiceImpl(val fld: FundDownloader, val fbdao: FundBriefDao) extends CrawlFundService {

  private val fundListDownloader: FundDownloader = fld
  private val fundBriefDao: FundBriefDao = fbdao

  private val pageSize: Int = 100

  override def doCrawlList = fundBriefDao.saveAll(new PageResolver[FundBrief](pageSize) {
    override def nextPage(i: Int) = fundListDownloader.downloadList(new Page(i / pageSize + 1, pageSize))
  }.getAll)

  override def doCrawDetail(fundCode: String): Unit = {
    fundBriefDao.queryByDate(new Date).foreach(fundBrief => {

    })
  }
}

object CrawlFundServiceImpl {
  def main(args: Array[String]) {
    val executor = new SyncHttpExecutor
    val downloader = new FundDownloaderImpl(executor);
    val fundBriefDao = new FundBriefDaoImpl

    val service: CrawlFundServiceImpl = new CrawlFundServiceImpl(downloader, fundBriefDao)

    service.doCrawlList

    executor.destroy
    Mongo.mongoClient.close
  }
}
