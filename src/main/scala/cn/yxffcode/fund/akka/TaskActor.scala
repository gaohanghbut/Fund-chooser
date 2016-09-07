package cn.yxffcode.fund.akka

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive
import akka.routing.RoundRobinPool

/**
  * @author gaohang on 9/4/16.
  */
class TaskActor extends Actor {

  private val numOfDetailCrawlerActor: Int = 2

  private val numOfScoreActor: Int = Runtime.getRuntime.availableProcessors() * 2

  private val crawlTaskRouter = context.actorOf(Props(CrawlTask(numOfDetailCrawlerActor, self)).withRouter(RoundRobinPool(1)), name = "crawlTaskRouter")
  private val analyzerTaskRouter = context.actorOf(Props(AnalyzerTask(numOfScoreActor)).withRouter(RoundRobinPool(1)), name = "analyzerTaskRouter")

  override def receive: Receive = {
    case CrawlMessage => crawlTaskRouter ! CrawlMessage
    case CrawlFinishedMessage => analyzerTaskRouter ! LoadFundDetailMessage
  }
}
