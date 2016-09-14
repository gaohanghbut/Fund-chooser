package cn.yxffcode.fund.akka.bootstrap

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import cn.yxffcode.fund.akka.{CrawlFinishedMessage, CrawlMessage, TaskActor}

import scala.concurrent.Future

/**
  * @author gaohang on 9/4/16.
  */
object ActorBoot {
  val system = ActorSystem("FundSystem")
  var taskActorRouter = system.actorOf(Props(new TaskActor), name = "taskActorRouter")

  def apply() = taskActorRouter ! CrawlMessage
  def apply(timeout: Timeout): Future[Any] = taskActorRouter.?(CrawlMessage)(timeout)

  def destroy: Unit = {
    system.terminate()
  }

  def main(args: Array[String]) {
    apply()
  }
}
