package cn.yxffcode.fund.akka

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import cn.yxffcode.fund.service.FundService
import cn.yxffcode.fund.service.impl.FundServiceImpl

/**
  * @author gaohang on 9/4/16.
  */
class FundDetailLoader(private val fundService: FundService, private val actorRef: ActorRef) extends Actor {
  override def receive: Receive = {
    case LoadFundDetailMessage =>
      fundService.getAllFundDetails.foreach(fundDetail => actorRef ! AnalyzeFundMessage(fundDetail))
  }
}

object FundDetailLoader {
  def apply(actorRef: ActorRef): FundDetailLoader = new FundDetailLoader(FundServiceImpl(), actorRef)
}
