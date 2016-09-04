package cn.yxffcode.fund.akka

import cn.yxffcode.fund.model.FundBrief

/**
  * @author gaohang on 9/4/16.
  */
sealed trait Message

case class CrawlMessage() extends Message

case class CrawlFinishedMessage() extends Message

case class CrawlListMessage() extends Message

case class ListFinishedMessage() extends Message

case class CrawlDetailMessage(val fundBrief: FundBrief) extends Message

case class AnalyzeMessage() extends Message

case class DeliveryFundBriefMessage() extends Message

case class DeliveryFinishedMessage(val deliverCount: Int) extends Message

case class DetailFinishedMessage() extends Message