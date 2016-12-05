package com.github.xtorrent.xtorrent.search.source.remote

import com.github.xtorrent.xtorrent.core.BASE_URL
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import com.github.xtorrent.xtorrent.search.source.SearchResourcesDataSource
import org.jsoup.Jsoup
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable
import java.net.URLEncoder

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class SearchResourcesRemoteDataSource() : SearchResourcesDataSource {
    override fun getSearchResources(keyword: String): Observable<List<Pair<Resource, List<ResourceItem>>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val searchUrl = "$BASE_URL/s/${URLEncoder.encode(keyword, "utf-8")}"
                    val document = Jsoup.connect(searchUrl).get()
                    val nodes = document.getElementsByClass("result-item")
                    val list = nodes.map {
                        val titleNode = it.select("a").first()
                        val url = titleNode.attr("abs:href")
                        val title = titleNode.html()

                        val infoNode = it.getElementsByClass("info").first().getElementsByTag("span")
                        val type = infoNode[0].text()
                        val files = infoNode[1].text()
                        val size = infoNode[2].text()
                        val downloads = infoNode[3].text()
                        val updated = infoNode[4].text()
                        val created = infoNode[5].text()

                        val resource = Resource.create(url, title, "", type, size, files, downloads, updated, created)

                        val filesNode = it.getElementsByClass("files")
                        val resourceItems = filesNode?.first()
                                ?.getElementsByClass("inline")
                                ?.map {
                                    ResourceItem.create(it.text(), url)
                                } ?: arrayListOf()
                        Pair(resource, resourceItems)
                    }

                    it.onNext(list)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getSearchResource(url: String): Observable<Pair<Resource, List<ResourceItem>>> {
        // TODO
        return emptyObservable()
    }

    override fun saveSearchResource(resource: Resource) {
        // Ignored.
    }

    override fun updateSearchResource(resource: Resource) {
        // Ignored.
    }
}