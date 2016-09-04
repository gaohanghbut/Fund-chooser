package cn.yxffcode.fund.akka

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool
import cn.yxffcode.fund.dao.Page
import cn.yxffcode.fund.service.CrawlFundService

/**
  * @author gaohang on 9/4/16.
  */
class CrawlTask(val numOfDetailCrawlerActor: Int,
                val actorRef: ActorRef) extends Actor {

  private val listRouter = context.actorOf(Props(ListCrawler(self)).withRouter(RoundRobinPool(2)), name = "listCrawlerRouter")

  private val detailRouter = context.actorOf(Props(DetailCrawler()).withRouter(RoundRobinPool(numOfDetailCrawlerActor)), name = "detailCrawlerRouter")

  private val fundBriefDelivererRouter = context.actorOf(Props(FundBriefDeliverer(self)).withRouter(RoundRobinPool(1)), name = "fundBriefDelivererRouter")

  private var deliverCount: Int = 0
  private var detailCrawlCount: Int = 0

  override def receive: Receive = {
    case CrawlMessage =>
      for (i <- 1 to 36) {
        listRouter ! CrawlListMessage(new Page(i, 100))
      }
    case ListFinishedMessage => fundBriefDelivererRouter ! DeliveryFundBriefMessage
    case CrawlDetailMessage(fundBrief) => detailRouter ! CrawlDetailMessage(fundBrief)
    case DeliveryFinishedMessage(deliverCount) => this.deliverCount = deliverCount
    case DetailFinishedMessage() =>
      this.detailCrawlCount = this.detailCrawlCount + 1
      if (deliverCount == detailCrawlCount) {
        actorRef ! CrawlFinishedMessage
      }
  }
}

object CrawlTask {
  def apply(numOfDetailCrawlerActor: Int, actorRef: ActorRef): CrawlTask = new CrawlTask(numOfDetailCrawlerActor, actorRef)
}
