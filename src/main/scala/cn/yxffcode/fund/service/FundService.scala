package cn.yxffcode.fund.service

import cn.yxffcode.fund.model.FundBrief

/**
  * @author gaohang on 9/3/16.
  */
trait FundService {
  def getAllFunds: Iterable[FundBrief];
}
