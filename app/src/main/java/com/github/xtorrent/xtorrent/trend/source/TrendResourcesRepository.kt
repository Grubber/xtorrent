package com.github.xtorrent.xtorrent.trend.source

import com.github.xtorrent.xtorrent.core.BASE_URL
import com.github.xtorrent.xtorrent.trend.model.TrendResource
import com.github.xtorrent.xtorrent.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable
import javax.inject.Inject

/**
 * Created by grubber on 16/12/27.
 */
@TrendResourcesScope
class TrendResourcesRepository @Inject constructor() : TrendResourcesDataSource {
    override fun getResources(type: String): Observable<List<TrendResource>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val searchUrl = "$BASE_URL/trend?type=$type"
                    val document = newJsoupConnection(searchUrl).get()
                    val resources = document.getElementsByTag("tbody")?.get(0)?.getElementsByTag("tr")?.map {
                        val nodes = it.getElementsByTag("td")
                        val title = nodes[1].text()
                        val url = nodes[1].select("a").first().attr("abs:href")
                        val downloads = nodes[2].text()
                        val created = nodes[3].text()
                        val files = nodes[4].text()
                        TrendResource(title, url, files, downloads, created)
                    }
                    it.onNext(resources)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }
}