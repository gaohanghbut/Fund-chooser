package cn.yxffcode.fund.akka.bootstrap

import akka.actor.{ActorSystem, Props}
import cn.yxffcode.fund.akka.{CrawlMessage, TaskActor}

/**
  * @author gaohang on 9/4/16.
  */
object ActorBoot {
  val system = ActorSystem("FundSystem")
  var taskActorRouter = system.actorOf(Props(new TaskActor), name = "taskActorRouter")

  def apply(): Unit = {
    taskActorRouter ! CrawlMessage
  }

  def destroy: Unit = {
    system.stop(taskActorRouter)
  }

  def main(args: Array[String]) {
    apply()
  }
}
