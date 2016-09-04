package cn.yxffcode.fund.akka

import akka.actor.{Actor, Props}
import akka.actor.Actor.Receive
import akka.routing.RoundRobinPool

/**
  * @author gaohang on 9/4/16.
  */
class AnalyzerTask extends Actor {

  private val singleFundManagerScoreRouter = context.actorOf(Props(SingleFundManagerAnalyzer(self)).withRouter(RoundRobinPool(1)), name = "singleFundManagerScoreRouter")

  override def receive: Receive = {
    case AnalyzeMessage => singleFundManagerScoreRouter.forward(AnalyzeMessage)
    case SingleFundManagerScoreAnalyzeFinishedMessage =>
  }
}
