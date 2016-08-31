package cn.yxffcode.fund.http

import java.io.IOException
import java.util.concurrent.{ExecutionException, Future, TimeUnit}

import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils

/**
  * @author gaohang on 8/30/16.
  */
class ResponseFutureAdapter(future: Future[HttpResponse]) extends Future[String] {

  private val delegate: Future[HttpResponse] = future;

  override def isCancelled: Boolean = delegate.isCancelled

  override def get(): String = getContent(delegate.get)

  override def get(timeout: Long, unit: TimeUnit): String = getContent(delegate.get(timeout, unit))

  override def cancel(mayInterruptIfRunning: Boolean): Boolean = delegate.cancel(mayInterruptIfRunning)

  override def isDone: Boolean = delegate.isDone

  private def getContent(httpResponse: HttpResponse): String = {
    if (httpResponse.getStatusLine.getStatusCode != 200) {
      throw new ExecutionException(("请求出错:" + httpResponse)) {}
    }
    try {
      EntityUtils.toString(httpResponse.getEntity)
    } catch {
      case e: IOException => {
        throw new ExecutionException(e)
      }
    }
  }
}
