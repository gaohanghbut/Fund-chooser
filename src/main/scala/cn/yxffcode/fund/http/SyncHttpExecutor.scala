package cn.yxffcode.fund.http

import org.apache.commons.lang3.StringUtils
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils

/**
  * @author gaohang on 8/30/16.
  */
class SyncHttpExecutor extends HttpExecutor {
  var httpClient: CloseableHttpClient = _

  {
    this.httpClient = HttpClients.custom().setConnectionManager(new PoolingHttpClientConnectionManager).build()
  }

  def destroy = {
    httpClient.close
  }

  override def get(url: String): String = {
    val response: CloseableHttpResponse = httpClient.execute(new HttpGet(url))
    if (response.getStatusLine.getStatusCode != 200) {
      return StringUtils.EMPTY
    }
    return EntityUtils.toString(response.getEntity, "UTF-8")
  }
}
