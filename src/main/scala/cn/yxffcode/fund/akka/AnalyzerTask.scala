package cn.yxffcode.fund.akka

import akka.actor.{Actor, Props}
import akka.routing.RoundRobinPool
import cn.yxffcode.fund.service.impl.FundServiceImpl

/**
  * @author gaohang on 9/4/16.
  */
class AnalyzerTask(private val numOfScoreActor: Int) extends Actor {

  private val singleFundManagerScoreRouter = context.actorOf(Props(classOf[SingleFundManagerAnalyzer], FundServiceImpl()).withRouter(RoundRobinPool(numOfScoreActor)), name = "singleFundManagerScoreRouter")

  private val fundDetailLoaderRouter = context.actorOf(Props(classOf[FundDetailLoader], FundServiceImpl(), self).withRouter(RoundRobinPool(1)), name = "fundDetailLoaderRouter")

  override def receive: Receive = {
    case LoadFundDetailMessage => fundDetailLoaderRouter ! LoadFundDetailMessage
    case AnalyzeFundMessage(fundDetail) => singleFundManagerScoreRouter ! AnalyzeFundMessage(fundDetail)
  }
}

object AnalyzerTask {
  def apply(numOfScoreActor: Int): AnalyzerTask = new AnalyzerTask(numOfScoreActor)
}
