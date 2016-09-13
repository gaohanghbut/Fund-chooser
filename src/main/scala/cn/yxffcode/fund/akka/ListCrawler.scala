package cn.yxffcode.fund.akka

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import cn.yxffcode.fund.service.CrawlFundService
import cn.yxffcode.fund.service.impl.CrawlFundServiceImpl
import cn.yxffcode.fund.utils.Consts

/**
  * @author gaohang on 9/4/16.
  */
class ListCrawler(val crawlFundService: CrawlFundService, val actorRef: ActorRef) extends Actor {

  override def receive: Receive = {
    case CrawlListMessage(page) =>
      crawlFundService doCrawlList page
      if (page.page == Consts.listTotalPage) {
        actorRef ! ListFinishedMessage
      }
  }
}
