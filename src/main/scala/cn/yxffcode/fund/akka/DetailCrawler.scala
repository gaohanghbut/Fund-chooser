package cn.yxffcode.fund.akka

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import cn.yxffcode.fund.api.FundCrawlServlet
import cn.yxffcode.fund.service.CrawlFundService
import cn.yxffcode.fund.service.impl.CrawlFundServiceImpl

/**
  * @author gaohang on 9/4/16.
  */
class DetailCrawler(val crawlFundService: CrawlFundService, val actorRef: ActorRef) extends Actor {
  override def receive: Receive = {
    case CrawlDetailMessage(fundBrief) =>
      try {
        crawlFundService.doCrawDetail(fundBrief.fundCode)
      } finally {
        actorRef ! DetailFinishedMessage
      }
  }
}
