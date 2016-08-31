package cn.yxffcode.fund.model

import java.util.Date

import cn.yxffcode.fund.utils.Maths._

/**
  * @author gaohang on 8/30/16.
  */
class FundBrief {
  var fundCode: String = _
  var fundName: String = _
  var nav: BigDecimal = _
  var totalNav: BigDecimal = _
  var weekDelta: BigDecimal = _
  var monthDelta: BigDecimal = _
  var threeMonthDelta: BigDecimal = _
  var halfYearDelta: BigDecimal = _
  var yearDelta: BigDecimal = _
  var twoYearDelta: BigDecimal = _
  var threeYearDelta: BigDecimal = _
  var thisYear: BigDecimal = _
  var fromBirth: BigDecimal = _
  var createDate: Date = _

  override def toString = s"FundBrief(fundCode=$fundCode, fundName=$fundName, nav=$nav, totalNav=$totalNav, weekDelta=$weekDelta, monthDelta=$monthDelta, halfYearDelta=$halfYearDelta, yearDelta=$yearDelta, twoYearDelta=$twoYearDelta, threeYearDelta=$threeYearDelta, thisYear=$thisYear, fromBirth=$fromBirth)"
}

object FundBrief {
  def apply(info: Iterable[String]): FundBrief = {
    val brief: FundBrief = new FundBrief
    val iter = info.iterator

    brief.fundCode = iter.next()
    brief.fundName = iter.next()
    iter.next()
    iter.next()
    brief.nav = iter.next.toBigDecimal
    brief.totalNav = iter.next.toBigDecimal
    iter.next()
    brief.weekDelta = iter.next.toBigDecimal
    brief.monthDelta = iter.next.toBigDecimal
    brief.threeMonthDelta = iter.next.toBigDecimal
    brief.halfYearDelta = iter.next.toBigDecimal
    brief.yearDelta = iter.next.toBigDecimal
    brief.twoYearDelta = iter.next.toBigDecimal
    brief.threeYearDelta = iter.next.toBigDecimal
    brief.thisYear = iter.next.toBigDecimal
    brief.fromBirth = iter.next.toBigDecimal

    brief.createDate = new Date()

    return brief
  }
}
