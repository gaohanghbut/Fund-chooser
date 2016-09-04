package cn.yxffcode.fund.akka

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import cn.yxffcode.fund.service.FundService
import cn.yxffcode.fund.service.impl.FundServiceImpl

/**
  * @author gaohang on 9/4/16.
  */
class SingleFundManagerAnalyzer(val fundService: FundService) extends Actor {
  override def receive: Receive = {
    case AnalyzeFundMessage(fundDetail) =>
      fundService scoreManagerForFund fundDetail
  }
}

object SingleFundManagerAnalyzer {
  def apply(): SingleFundManagerAnalyzer = new SingleFundManagerAnalyzer(FundServiceImpl())
}