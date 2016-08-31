package cn.yxffcode.fund.io.impl

import cn.yxffcode.freetookit.collection.MappingMap
import cn.yxffcode.freetookit.text.TextTemplate
import cn.yxffcode.fund.http.HttpExecutor
import cn.yxffcode.fund.io.{FundListDownloader, Page}
import cn.yxffcode.fund.model.{FundBrief, FundResponse}
import cn.yxffcode.fund.utils.Jsons._
import cn.yxffcode.fund.utils.Splitters._

/**
  * @author gaohang on 8/30/16.
  */
class FundListDownloaderImpl(httpexe: HttpExecutor) extends FundListDownloader {
  private val jsonPreffix: String = "var rankData = "

  private val urlTemplate: TextTemplate = new TextTemplate("http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&qdii=&pi=#{page}&pn=#{size}")

  private val httpExecutor: HttpExecutor = httpexe

  override def download(page: Page): List[FundBrief] = {
    val url = s"http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&qdii=&pi=${page.page}&pn=${page.size}"

    val content: String = httpExecutor.get(url)
    val json: String = content.substring(jsonPreffix.length, content.length - 1)

    json.mapJson(classOf[FundResponse]).datas.map(item => FundBrief(item.commaSplit()))
  }
}
