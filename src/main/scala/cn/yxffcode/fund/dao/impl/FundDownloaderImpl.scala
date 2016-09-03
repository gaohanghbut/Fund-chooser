package cn.yxffcode.fund.dao.impl

import cn.yxffcode.fund.dao.{FundDownloader, Page}
import cn.yxffcode.fund.http.{HttpExecutor, SyncHttpExecutor}
import cn.yxffcode.fund.model.{FundBrief, FundDetail, FundResponse}
import cn.yxffcode.fund.utils.Jsons._
import cn.yxffcode.fund.utils.Splitters._

import scala.collection.mutable
import scala.io.Source._

/**
  * @author gaohang on 8/30/16.
  */
class FundDownloaderImpl(httpexe: HttpExecutor) extends FundDownloader {
  private val jsonPrefix = "var rankData = "

  private val httpExecutor = httpexe

  override def downloadList(page: Page): List[FundBrief] = {
    val url = s"http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&qdii=&pi=${page.page}&pn=${page.size}"

    val content = httpExecutor get url
    val json = content.substring(jsonPrefix.length, content.length - 1)

    (json mapJson classOf[FundResponse]).datas.map(item => FundBrief(item.commaSplit()))
  }

  override def downloadDetail(fundCode: String): Option[FundDetail] = {
    val url = s"http://fund.eastmoney.com/pingzhongdata/$fundCode.js"
    val content = httpExecutor get url

    //从content生成存储变成与json的map
    val jsonObjects: mutable.HashMap[String, String] = mutable.HashMap[String, String]()
    fromString(content).getLines.foreach(line => {
      if (line startsWith "var") {
        val variabledesc: Iterator[String] = ((line substring "var".length) =:).iterator
        val name: String = variabledesc.next
        val value: String = variabledesc.next
        jsonObjects.put(name, value.substring(0, value.length - 1))
      }
    })
    val option: Option[String] = jsonObjects get "Data_currentFundManager"
    if (option.isEmpty) {
      return None
    }
    val managerData: Map[String, _] = option.get.mapJson(classOf[List[_]]).head.asInstanceOf[Map[String, _]]

    val detail: FundDetail = new FundDetail
    detail.managerId = managerData.get("id").get.asInstanceOf[String]
    detail.managerName = managerData.get("name").get.asInstanceOf[String]
    detail.workTime = managerData.get("workTime").get.asInstanceOf[String]

    val data: List[Map[String, _]] = managerData.get("profit").get.asInstanceOf[Map[String, _]]
      .get("series").get.asInstanceOf[List[Map[String, _]]]

    val profitIter: Iterator[Double] = data.map(map => map.get("data")
      .get.asInstanceOf[List[Map[String, _]]].map(map => map.get("y")
      .get.asInstanceOf[Double])).head.iterator

    detail.selfProfit = profitIter.next
    detail.categoryAvgProfit = profitIter.next
    detail.stockProfit = profitIter.next
    val rawFundCode: String = jsonObjects.get("fS_code").get
    detail.fundCode = rawFundCode.substring(1, rawFundCode.length - 1)

    Option(detail)
  }
}