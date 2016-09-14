package cn.yxffcode.fund.dao.impl

import cn.yxffcode.fund.dao.{FundDownloader, Page}
import cn.yxffcode.fund.http.{SyncHttpExecutor, HttpExecutor}
import cn.yxffcode.fund.model.{FundBrief, FundDetail, FundResponse}
import cn.yxffcode.fund.utils.Jsons._
import cn.yxffcode.fund.utils.Splitters._
import org.apache.commons.lang3.StringUtils
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, Days}
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.io.Source

/**
  * @author gaohang on 8/30/16.
  */
class FundDownloaderImpl(httpexe: HttpExecutor) extends FundDownloader {
  private val jsonPrefix = "var rankData = "

  private val managerDateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")

  private val httpExecutor = httpexe

  override def downloadList(page: Page): List[FundBrief] = {
    val url = s"http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&qdii=&pi=${page.page}&pn=${page.size}"

    val content = httpExecutor get url
    val json = content.substring(jsonPrefix.length, content.length - 1)

    (json mapJson classOf[FundResponse]).datas.map(item => FundBrief(item.commaSplit()))
  }

  override def downloadDetail(fundCode: String): Option[FundDetail] = {
    val fundDetail: FundDetail = downloadDetailBasic(fundCode)
    if (fundDetail == null) {
      return None
    }

    downloadManagerInfo(fundDetail)

    Option(fundDetail)
  }

  private def downloadManagerInfo(fundDetail: FundDetail): Unit = {
    //download manager changing
    val managerChangingUrl = s"http://fund.eastmoney.com/f10/jjjl_${fundDetail.fundCode}.html"
    val managerHtml: String = httpExecutor get managerChangingUrl
    val managerDoc: Document = Jsoup.parse(managerHtml)
    val manageStartInfo: Elements = managerDoc.select("#bodydiv > div:nth-child(9) > div.r_cont.right > div.detail > div.txt_cont > div > div:nth-child(1) > div > table > tbody > tr")

    for (tr <- manageStartInfo) {
      val columns: Elements = tr.select("td")
      if (!columns.isEmpty) {
        val column: Element = columns get 2
        column.select("a").foreach(a => {
          if (StringUtils.equals(a.text, fundDetail.managerName)) {
            //FIXME:同一个基金经理管理此基金的时间区间可能不连续,会导致结果不准
            val manageDate: DateTime = managerDateFormatter.parseDateTime(columns.get(0).text())
            val currentWorkTime: Int = Days.daysBetween(manageDate, DateTime.now()).getDays
            if (currentWorkTime > fundDetail.workTime) {
              fundDetail.workTime = currentWorkTime
            }
          }
        })
      }
    }
  }

  private def downloadDetailBasic(fundCode: String): FundDetail = {
    val url = s"http://fund.eastmoney.com/pingzhongdata/$fundCode.js"
    val content = httpExecutor get url

    //从content生成存储变成与json的map
    val jsonObjects: mutable.HashMap[String, String] = mutable.HashMap[String, String]()
    Source.fromString(content).getLines.foreach(line => {
      if (line startsWith "var") {
        val variabledesc: Iterator[String] = ((line substring "var".length) =:).iterator
        val name: String = variabledesc.next
        val value: String = variabledesc.next
        jsonObjects.put(name, value.substring(0, value.length - 1))
      }
    })
    val option: Option[String] = jsonObjects get "Data_currentFundManager"
    if (option.isEmpty) {
      return null
    }

    val managerData: Map[String, _] = option.get.mapJson(classOf[List[_]]).head.asInstanceOf[Map[String, _]]

    val detail: FundDetail = new FundDetail
    detail.managerId = managerData.get("id").get.asInstanceOf[String]
    detail.managerName = managerData.get("name").get.asInstanceOf[String]

    val data: List[Map[String, _]] = managerData.get("profit").get.asInstanceOf[Map[String, _]]
      .get("series").get.asInstanceOf[List[Map[String, _]]]

    val profitIter: Iterator[Double] = data.map(map => map.get("data")
      .get.asInstanceOf[List[Map[String, _]]].map(map => {
      val value = map.get("y").get
      if (value.isInstanceOf[Double]) {
        value.asInstanceOf[Double]
      } else if (value.isInstanceOf[Float]) {
        value.asInstanceOf[Float].toDouble
      } else {
        value.asInstanceOf[Integer].toDouble
      }
    })).head.iterator

    detail.selfProfit = profitIter.next
    detail.categoryAvgProfit = profitIter.next
    if (profitIter.hasNext) {
      detail.stockProfit = profitIter.next
    }
    val rawFundCode: String = jsonObjects.get("fS_code").get
    detail.fundCode = rawFundCode.substring(1, rawFundCode.length - 1)

    val stockDataOption: Option[String] = jsonObjects.get("stockCodes")
    if (!stockDataOption.isEmpty) {
      val stocks: Seq[String] = stockDataOption.get.commaSplitToSeq()
    }
    detail
  }
}

object FundDownloaderImpl {
  def main(args: Array[String]) {
    val downloader: FundDownloaderImpl = new FundDownloaderImpl(new SyncHttpExecutor)
    val detail: Option[FundDetail] = downloader.downloadDetail("020003")
    println(detail)
  }
}
