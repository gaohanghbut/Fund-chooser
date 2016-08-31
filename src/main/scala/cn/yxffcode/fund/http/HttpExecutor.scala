package cn.yxffcode.fund.http

import java.util.concurrent.Future

/**
  * @author gaohang on 8/30/16.
  */
trait HttpExecutor {
  def get(url: String): String
}
