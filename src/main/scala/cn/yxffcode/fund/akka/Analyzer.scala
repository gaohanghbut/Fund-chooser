package cn.yxffcode.fund.akka

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * @author gaohang on 9/4/16.
  */
class Analyzer extends Actor {
  override def receive: Receive = {
    case AnalyzeMessage => null
  }
}
