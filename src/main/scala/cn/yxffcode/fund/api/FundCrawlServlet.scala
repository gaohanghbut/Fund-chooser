package cn.yxffcode.fund.api

import javax.servlet.annotation.WebServlet
import javax.servlet.{GenericServlet, ServletRequest, ServletResponse}

import cn.yxffcode.fund.service.{CrawlFundService, FundService}
import cn.yxffcode.fund.service.impl.{CrawlFundServiceImpl, FundServiceImpl}

/**
  * @author gaohang on 9/3/16.
  */
@WebServlet(Array("/crawl"))
class FundCrawlServlet extends GenericServlet {

  private var crawlFundService: CrawlFundService = _
  private var fundService: FundService = _

  override def init(): Unit = {
    crawlFundService = CrawlFundServiceImpl()
    fundService = FundServiceImpl()
  }

  override def service(req: ServletRequest, res: ServletResponse): Unit = {
    crawlFundService.doCrawlList
    fundService.getAllFunds.foreach(fundBrief => crawlFundService.doCrawDetail(fundBrief.fundCode))
  }
}
