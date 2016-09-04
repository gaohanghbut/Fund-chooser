package cn.yxffcode.fund.service

import cn.yxffcode.fund.model.{FundBrief, FundDetail}

/**
  * @author gaohang on 9/3/16.
  */
trait FundService {
  def getAllFundBriefs: Iterable[FundBrief];

  def getAllFundDetails: Iterable[FundDetail];

  def scoreManagerForFund(fundDetail: FundDetail);
}
