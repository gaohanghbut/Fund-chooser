package cn.yxffcode.fund.utils

import org.apache.commons.lang3.math.NumberUtils

/**
  * @author gaohang on 8/30/16.
  */
object Maths {
  implicit class Strings(private val self: String) extends AnyVal {
    def toBigDecimal: BigDecimal = NumberUtils.toDouble(self, 0)
  }
}
