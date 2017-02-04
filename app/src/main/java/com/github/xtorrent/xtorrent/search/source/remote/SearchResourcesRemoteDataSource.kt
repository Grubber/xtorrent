package com.github.xtorrent.xtorrent.search.source.remote

import com.github.xtorrent.xtorrent.core.BASE_URL
import com.github.xtorrent.xtorrent.search.model.Resource
import com.github.xtorrent.xtorrent.search.model.ResourceItem
import com.github.xtorrent.xtorrent.search.source.SearchResourcesDataSource
import com.github.xtorrent.xtorrent.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.observable
import timber.log.Timber
import java.net.URLEncoder

/**
 * Created by zhihao.zeng on 16/11/29.
 */
class SearchResourcesRemoteDataSource() : SearchResourcesDataSource {
    override fun getSearchResources(keyword: String, pageNumber: Int): Observable<List<Pair<Resource, List<ResourceItem>>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val searchUrl = "$BASE_URL/s/${URLEncoder.encode(keyword, "utf-8")}__1_$pageNumber"
                    val document = newJsoupConnection(searchUrl).get()
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

                    Timber.d("### load url is $searchUrl, data size is ${list.size}")

                    it.onNext(list)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getSearchResource(url: String): Observable<Pair<Resource, List<ResourceItem>>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection(url).get()
                    val node = document.getElementsByClass("detail")[0]
                    val titleNode = node.select("a").first()
                    val title = titleNode.text()
                    val magnet = node.select("a")[1].text()

                    val infoNode = node.getElementsByTag("td")
                    val type = infoNode[0].text()
                    val size = infoNode[1].text()
                    val files = infoNode[2].text()
                    val downloads = infoNode[3].text()
                    val updated = infoNode[4].text()
                    val created = infoNode[5].text()

                    val resource = Resource.create(url, title, magnet, type, size, files, downloads, updated, created)

                    val filesNode = node.getElementsByTag("ul")
                    val resourceItems = filesNode?.first()
                            ?.getElementsByClass("inline")
                            ?.map {
                                ResourceItem.create(it.text(), url)
                            } ?: arrayListOf()

                    it.onNext(Pair(resource, resourceItems))
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun getResourceTorrentUrl(magnet: String): Observable<String> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val document = newJsoupConnection("http://bt.gg/magnet2torrent")
                            .data("url", magnet)
                            .post()
                    val url = document?.getElementById("download_bt")
                            ?.attr("href")
                    it.onNext(url)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    override fun saveSearchResource(resource: Resource) {
        // Ignored.
    }

    override fun updateSearchResource(resource: Resource) {
        // Ignored.
    }
}