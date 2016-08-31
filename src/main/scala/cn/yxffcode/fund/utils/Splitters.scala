package cn.yxffcode.fund.utils

import com.google.common.base.Splitter
import scala.collection.JavaConversions._

/**
  * @author gaohang on 8/30/16.
  */
object Splitters {

  private val commaSplitter: Splitter = Splitter.on(',').trimResults()

  implicit class StringSplitter(private val self: String) extends AnyVal {
    def commaSplit(): Iterable[String] = commaSplitter.split(self)

    def commaSplitToSeq(): Seq[String] = commaSplitter.splitToList(self)
  }

}
