package com.github.xtorrent.xtorrent.home.source.remote

import com.github.xtorrent.xtorrent.core.BASE_URL
import com.github.xtorrent.xtorrent.home.model.HomeResource
import com.github.xtorrent.xtorrent.home.source.HomeResourcesDataSource
import com.github.xtorrent.xtorrent.utils.newJsoupConnection
import rx.Observable
import rx.lang.kotlin.emptyObservable
import rx.lang.kotlin.observable

/**
 * Created by grubber on 16/11/29.
 */
class HomeResourcesRemoteDataSource : HomeResourcesDataSource {
    override fun getHomeResources(type: HomeResource.Type): Observable<List<HomeResource>> {
        return observable {
            if (!it.isUnsubscribed) {
                try {
                    val resources = when (type) {
                        HomeResource.Type.HOT -> _getHotResources()
                        HomeResource.Type.POPULARITY -> _getPopularityResources()
                        else -> _getNewlyResources()
                    }

                    it.onNext(resources)
                    it.onCompleted()
                } catch (e: Exception) {
                    it.onError(e)
                }
            }
        }
    }

    private fun _getHotResources(): List<HomeResource> {
        return _getHotOrPopularityResources(HomeResource.Type.HOT)
    }

    private fun _getPopularityResources(): List<HomeResource> {
        return _getHotOrPopularityResources(HomeResource.Type.POPULARITY)
    }

    private fun _getHotOrPopularityResources(type: HomeResource.Type): List<HomeResource> {
        val contentId = when (type) {
            HomeResource.Type.HOT -> "hot"
            else -> "fashion"
        }
        val document = newJsoupConnection(BASE_URL).get()
        val content = document.getElementById(contentId)
        val nodes = content.getElementsByClass("panel-item")
        return nodes.map {
            val link = it.select("a").first()
            val title = link.text()
            val url = link.attr("href")
            val description = it.select("div")[1].text()
            HomeResource.create(title, description, url, type)
        }
    }

    private fun _getNewlyResources(): List<HomeResource> {
        val document = newJsoupConnection(BASE_URL).get()
        val content = document.getElementById("newest")
        val links = content.getElementsByTag("a")
        return links.map {
            HomeResource.create(it.text(), null, it.attr("href"), HomeResource.Type.NEWLY)
        }
    }

    override fun getHomeResource(url: String): Observable<HomeResource> {
        // TODO
        return emptyObservable()
    }

    override fun saveHomeResource(resource: HomeResource) {
        // Ignored.
    }
}