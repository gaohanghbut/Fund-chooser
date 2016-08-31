package cn.yxffcode.fund.io

/**
  * @author gaohang on 8/30/16.
  */
class Page(pageNo: Int, pageSize: Int) {
  val page: Int = pageNo
  val size: Int = pageSize
}

object Page {
  def apply(pageNo: Int, pageSize: Int): Page = new Page(pageNo, pageSize)
}
