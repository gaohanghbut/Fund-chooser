package cn.yxffcode.fund.akka

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive
import akka.routing.RoundRobinPool

/**
  * @author gaohang on 9/4/16.
  */
class AnalyzerTask(private val numOfScoreActor: Int) extends Actor {

  private val singleFundManagerScoreRouter = context.actorOf(Props(SingleFundManagerAnalyzer()).withRouter(RoundRobinPool(numOfScoreActor)), name = "singleFundManagerScoreRouter")

  private val fundDetailLoaderRouter = context.actorOf(Props(FundDetailLoader(self)).withRouter(RoundRobinPool(1)), name = "fundDetailLoaderRouter")

  override def receive: Receive = {
    case LoadFundDetailMessage => fundDetailLoaderRouter ! AnalyzeFundMessage
    case AnalyzeFundMessage(fundDetail) => singleFundManagerScoreRouter ! AnalyzeFundMessage(fundDetail)
  }
}

object AnalyzerTask {
  def apply(numOfScoreActor: Int): AnalyzerTask = new AnalyzerTask(numOfScoreActor)
}
