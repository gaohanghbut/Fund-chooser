package cn.yxffcode.fund.utils

import org.apache.commons.lang3.math.NumberUtils

/**
  * @author gaohang on 8/30/16.
  */
object Maths {

  implicit class Strings(private val self: String) extends AnyVal {
    @inline def toBigDecimal: BigDecimal = NumberUtils.toDouble(self, 0)
  }

  implicit class MathFunc(private val self: BigDecimal) extends AnyVal {
    @inline def sqrt: Double = math.sqrt(self.doubleValue())

    @inline def ln: Double = math.log(self.doubleValue())
  }

}

object AvgProfit {
  def apply(days: Int, totalProfit: Double): Double = {
    val powTimes: Double = math.log10(totalProfit.doubleValue()) / days
    math.pow(10, powTimes) - 1
  }
}
