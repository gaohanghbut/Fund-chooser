package cn.yxffcode.fund.io

import cn.yxffcode.fund.model.FundBrief

/**
  * @author gaohang on 8/31/16.
  */
trait FundBriefWriter {
  def writeFundBriefs(fundBriefs: Iterable[FundBrief]): Unit
}
