package cn.yxffcode.fund.akka

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import cn.yxffcode.fund.service.FundService
import cn.yxffcode.fund.service.impl.FundServiceImpl

/**
  * @author gaohang on 9/4/16.
  */
class FundBriefDeliverer(val fundService: FundService, val actorRef: ActorRef) extends Actor {
  override def receive: Receive = {
    case DeliveryFundBriefMessage =>
      var count: Int = 0
      fundService.getAllFundBriefs.foreach(fundBrief => {
        actorRef ! CrawlDetailMessage(fundBrief)
        count = count + 1
      })
      actorRef ! DeliveryFinishedMessage(count)
  }
}
